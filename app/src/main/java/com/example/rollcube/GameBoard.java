package com.example.rollcube;

import com.example.rollcube.gameinterface.Pointer;
import com.example.rollcube.gameinterface.buttons.StartButton;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.gameinterface.mainmenu.records.Records;
import com.example.rollcube.gameobjects.MainBackground;
import com.example.rollcube.gameobjects.cells.CellObject;
import com.example.rollcube.managers.DBHelper;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.gamecubes.GameCubeFigures3;
import com.example.rollcube.gameobjects.gamecubes.GameCubeFigures6;
import com.example.rollcube.gameobjects.gamecubes.GameCubeXYZ;
import com.example.rollcube.gameobjects.gamecubes.StartingCube;
import com.example.rollcube.gameobjects.platforms.Platform;
import com.example.rollcube.gameobjects.twisttiles.TwistTile;
import com.example.rollcube.gameobjects.twisttiles.TwistTileDice;
import com.example.rollcube.gameobjects.twisttiles.TwistTileFigures3;
import com.example.rollcube.gameobjects.twisttiles.TwistTileFigures6;
import com.example.rollcube.gameobjects.twisttiles.TwistTileXYZ;
import com.example.rollcube.gameobjects.walls.Wall;
import com.example.rollcube.gameobjects.walls.WallDice;
import com.example.rollcube.gameobjects.walls.WallFigures3;
import com.example.rollcube.gameobjects.walls.WallFigures6;
import com.example.rollcube.gameobjects.walls.WallXYZ;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.gameobjects.cells.Cell;
import com.example.rollcube.gameobjects.cells.CellNet;
import com.example.rollcube.gameobjects.gamecubes.GameCube;
import com.example.rollcube.gameobjects.gamecubes.GameCubeDice;
import com.example.rollcube.gameobjects.staticobjects.StaticObjects;
import com.example.rollcube.gameinterface.Timer;

import java.util.LinkedList;
import java.util.Random;


public class GameBoard implements Drawable {
    public enum GameType {
        FIGURES_3("FIGURES_3"),
        FIGURES_6("FIGURES_6"),
        DICE("DICE"),
        XYZ("XYZ"),
        STARTING("");

        public final String string;
        GameType(String string) {
            this.string = string;
        }
    }

    private final MainManager mainManager;
    private final ClassMediator classMediator;
    private final DBHelper dbHelper;
    public static int N; // X
    public static int M; // Z
    public static final float TILE_SIDE = 0.15f;
    public static final float GAP_WIDTH = TILE_SIDE / 5;
    public static final float CELL_SIDE = TILE_SIDE + GAP_WIDTH;
    public static float BOARD_SIZE_N;
    public static float BOARD_SIZE_M;
    public static Point2DFloat CENTER_POINT;

    private final StaticObjects staticObjects;
    private final CellNet cellNet;
    private GameCube gameCube;
    private MovingDetails movingDetails = null;

    private Pointer pointer;
    private Timer timer;
    private Records.RecordArrays recordArrays;

    private GameInfo gameInfo = null;

    private final Random rand = new Random();

    private int detailCounter = 0;
    private Cell activeCell = null;
    private final LinkedList<CellObject.CellCoords> platformsCoordList = new LinkedList<>();

    private final CubeController.Queue queue;
    private final CubeController cubeController;
    private NewGameLauncher newGameLauncher = null;



