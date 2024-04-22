package com.example.rollcube.gameobjects.gamecubes;

import android.opengl.Matrix;

import com.example.rollcube.Animation;
import com.example.rollcube.CubeController;
import com.example.rollcube.gameobjects.cells.CellObject;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.gameobjects.sides.Side;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.platforms.Platform;
import com.example.rollcube.gameobjects.walls.Wall;


public abstract class GameCube extends CellObject {
    protected GameBoard gameBoard;
    private final AnimationGlideAndRoll animationGlideAndRoll;
    private final AnimationRotateOnPlatform animationRotateOnPlatform;
    protected Position position;
    private boolean isBlocked = false;


    public GameCube(MainManager mainManager, int n, int m, GameBoard gameBoard, GameBoard.GameType gameType) {
        super(mainManager, 0, 0, gameType);
        cellCoords.n = n;
        cellCoords.m = m;
        this.gameBoard = gameBoard;

        animationGlideAndRoll = new AnimationGlideAndRoll();
        animationRotateOnPlatform = new AnimationRotateOnPlatform();

        setDefaultPosition();
        moveToStartCell(n, m);
    }



    @Override
    public void setVertices(float dx, float dz) {
        verticesList.add(new Vector3DFloat(0,0,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,0,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,100,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,100,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,100,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,100,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,0,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));

        verticesList.add(new Vector3DFloat(100,100,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,100,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,0,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,0,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,100,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,100,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));


        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));

        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
    }

    @Override
    public void setIndexes() {
        indexArr = new short[0];
    }

    public void moveToStartCell(int n, int m) {
        float dX = n * GameBoard.TILE_SIDE + n * GameBoard.GAP_WIDTH;
        float dZ = m * GameBoard.TILE_SIDE + m * GameBoard.GAP_WIDTH;

        float[] trM = new float[16];
        Matrix.setIdentityM(trM, 0);
        Matrix.translateM(trM, 0, dX, 0, dZ);
        Matrix.multiplyMM(mModelMatrix, 0, trM, 0, mModelMatrix, 0);
    }

    public abstract void setDefaultPosition();

    public boolean checkWallMatch(Wall.WallSide wallSide, Side sideToCompare) {
        if (sideToCompare == null) {
            return false;
        } else {
            Side side = null;
            switch (wallSide) {
                case LEFT:
                    side = position.getLeftSide();
                    break;
                case RIGHT:
                    side = position.getRightSide();
                    break;
                case FORWARD:
                    side = position.getForwardSide();
                    break;
                case BACK:
                    side = position.getBackSide();
                    break;
            }
            return side.compare(sideToCompare);
        }
    }

    public boolean checkTileMatch(Side sideToCompare){
        if (sideToCompare == null) {
            return false;
        } else {
            return position.getDownSide().compare(sideToCompare);
        }
    }

    public void rollCubePosition(CubeController.Queue.MoveDirection move) {
        switch (move) {
            case LEFT:
                position.rotateY2();
                break;
            case RIGHT:
                position.rotateY();
                break;
            case FORWARD:
                position.rotateX2();
                break;
            case BACK:
                position.rotateX();
                break;
        }
    }

    public void rotateOnPlatformCubePosition(Platform.PlatformType platformType) {
        switch (platformType) {
            case ROTATE_LEFT_180:
            case ROTATE_RIGHT_180:
                position.rotateZ();
                position.rotateZ();
                break;
            case ROTATE_RIGHT_90:
                position.rotateZ();
                break;
            case ROTATE_LEFT_90:
                position.rotateZ2();
                break;
        }
        animationRotateOnPlatform.setAnimationParams(platformType);
    }

    public boolean isMoveLegal(Side tileSide, CubeController.Queue.MoveDirection moveDirection) {
        boolean isMatch = false;
        Side cubeSide;
        switch(moveDirection) {
            case LEFT:
                position.rotateY2();
                cubeSide = position.getDownSide();
                isMatch = cubeSide.compare(tileSide);
                position.rotateY();
                break;
            case RIGHT:
                position.rotateY();
                cubeSide = position.getDownSide();
                isMatch = cubeSide.compare(tileSide);
                position.rotateY2();
                break;
            case FORWARD:
                position.rotateX2();
                cubeSide = position.getDownSide();
                isMatch = cubeSide.compare(tileSide);
                position.rotateX();
                break;
            case BACK:
                position.rotateX();
                cubeSide = position.getDownSide();
                isMatch = cubeSide.compare(tileSide);
                position.rotateX2();
                break;
        }
        return isMatch;
    }

    public Point2DFloat getFloatCoords() {
        float x = (cellCoords.n + 0.5f) * GameBoard.TILE_SIDE + (1 + cellCoords.n) * GameBoard.GAP_WIDTH;
        float y = (cellCoords.m + 0.5f) * GameBoard.TILE_SIDE + (1 + cellCoords.m) * GameBoard.GAP_WIDTH;
        return new Point2DFloat(x, y);
    }

    public void block() {
        isBlocked = true;
    }
    public void unblock() {
        isBlocked = false;
    }
    public boolean isBlocked() {
        return isBlocked;
    }

    public boolean isAnimating() {
        return animationGlideAndRoll.isAnimating || animationRotateOnPlatform.isAnimating;
    }




    public class AnimationRotateOnPlatform extends Animation {
        private float angle;
        private Platform.PlatformType rotateType;
        private final int frameCountMovingUpRotation = 4;
        private final int frameCountPauseBeforeRotation = frameCountMovingUpRotation + 5;
        private final int frameCountRotation = frameCountPauseBeforeRotation + 15;
        private final int frameCountPauseAfterRotation = frameCountRotation + 5;
        private final int totalRotationFrameCount = frameCountPauseAfterRotation + 4;

        public AnimationRotateOnPlatform() {


        }

        public void setAnimationParams(Platform.PlatformType platformType) {
            //if (!isAnimating && !animationDataGlideAndRoll.isAnimating) {
            if (true) {
                rotateType = platformType;
                switch (platformType) {
                    case ROTATE_RIGHT_180:
                        angle = - 180f / (frameCountPauseBeforeRotation - frameCountRotation);
                        break;
                    case ROTATE_RIGHT_90:
                        angle = - 90f / (frameCountPauseBeforeRotation - frameCountRotation);
                        break;
                    case ROTATE_LEFT_90:
                        angle = 90f / (frameCountPauseBeforeRotation - frameCountRotation);
                        break;
                    case ROTATE_LEFT_180:
                        angle = 180f / (frameCountPauseBeforeRotation - frameCountRotation);
                        break;
                }
                frameCounter = 0;
                isAnimating = true;
            }
        }

        @Override
        public void moveAnimation() {
            if (isAnimating) {
                if (frameCounter < totalRotationFrameCount) {
                    if (frameCounter < frameCountMovingUpRotation) {
                        moveUp();
                    }
                    else if (frameCounter < frameCountPauseBeforeRotation) {

                    }
                    else if (frameCounter < frameCountRotation) {
                        moveRotation(angle);
                    }
                    else if (frameCounter < frameCountPauseAfterRotation) {

                    }
                    else {
                        moveDown();
                    }
                    frameCounter++;
                }
                else {
                    isAnimating = false;
                }
            }
        }

        public void moveRotation(float angle) {
            float shiftX = cellCoords.n * GameBoard.TILE_SIDE + GameBoard.TILE_SIDE / 2 + (cellCoords.n + 1) * GameBoard.GAP_WIDTH;
            float shiftZ = cellCoords.m * GameBoard.TILE_SIDE + GameBoard.TILE_SIDE / 2 + (cellCoords.m + 1) * GameBoard.GAP_WIDTH;
            rotate(shiftX, 0f, shiftZ,
                    0f, 1f, 0f,
                    angle,
                    mModelMatrix,
                    I1, mMatrix, I2);
        }

        public void moveDown() {
            float shift = Platform.THICKNESS / frameCountMovingUpRotation;
            shift(0f, -shift, 0f, mModelMatrix, I1);
        }

        public void moveUp() {
            float shift = Platform.THICKNESS / frameCountMovingUpRotation;
            shift(0f, shift, 0f, mModelMatrix, I1);
        }

    }

    public class AnimationGlideAndRoll extends Animation {
        private float dShiftX;
        private float dShiftZ;
        private float shiftX;
        private float shiftZ;
        private float angle;
        private CubeController.Queue.MoveDirection move;


        public void setAnimationParams(CubeController.Queue.MoveDirection move) {
            frameCounter = 0;
            switch(move) {
                case LEFT:
                    this.move = CubeController.Queue.MoveDirection.LEFT;
                    dShiftX = - GameBoard.GAP_WIDTH / 3;
                    dShiftZ = 0f;
                    shiftX = cellCoords.n * GameBoard.TILE_SIDE + cellCoords.n * GameBoard.GAP_WIDTH;
                    shiftZ = 0f;
                    angle = 7.5f;
                    break;
                case RIGHT:
                    this.move = CubeController.Queue.MoveDirection.RIGHT;
                    dShiftX = GameBoard.GAP_WIDTH / 3;
                    dShiftZ = 0f;
                    shiftX = (cellCoords.n + 1) * GameBoard.TILE_SIDE + (cellCoords.n + 2) * GameBoard.GAP_WIDTH;
                    shiftZ = 0f;
                    angle = -7.5f;
                    break;
                case BACK:
                    this.move = CubeController.Queue.MoveDirection.BACK;
                    dShiftX = 0f;
                    dShiftZ = GameBoard.GAP_WIDTH / 3;
                    shiftX = 0f;
                    shiftZ = (cellCoords.m + 1) * GameBoard.TILE_SIDE + (cellCoords.m + 2) * GameBoard.GAP_WIDTH;
                    angle = 7.5f;
                    break;
                case FORWARD:
                    this.move = CubeController.Queue.MoveDirection.FORWARD;
                    dShiftX = 0f;
                    dShiftZ = - GameBoard.GAP_WIDTH / 3;
                    shiftX = 0f;
                    shiftZ = cellCoords.m * GameBoard.TILE_SIDE + cellCoords.m * GameBoard.GAP_WIDTH;
                    angle = -7.5f;
                    break;
            }
            isAnimating = true;
        }

        @Override
        public void moveAnimation() {
            if (isAnimating) {
                if (frameCounter == 0) {
                    mainManager.soundManager.playCubeFriction();
                }
                if (frameCounter == 14) {
                    mainManager.soundManager.playCubeHit();
                }

                if (frameCounter < 15) {
                    if (frameCounter < 3) {
                        setModelMatrixGlide(dShiftX, dShiftZ);
                    }
                    else {
                        setModelMatrixRoll(shiftX, shiftZ, angle);
                    }
                    frameCounter++;
                }
                else {
                    updateDataAfterRoll(move);
                    isAnimating = false;
                }
            }
        }

        public void setModelMatrixRoll(float shiftX, float shiftZ, float angle) {
            switch(move) {
                case LEFT:
                case RIGHT:
                    rotate(shiftX, 0f, shiftZ,
                            0f, 0f, 1f,
                            angle,
                            mModelMatrix,
                            I1, mMatrix, I2);
                    break;
                case BACK:
                case FORWARD:
                    rotate(shiftX, 0f, shiftZ,
                            1f, 0f, 0f,
                            angle,
                            mModelMatrix,
                            I1, mMatrix, I2);
                    break;
            }
        }

        public void setModelMatrixGlide(float dShiftX, float dShiftZ) {
            shift(dShiftX, 0f, dShiftZ, mModelMatrix, I1);
        }

        private void updateDataAfterRoll(CubeController.Queue.MoveDirection move) {
            switch (move) {
                case LEFT:
                    cellCoords.n--;
                    break;
                case RIGHT:
                    cellCoords.n++;
                    break;
                case FORWARD:
                    cellCoords.m--;
                    break;
                case BACK:
                    cellCoords.m++;
                    break;
            }
            rollCubePosition(move);
            onCubeAnimEnd();
        }
    }


    public void moveCube(CubeController.Queue.MoveDirection move) {
        if(!isAnimating()) {
            animationGlideAndRoll.setAnimationParams(move);
        }
    }

    public void moveAnimation() {
        animationRotateOnPlatform.moveAnimation();
        animationGlideAndRoll.moveAnimation();
    }

    protected void onCubeAnimEnd() {
        gameBoard.onCubeAnimEnd();
    }





}
