package com.example.rollcube;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;

import com.example.rollcube.gameinterface.buttons.StartButton;
import com.example.rollcube.linearalgebra.LinearAlgebra;
import com.example.rollcube.linearalgebra.PlaneFloat;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public class GameView extends GLSurfaceView {
    public enum GameStatus {READY_TO_START, RUNNING, PAUSED, FINISHED}

    private final ClassMediator classMediator;
    public GameStatus gameStatus = GameStatus.READY_TO_START;
    public static Point2DFloat VIEW_SIZE;
    private boolean isPaused = false;
    private GestureController gestureController;


    public GameView(ClassMediator classMediator) {
        super(classMediator.getContext());
        this.classMediator = classMediator;
        this.classMediator.gameView = this;
        init();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //float width = getWidth();
                //float height = getHeight();
                classMediator.screenParams.findSizeParams2(classMediator.gameActivity);
                float width = classMediator.screenParams.pxl_SCREEN_WIDTH;
                float height = classMediator.screenParams.pxl_SCREEN_HEIGHT;

                VIEW_SIZE = new Point2DFloat(width, height);

                float gameViewRatio = height/width;
                setRenderer(new OpenGLRend(classMediator, gameViewRatio));
            }
        });
    }


    private void init() {
        gestureController = new GestureController();
        setEGLContextClientVersion(2);
    }



    @Override
    public void onResume() {
        if(isPaused) {
            super.onResume();
            isPaused = false;
        }
    }

    @Override
    public void onPause() {
        if(!isPaused) {
            super.onPause();
            isPaused = true;
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureController.GestureData gestureData = gestureController.eventProcess(event);

        if (gestureData != null) {
            GestureController.GestureType gestureType = gestureData.gestureType;

            ClickEventData gestureEventData;

            switch(gestureType) {
                case CLICK:
                    GestureController.ClickData clickData = (GestureController.ClickData) gestureData;
                    Point2DFloat clickCoords = new Point2DFloat(clickData.x, clickData.y);
                    gestureEventData = classMediator.gameInterface.checkClick(clickCoords, null, null);
                    switch(gestureEventData.clickEvent) {
                        case NO_CLICK:
                        case NO_EVENT:
                            break;
                        case FIRST_RUN:
                            gameStatus = GameStatus.RUNNING;
                            classMediator.gameBoard.startGame();
                            break;
                        case PAUSE:
                            pauseGame();
                            break;
                        case RESUME:
                            resumeGame();
                            break;
                        case NEW_GAME:
                            startNewGame(gestureEventData.gameInfo);
                            break;
                        case OPEN_MENU:
                            if (gameStatus == GameStatus.RUNNING) {
                                pauseGame();
                            }
                            classMediator.gameInterface.openMainMenu();
                            break;
                        case CLOSE_MENU:
                            classMediator.gameInterface.closeMainMenu();
                            break;
                        case EXIT_GAME:
                            exitGame();
                            break;
                        case RESTART:
                            restartGame();
                            break;
                    }
                    break;
                case SWIPE:
                    if (classMediator.gameInterface.getSideMenu().isActive()) {
                        GestureController.SwipeData swipeData = (GestureController.SwipeData) gestureData;
                        classMediator.gameInterface.checkSwipe(swipeData, null, null);
                    } else {
                        if(!classMediator.gameBoard.isCubeBlocked()) {
                            moveGameCube((GestureController.SwipeData) gestureData);
                        }
                    }
                    break;
                case ZOOM:
                    GestureController.ZoomData  zoomData = (GestureController.ZoomData) gestureData;
                    switch (zoomData.getZoomType()) {
                        case INCREASE:
                            pushAnimation(Scene.AnimationType.CAMERA_FORWARD);
                            break;
                        case DECREASE:
                            pushAnimation(Scene.AnimationType.CAMERA_BACK);
                            break;
                    }
                    break;
                case LONG_PRESS:
                    GestureController.LongPressData longPressData = (GestureController.LongPressData) gestureData;
                    if(longPressData.dt > GestureController.LONG_PRESS_OK_BOARD_ACTIVATING) {
                        classMediator.gameInterface.closeSideMenu();
                        classMediator.gameInterface.closeMainMenu();
                        classMediator.gameInterface.activateOkBoard();
                    }
                    else if (classMediator.gameInterface.getSideMenu().isActive()) {
                        classMediator.gameInterface.checkLongPress(longPressData, null, null);
                    }
                    else {
                        //processPressOnCube(longPressData);
                    }
                    break;
            }
        }
        return true;
    }



    void processPressOnCube(GestureController.LongPressData longPressData) {
        PlaneFloat cameraPlane;
        Vector3DFloat cameraX;
        Vector3DFloat cameraY;

        Vector3DFloat[] corners = classMediator.camera.findCorners();

        Point2DFloat cubeCoordsFloat = classMediator.gameBoard.getCubeCoordsFloat();
        Vector3DFloat cubeCoordsFloat3D = new Vector3DFloat(cubeCoordsFloat.x, GameBoard.TILE_SIDE / 2, cubeCoordsFloat.y);
        cameraPlane = classMediator.camera.getPlane();

        Vector3DFloat cubeProjection = LinearAlgebra.getPointProjectionOnPlain(cameraPlane, cubeCoordsFloat3D);

        cameraX = corners[3].diff(corners[1]);
        cameraY = corners[0].diff(corners[1]);
        Vector3DFloat cameraCube = cubeProjection.diff(corners[1]);

        float cameraCubeX = cameraCube.scalarProd(cameraX.norm());
        float cameraCubeY = cameraCube.scalarProd(cameraY.norm());

        float pressX_cameraDimen = (cameraX.length() / VIEW_SIZE.x) * longPressData.x;
        float pressY_cameraDimen = (cameraY.length() / VIEW_SIZE.y) * longPressData.y;

        Vector2DFloat cameraCube2D = new Vector2DFloat(cameraCubeX, cameraCubeY);
        Vector2DFloat pressOnScreen = new Vector2DFloat(pressX_cameraDimen, pressY_cameraDimen);
        float R_sph = (float) (GameBoard.TILE_SIDE * Math.sqrt(3f) / 2);

        if (R_sph > cameraCube2D.diff(pressOnScreen).length()) {
            cameraCube2D.printVect("cube on screen");
            pressOnScreen.printVect("long pass on screen");
        }
    }

    private void moveGameCube(GestureController.SwipeData swipeData) {
        PlaneFloat cameraPlane = classMediator.camera.getPlane();
        Vector3DFloat boardCenter_O = new Vector3DFloat(0, 0, 0);
        Vector3DFloat boardAxe_X = new Vector3DFloat(1, 0, 0);
        Vector3DFloat boardAxe_Z = new Vector3DFloat(0, 0, 1);
        Vector3DFloat boardCenter_O_projection = LinearAlgebra.getPointProjectionOnPlain(cameraPlane, boardCenter_O);
        Vector3DFloat boardAxe_X_projection = LinearAlgebra.getPointProjectionOnPlain(cameraPlane, boardAxe_X).diff(boardCenter_O_projection);
        Vector3DFloat boardAxe_Z_projection = LinearAlgebra.getPointProjectionOnPlain(cameraPlane, boardAxe_Z).diff(boardCenter_O_projection);

        Vector3DFloat[] corners = classMediator.camera.findCorners();
        Vector3DFloat cameraX = corners[3].diff(corners[1]);
        Vector3DFloat cameraY = corners[0].diff(corners[1]);


        Vector2DFloat boardAxe_X_projection2D_norm = new Vector2DFloat(boardAxe_X_projection.scalarProd(cameraX.norm()),
                boardAxe_X_projection.scalarProd(cameraY.norm())).norm();
        Vector2DFloat boardAxe_Z_projection2D_norm = new Vector2DFloat(boardAxe_Z_projection.scalarProd(cameraX.norm()),
                boardAxe_Z_projection.scalarProd(cameraY.norm())).norm();

        Vector2DFloat swipeVect_norm = new Vector2DFloat(swipeData.dx, swipeData.dy).norm();

        float cosX = boardAxe_X_projection2D_norm.scalarProd(swipeVect_norm);
        float cosZ = boardAxe_Z_projection2D_norm.scalarProd(swipeVect_norm);

        float straight_coeff = 0.72f;
        if (cosX > straight_coeff) {
            pushAnimation(Scene.AnimationType.GAME_CUBE_RIGHT);
        }
        else if (cosX < -straight_coeff) {
            pushAnimation(Scene.AnimationType.GAME_CUBE_LEFT);
        }
        else if (cosZ > straight_coeff) {
            pushAnimation(Scene.AnimationType.GAME_CUBE_DOWN);
        }
        else if (cosZ < -straight_coeff) {
            pushAnimation(Scene.AnimationType.GAME_CUBE_UP);
        }
    }

    private void pushAnimation(Scene.AnimationType animationType) {
        classMediator.scene.pushAnimation(animationType);
    }

    private void startNewGame(GameBoard.GameInfo gameInfo) {
        classMediator.gameBoard.chooseGameType(gameInfo);
        classMediator.gameInterface.getSideMenu().closeMenu();
        classMediator.gameInterface.setStartButtonCondition(StartButton.Status.READY_TO_START);
    }

    public void restartGame() {
        gameStatus = GameStatus.READY_TO_START;
        //openGLRend.gameInterface.setPlayButtonCondition(PlayButton.Status.READY_TO_START);
        classMediator.gameBoard.prepareNewGame();
    }

    public void pauseGame() {
        gameStatus = GameStatus.PAUSED;
        classMediator.gameInterface.setStartButtonCondition(StartButton.Status.PAUSED);
        classMediator.gameBoard.pauseGame();
    }

    private void resumeGame() {
        gameStatus = GameStatus.RUNNING;
        classMediator.gameBoard.resumeGame();
    }

    private void exitGame() {
        classMediator.gameBoard.saveRecordsIfWereChanged();
        classMediator.gameBoard.switchOffCubeController();
        classMediator.gameActivity.finish();
    }


}