    public GameBoard(MainManager mainManager, ClassMediator classMediator, int N_, int M_, Records.RecordArrays recordArrays) {
        this.mainManager = mainManager;
        this.classMediator = classMediator;
        this.classMediator.gameBoard = this;
        this.recordArrays = recordArrays;

        dbHelper = new DBHelper(mainManager.getContext(), "records_file", recordArrays);
        dbHelper.uploadRecordArraysFromSQL();

        N = N_;
        M = M_;

        BOARD_SIZE_N = N * TILE_SIDE + (N + 1) * GAP_WIDTH;
        BOARD_SIZE_M = M * TILE_SIDE + (M + 1) * GAP_WIDTH;
        CENTER_POINT = new Point2DFloat(BOARD_SIZE_N / 2, BOARD_SIZE_M / 2);

        staticObjects = new StaticObjects(N, M, mainManager);
        cellNet = new CellNet(mainManager, N, M, GameType.STARTING);
        gameCube = createGameCube(mainManager, GameType.STARTING);

        createPlatforms();

        queue = new CubeController.Queue();
        cubeController = new CubeController(this, gameCube, queue);
    }



    private GameCube createGameCube(MainManager mainManager, GameType gameType) {
        GameCube gameCube = null;
        switch (gameType) {
            case STARTING:
                gameCube = new StartingCube(mainManager, (N - 1) / 2,(M - 1) / 2, this);
                break;
            case FIGURES_3:
                gameCube = new GameCubeFigures3(mainManager, (N - 1) / 2,(M - 1) / 2, this);
                break;
            case FIGURES_6:
                gameCube = new GameCubeFigures6(mainManager, (N - 1) / 2,(M - 1) / 2, this);
                break;
            case DICE:
                gameCube = new GameCubeDice(mainManager, (N - 1) / 2,(M - 1) / 2, this);
                break;
            case XYZ:
                gameCube = new GameCubeXYZ(mainManager, (N - 1) / 2,(M - 1) / 2, this);
                break;
        }
        classMediator.gameCube = gameCube;
        return gameCube;
    }

    private void createPlatforms() {
        addPlatform(0, 1, Platform.PlatformType.ROTATE_RIGHT_90);
        addPlatform(4, 0, Platform.PlatformType.ROTATE_RIGHT_180);
        addPlatform(5, 4, Platform.PlatformType.ROTATE_LEFT_90);
        addPlatform(1, 5, Platform.PlatformType.ROTATE_LEFT_180);
    }

    private void addPlatform(int n, int m, Platform.PlatformType platformType) {
        platformsCoordList.add(new CellObject.CellCoords(n, m));
        cellNet.getCell(n, m).createPlatform(mainManager, platformType);
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
        timer.setGameBoard(this);
        Thread timerThread = new Thread(timer);
        timerThread.start();
    }

    public void setPointCounter(Pointer pointer) {
        this.pointer = pointer;
        pointer.setGameBoard(this);
    }

    public boolean isMoveLegal(CubeController.Queue.MoveDirection move) {
        boolean isMoveLegal = false;
        CellObject.CellCoords gameCubeCoords = gameCube.getCoords();
        Cell cell;
        switch (move) {
            case LEFT:
                if (gameCubeCoords.n > 0) {
                    boolean isWallDown = cellNet.getCell(gameCubeCoords.n, gameCubeCoords.m).isLeftWallDeactivated();
                    if (isWallDown) {
                        cell = cellNet.getCell(gameCubeCoords.n - 1, gameCubeCoords.m);
                        if (cell.isTwistTileDeactivated()) {
                            isMoveLegal = true;
                        }
                        else {
                            isMoveLegal = gameCube.isMoveLegal(cell.getTwistTile().getSide(), move);
                        }
                    }
                }
                break;
            case RIGHT:
                if (gameCubeCoords.n < N - 1) {
                    boolean isWallDown = cellNet.getCell(gameCubeCoords.n, gameCubeCoords.m).isRightWallDeactivated();
                    if (isWallDown) {
                        cell = cellNet.getCell(gameCubeCoords.n + 1, gameCubeCoords.m);
                        if (cell.isTwistTileDeactivated()) {
                            isMoveLegal = true;
                        }
                        else {
                            isMoveLegal = gameCube.isMoveLegal(cell.getTwistTile().getSide(), move);
                        }
                    }
                }
                break;
            case BACK:
                if (gameCubeCoords.m < M - 1) {
                    boolean isWallDown = cellNet.getCell(gameCubeCoords.n, gameCubeCoords.m).isBackWallDeactivated();
                    if (isWallDown) {
                        cell = cellNet.getCell(gameCubeCoords.n, gameCubeCoords.m + 1);
                        if (cell.isTwistTileDeactivated()) {
                            isMoveLegal = true;
                        }
                        else {
                            isMoveLegal = gameCube.isMoveLegal(cell.getTwistTile().getSide(), move);
                        }
                    }
                }
                break;
            case FORWARD:
                if (gameCubeCoords.m > 0) {
                    boolean isWallDown = cellNet.getCell(gameCubeCoords.n, gameCubeCoords.m).isForwardWallDeactivated();
                    if (isWallDown) {
                        cell = cellNet.getCell(gameCubeCoords.n, gameCubeCoords.m - 1);
                        if (cell.isTwistTileDeactivated()) {
                            isMoveLegal = true;
                        }
                        else {
                            isMoveLegal = gameCube.isMoveLegal(cell.getTwistTile().getSide(), move);
                        }
                    }
                }
                break;
        }
        return isMoveLegal;
    }

