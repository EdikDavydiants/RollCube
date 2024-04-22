package com.example.rollcube.gameobjects.cells;

import com.example.rollcube.Drawable;
import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.gamecubes.GameCube;
import com.example.rollcube.gameobjects.platforms.Platform;
import com.example.rollcube.gameobjects.twisttiles.TwistTile;
import com.example.rollcube.gameobjects.walls.Wall;
import com.example.rollcube.linearalgebra.Vector3DFloat;

import java.util.Random;


public class Cell implements Drawable {
    private final Tile tile;
    private WallContainer leftWallContainer;
    private WallContainer rightWallContainer;
    private WallContainer forwardWallContainer;
    private WallContainer backWallContainer;
    private TwistTile twistTile = null;
    private Platform platform = null;
    private final int n;
    private final int m;
    private final Vector3DFloat leftWallVect = new Vector3DFloat(-1f, 0f, 0f);
    private final Vector3DFloat rightWallVect = new Vector3DFloat(1f, 0f, 0f);
    private final Vector3DFloat backWallVect = new Vector3DFloat(0f, 0f, 1f);
    private final Vector3DFloat forwardWallVect = new Vector3DFloat(0f, 0f, -1f);


    public Cell(MainManager mainManager, int n, int m, GameBoard.GameType gameType) {
        this.n = n;
        this.m = m;
        tile = new Tile(mainManager, n, m, gameType);
    }



    @Override
    public void draw() {
        if(platform != null) {
            platform.draw();
        } else if (isTwistTileDeactivated()) {
            tile.draw();
        }
    }


    public void activateTile(Random rndm) {
        if (twistTile != null) {
            twistTile.setRandomSide(rndm);
            twistTile.activate();
        }
    }


    public void activatePlatform() {
        platform.activate();
    }

    public void deactivatePlatform() {
        platform.deactivate();
    }


    public void createWall(Wall.WallSide wallSide) {
        switch(wallSide) {
            case LEFT:
                leftWallContainer = new WallContainer();
                break;
            case RIGHT:
                rightWallContainer = new WallContainer();
                break;
            case FORWARD:
                forwardWallContainer = new WallContainer();
                break;
            case BACK:
                backWallContainer = new WallContainer();
                break;
        }
    }


    public void createPlatform(MainManager mainManager, Platform.PlatformType platformType) {
        platform = new Platform(mainManager, n, m, platformType);
    }



    public TwistTile getTwistTile() {
        return twistTile;
    }


    public int checkDetails(GameCube gameCube) {
        if(platform != null && platform.isWaiting()) {
            platform.rotate(gameCube);
            return 0;
        }
        else {
            boolean isTileMatch = isTwistTileActivated() &&
                    gameCube.checkTileMatch(twistTile.getSide());
            boolean isForwardWallMatch = isWallActivated(forwardWallContainer) &&
                    gameCube.checkWallMatch(Wall.WallSide.FORWARD, forwardWallContainer.wall.getSide());
            boolean isBackWallMatch = isWallActivated(backWallContainer) &&
                    gameCube.checkWallMatch(Wall.WallSide.BACK, backWallContainer.wall.getSide());
            boolean isLeftWallMatch = isWallActivated(leftWallContainer) &&
                    gameCube.checkWallMatch(Wall.WallSide.LEFT, leftWallContainer.wall.getSide());
            boolean isRightWallMatch = isWallActivated(rightWallContainer) &&
                    gameCube.checkWallMatch(Wall.WallSide.RIGHT, rightWallContainer.wall.getSide());

            int points = 0;

            if (isTileMatch) {
                twistTile.deactivateQuickly();
                points++;
            }
            if (isForwardWallMatch) {
                forwardWallContainer.wall.deactivate();
                points++;
            }
            if (isBackWallMatch) {
                backWallContainer.wall.deactivate();
                points++;
            }
            if (isLeftWallMatch) {
                leftWallContainer.wall.deactivate();
                points++;
            }
            if (isRightWallMatch) {
                rightWallContainer.wall.deactivate();
                points++;
            }
            return points;
        }
    }