    public void putCubeMove(CubeController.Queue.MoveDirection move) {
        synchronized (queue) {
            queue.put(move);
        }
    }



    @Override
    public void draw() {
        gameCube.draw();
        if(movingDetails != null) {
            movingDetails.draw();
        }

        mainManager.bindMainObjects();
        staticObjects.draw();
        cellNet.draw();
    }




    public void nextRandomGroup() {
        int N = GameBoard.N;
        int M = GameBoard.M;
        CellObject.CellCoords cubeCoords = gameCube.getCoords();
        int cube_n = cubeCoords.n;
        int cube_m = cubeCoords.m;

        int rand_n = rand.nextInt(N);
        int rand_m = rand.nextInt(M);

        boolean a = cube_n == rand_n && cube_m == rand_m;
        boolean a_back = cube_n == rand_n && cube_m + 1 == rand_m;
        boolean a_forward = cube_n == rand_n && cube_m - 1 == rand_m;
        boolean a_right = cube_n + 1 == rand_n && cube_m == rand_m;
        boolean a_left = cube_n - 1 == rand_n && cube_m == rand_m;

        if (a || a_back || a_forward || a_left || a_right) {
            if (cube_n == 0 && cube_m == 0) {
                rand_n = 1;
                rand_m = 1;
            }
            else if (cube_n == 0 && cube_m == M - 1) {
                rand_n = 1;
                rand_m = M - 2;
            }
            else if (cube_n == N - 1 && cube_m == 0) {
                rand_n = N - 2;
                rand_m = 1;
            }
            else if (cube_n == N - 1 && cube_m == M - 1) {
                rand_n = N - 2;
                rand_m = M - 2;
            }

            else if (cube_n == 0 || cube_n == N - 1) {
                if(rand.nextBoolean()) {
                    rand_m = cube_m - 1;
                }
                else {
                    rand_m = cube_m + 1;
                }
                if (cube_n == 0) {
                    rand_n = 1;
                }
                else {
                    rand_n = N - 2;
                }
            }
            else if (cube_m == 0 || cube_m == M - 1) {
                if(rand.nextBoolean()) {
                    rand_n = cube_n - 1;
                }
                else {
                    rand_n = cube_n + 1;
                }
                if (cube_m == 0) {
                    rand_m = 1;
                }
                else {
                    rand_m = M - 2;
                }
            }

            else {
                int rand03 = rand.nextInt(4);
                if (rand03 == 0) {
                    rand_n = cube_n + 1;
                    rand_m = cube_n + 1;
                }
                else if (rand03 == 1) {
                    rand_n = cube_n + 1;
                    rand_m = cube_n - 1;
                }
                else if (rand03 == 2) {
                    rand_n = cube_n - 1;
                    rand_m = cube_n + 1;
                }
                else {
                    rand_n = cube_n - 1;
                    rand_m = cube_n - 1;
                }
            }
        }

        int cellCoord_n;
        int cellCoord_m;

        if (cube_n <= 2 && cube_m <= 2) {
            cellCoord_n = 3;
            cellCoord_m = 3;
        }
        else if (cube_n > 2 && cube_m > 2) {
            cellCoord_n = 2;
            cellCoord_m = 2;
        }
        else if (cube_n <= 2) {
            cellCoord_n = 3;
            cellCoord_m = 2;
        }
        else {
            cellCoord_n = 2;
            cellCoord_m = 3;
        }


        if(activeCell != null) {
            activeCell.disconnectDetails();
        }
        activeCell = cellNet.getCell(cellCoord_n, cellCoord_m);
        activeCell.collectMovingDetailsAndActivate(movingDetails, rand);


//        cellNet.activateCell(rand_n, rand_m, rand);
//        activeCell = cellNet.getCell(rand_n, rand_m);
    }

    public void onCubeAnimEnd() {
        if (!pointer.isFinished() && !timer.isPaused()) {
            int detailsCount = cellNet.checkDetails(gameCube);
            if(detailsCount > 0) {
                switch(gameInfo.regime) {
                    case POINT_RACE:
                        pointer.addOrTakePoints(countPoints(detailsCount), true);
                        break;
                    case TIME_RACE:
                        pointer.addOrTakePoints(countPoints(detailsCount), false);
                        break;
                }

                detailCounter += detailsCount;
                if (detailCounter == 1) {
                    activeCell.activateTile(rand);
                }
                if (detailCounter == 5) {
                    detailCounter = 0;
                    if (!pointer.isFinished()) {
                        nextRandomGroup();
                    }
                }
                if (pointer.isFinished()) {
                    finishGame();
                }
            }
        }
    }

    public void activatePlatforms() {
        for (CellObject.CellCoords cellCoord: platformsCoordList) {
            cellNet.getCell(cellCoord.n, cellCoord.m).activatePlatform();
        }
    }

    public void deactivatePlatforms() {
        for (CellObject.CellCoords cellCoord: platformsCoordList) {
            cellNet.getCell(cellCoord.n, cellCoord.m).deactivatePlatform();
        }
    }

    public void startGame() {
        switch (getGameType()) {
            case DICE:
            case XYZ:
            case FIGURES_6:
                activatePlatforms();
                break;
            case FIGURES_3:
                // In that type of game platforms are unnecessary, but it can be added for fun.
                // Remove this method if you don't want to see platforms in FIGURES_3.
                activatePlatforms();
                break;
        }
        nextRandomGroup();
        unblockGameCube();
        timer.start();
    }

    public void finishGame() {
        detailCounter = 0;
        timer.pause();
        classMediator.gameInterface.setStartButtonCondition(StartButton.Status.FINISHED);
        classMediator.gameView.gameStatus = GameView.GameStatus.FINISHED;
        int recordIdx;
        switch (gameInfo.regime) {
            case POINT_RACE:
                recordIdx = recordArrays.checkCandidateOnRecord(gameInfo, pointer.getValue());
                if(recordIdx != -1) {
                    pointer.startCelebration(recordIdx);
                }
                break;
            case TIME_RACE:
                recordIdx = recordArrays.checkCandidateOnRecord(gameInfo, timer.displayTime());
                if(recordIdx != -1) {
                    timer.startCelebration(recordIdx);
                }
                break;
        }
    }

    public void pauseGame() {
        blockGameCube();
        timer.pause();
    }

    public void resumeGame() {
        unblockGameCube();
        timer.resume();
    }

    public void prepareNewGame() {
        detailCounter = 0;
        if (activeCell != null) {
            activeCell.disconnectDetails();
        }
        movingDetails.deactivateDetails();
        deactivatePlatforms();
        unblockGameCube();
        timer.setStartTime();
        pointer.setStartPoints();
        gameCube.moveTo((N - 1) / 2, (M - 1) / 2);
        classMediator.camera.setAnimationConnectingParams(
                new Point2DFloat(
                        GameBoard.CENTER_POINT.x + GameBoard.CELL_SIDE / 2,
                        GameBoard.CENTER_POINT.y + GameBoard.CELL_SIDE / 2 )
        );
    }