    public void disconnectDetails() {
        twistTile = null;
        leftWallContainer.wall = null;
        rightWallContainer.wall = null;
        forwardWallContainer.wall = null;
        backWallContainer.wall = null;
    }

    public void setTransparency(Vector3DFloat cameraN) {
        if(!isWallDeactivated(leftWallContainer)) {
            leftWallContainer.wall.setN_vect(leftWallVect);
            leftWallContainer.wall.setTransparency(cameraN);
        }
        if(!isWallDeactivated(rightWallContainer)) {
            rightWallContainer.wall.setN_vect(rightWallVect);
            rightWallContainer.wall.setTransparency(cameraN);
        }
        if(!isWallDeactivated(backWallContainer)) {
            backWallContainer.wall.setN_vect(backWallVect);
            backWallContainer.wall.setTransparency(cameraN);
        }
        if(!isWallDeactivated(forwardWallContainer)) {
            forwardWallContainer.wall.setN_vect(forwardWallVect);
            forwardWallContainer.wall.setTransparency(cameraN);
        }
    }

    public boolean isTwistTileDeactivated() {
        return twistTile == null || twistTile.isDeactivated();
    }
    public boolean isTwistTileActivated() {
        return twistTile != null && twistTile.isActivated();
    }
    public boolean isWallDeactivated(WallContainer wallContainer) {
        return wallContainer.wall == null || wallContainer.wall.isDeactivated();
    }
    public boolean isWallActivated(WallContainer wallContainer) {
        return wallContainer.wall != null && wallContainer.wall.isActivated();
    }
    public boolean isLeftWallDeactivated() {
        return leftWallContainer.wall == null || leftWallContainer.wall.isDeactivated();
    }
    public boolean isRightWallDeactivated() {
        return rightWallContainer.wall == null || rightWallContainer.wall.isDeactivated();
    }
    public boolean isForwardWallDeactivated() {
        return forwardWallContainer.wall == null || forwardWallContainer.wall.isDeactivated();
    }
    public boolean isBackWallDeactivated() {
        return backWallContainer.wall == null || backWallContainer.wall.isDeactivated();
    }

    public void collectMovingDetailsAndActivate(GameBoard.MovingDetails movingDetails, Random rndm) {
        twistTile = movingDetails.findFreeTwistTile();
        twistTile.moveTo(n, m);

        leftWallContainer.wall = movingDetails.findFreeWallM();
        leftWallContainer.wall.moveTo(n, m);
        leftWallContainer.wall.setRandomSide(rndm);
        leftWallContainer.wall.activate();

        rightWallContainer.wall = movingDetails.findFreeWallM();
        rightWallContainer.wall.moveTo(n + 1, m);
        rightWallContainer.wall.setRandomSide(rndm);
        rightWallContainer.wall.activate();

        backWallContainer.wall = movingDetails.findFreeWallN();
        backWallContainer.wall.moveTo(n, m + 1);
        backWallContainer.wall.setRandomSide(rndm);
        backWallContainer.wall.activate();

        forwardWallContainer.wall = movingDetails.findFreeWallN();
        forwardWallContainer.wall.moveTo(n, m);
        forwardWallContainer.wall.setRandomSide(rndm);
        forwardWallContainer.wall.activate();
    }


    public void setTileGameType(GameBoard.GameType gameType) {
        tile.setGameType(gameType);
        if(platform != null) {
            platform.setGameType(gameType);
        }
    }


    public static class WallContainer {
        public Wall wall = null;
    }

    public WallContainer getRightWallContainer() {
        return rightWallContainer;
    }
    public WallContainer getBackWallContainer() {
        return backWallContainer;
    }

    public void setLeftWallContainer(WallContainer wallContainer) {
        leftWallContainer = wallContainer;
    }
    public void setForwardWallContainer(WallContainer wallContainer) {
        forwardWallContainer = wallContainer;
    }




}