    public Point2DFloat getCubeCoordsFloat() {
        CellObject.CellCoords cellCoords = gameCube.getCoords();
        float x = (cellCoords.n + 1) * (GameBoard.TILE_SIDE + GameBoard.GAP_WIDTH) - GameBoard.TILE_SIDE / 2;
        float z = (cellCoords.m + 1) * (GameBoard.TILE_SIDE + GameBoard.GAP_WIDTH) - GameBoard.TILE_SIDE / 2;
        return new Point2DFloat(x, z);
    }


    private int countPoints(int detailCounter) {
        if(detailCounter == 1) {
            return 1;
        }
        else if (detailCounter == 2) {
            return 3;
        }
        else if (detailCounter == 3) {
            return 7;
        }
        else {
            return 15;
        }
    }

    public void setTransparency(Vector3DFloat cameraN) {
        if (activeCell != null) {
            activeCell.setTransparency(cameraN);
        }
    }

    public void blockGameCube() {
        gameCube.block();
    }
    public void unblockGameCube() {
        gameCube.unblock();
    }
    public boolean isCubeBlocked() {
        return gameCube == null || gameCube.isBlocked();
    }

    public void switchOffCubeController() {
        cubeController.endThread();
    }


    public GameType getGameType() {
        return gameInfo.gameType;
    }
    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void chooseGameType(GameInfo gameInfo) {
        //mainManager.texturesId.changeTextures(GameBoard.this.gameInfo, gameInfo);
        this.gameInfo = gameInfo;
        newGameLauncher = new NewGameLauncher(gameInfo);
    }

    public void saveRecordsIfWereChanged() {
        if(recordArrays.wasChanged) {
            dbHelper.deleteAllRecords();
            dbHelper.saveRecordArraysToSQL(dbHelper.getWritableDatabase(), false);
        }
    }

    public GameCube getGameCube() {
        return gameCube;
    }

    public CubeController getCubeController() {
        return cubeController;
    }

    public void setMainBGIntoCubeController(MainBackground mainBackground) {
        cubeController.setMainBackground(mainBackground);
    }


    public static class MovingDetails implements Drawable{
        public TwistTile[] twistTiles;
        public Wall[] wallsN;
        public Wall[] wallsM;

        public MovingDetails(MainManager mainManager, GameType gameType) {
            twistTiles = new TwistTile[2];
            wallsN = new Wall[4];
            wallsM = new Wall[4];
            createDetails(mainManager, gameType);
        }


        private void createDetails(MainManager mainManager, GameType gameType) {
            twistTiles[0] = createTwistTile(mainManager, gameType);
            twistTiles[1] = createTwistTile(mainManager, gameType);
            for (int i = 0; i < 4; i++) {
                wallsN[i] = createWall(mainManager, Wall.Type.N, gameType);
                wallsM[i] = createWall(mainManager, Wall.Type.M, gameType);
            }
        }

        public TwistTile createTwistTile(MainManager mainManager, GameBoard.GameType gameType) {
            TwistTile twistTile = null;
            switch(gameType) {
                case FIGURES_3:
                    twistTile = new TwistTileFigures3(mainManager, 0, 0);
                    break;
                case FIGURES_6:
                    twistTile = new TwistTileFigures6(mainManager, 0, 0);
                    break;
                case DICE:
                    twistTile = new TwistTileDice(mainManager, 0, 0);
                    break;
                case XYZ:
                    twistTile = new TwistTileXYZ(mainManager, 0, 0);
                    break;
            }
            return twistTile;
        }

        public Wall createWall(MainManager mainManager, Wall.Type type, GameBoard.GameType gameType) {
            Wall wall = null;
            switch(gameType) {
                case FIGURES_3:
                    wall = new WallFigures3(mainManager, type);
                    break;
                case FIGURES_6:
                    wall = new WallFigures6(mainManager, type);
                    break;
                case DICE:
                    wall = new WallDice(mainManager, type);
                    break;
                case XYZ:
                    wall = new WallXYZ(mainManager, type);
                    break;
            }
            return wall;
        }


        @Override
        public void draw() {
            if (!twistTiles[0].isDeactivated()) {
                twistTiles[0].draw();
            }
            if (!twistTiles[1].isDeactivated()) {
                twistTiles[1].draw();
            }
            for (int i = 0; i < 4; i++) {
                if (!wallsN[i].isDeactivated()) {
                    wallsN[i].draw();
                }
                if (!wallsM[i].isDeactivated()) {
                    wallsM[i].draw();
                }
            }
        }


        public TwistTile findFreeTwistTile() {
            if(twistTiles[0].isDeactivated()) {
                return twistTiles[0];
            } else {
                return twistTiles[1];
            }
        }

        public Wall findFreeWallN() {
            for (Wall wallN: wallsN) {
                if (wallN.isDeactivated()) {
                    return wallN;
                }
            }
            return null;
        }

        public Wall findFreeWallM() {
            for (Wall wallM: wallsM) {
                if (wallM.isDeactivated()) {
                    return wallM;
                }
            }
            return null;
        }


        public void deactivateDetails() {
            twistTiles[0].deactivate();
            twistTiles[1].deactivate();
            for (int i = 0; i < 4; i++) {
                wallsN[i].deactivate();
                wallsM[i].deactivate();
            }
        }
    }



    public static class GameInfo {
        public GameType gameType;
        public GameRegimeSwitcher.Regime regime;
        public Race race;

        public GameInfo(GameType gameType) {
            this.gameType = gameType;
        }


        public class Race {}

        public class PointRace extends Race {
            public GameRegimeSwitcher.PointsRegime pointsRegime;
            public PointRace(GameRegimeSwitcher.PointsRegime pointsRegime) {
                this.pointsRegime = pointsRegime;

            }
        }

        public class TimeRace extends Race {
            public GameRegimeSwitcher.TimeRegime timeRegime;
            public TimeRace(GameRegimeSwitcher.TimeRegime timeRegime) {
                this.timeRegime = timeRegime;
            }
        }

    }


    public class NewGameLauncher implements Runnable {
        private final GameInfo gameInfo;
        private boolean isWaitingForLaunch = true;
        private boolean canLaunchNewGame = false;

        public NewGameLauncher(GameInfo gameInfo) {
            this.gameInfo = gameInfo;
            new Thread(this).start();
        }

        @Override
        public void run() {
            while(!canLaunchNewGame) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            launchNewGame();
        }

        public void launchNewGame() {
            mainManager.clearGameTypeList();
            MainBackground mainBackground = classMediator.scene.createMainBackground(mainManager,
                    classMediator.screenParams.dp_SCREEN_HEIGHT / classMediator.screenParams.dp_SCREEN_WIDTH,
                    gameInfo.gameType);
            gameCube = createGameCube(mainManager, gameInfo.gameType);
            movingDetails = new MovingDetails(mainManager, gameInfo.gameType);
            mainManager.initBuffersGameTypeObjects();
            cellNet.changeTilesGameType(gameInfo.gameType);
            cubeController.setGameCube(gameCube);
            cubeController.setMainBackground(mainBackground);
            prepareNewGame();
            isWaitingForLaunch = false;
            classMediator.openGLRend.resumeRend();
        }

    }

    public boolean isNewGameLauncherWaitingFor() {
        return newGameLauncher != null && newGameLauncher.isWaitingForLaunch;
    }

    public void allowLaunchNewGame() {
        newGameLauncher.canLaunchNewGame = true;
    }



}
