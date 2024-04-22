package com.example.rollcube.gameobjects.platforms;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.Animation;
import com.example.rollcube.gameobjects.cells.CellObject;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.gamecubes.GameCube;


public class Platform extends CellObject {
    public enum Status {DEACTIVATED, ACTIVATING, WAITING, ROTATING, DEACTIVATING}
    public enum PlatformType { ROTATE_RIGHT_180, ROTATE_RIGHT_90, ROTATE_LEFT_90, ROTATE_LEFT_180 }

    private Status status = Status.DEACTIVATED;
    private int condition = 0;
    private final PlatformType platformType;
    public final static float THICKNESS = 0.4f * GameBoard.TILE_SIDE;
    private final AnimationData animationData;


    public Platform(MainManager mainManager, int n, int m, PlatformType platformType) {
        super(mainManager, n, m);
        this.platformType = platformType;
        animationData = new AnimationData();
    }



    @Override
    public void draw() {
        moveAnimation();

        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);

        switch(gameType) {
            case FIGURES_3:
            case FIGURES_6:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_figures_empty);
                break;
            case STARTING:
            case DICE:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_dice_empty);
                break;
            case XYZ:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_empty);
                break;
        }
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);

        switch (platformType) {
            case ROTATE_RIGHT_180:
                switch (gameType) {
                    case FIGURES_3:
                    case FIGURES_6:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_figures_left_180);
                        break;
                    case DICE:
                    case STARTING:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_dice_left_180);
                        break;
                    case XYZ:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_xyz_left_180);
                        break;
                }
                break;
            case ROTATE_RIGHT_90:
                switch (gameType) {
                    case FIGURES_3:
                    case FIGURES_6:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_figures_left_90);
                        break;
                    case DICE:
                    case STARTING:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_dice_left_90);
                        break;
                    case XYZ:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_xyz_left_90);
                        break;
                }
                break;
            case ROTATE_LEFT_90:
                switch (gameType) {
                    case FIGURES_3:
                    case FIGURES_6:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_figures_right_90);
                        break;
                    case DICE:
                    case STARTING:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_dice_right_90);
                        break;
                    case XYZ:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_xyz_right_90);
                        break;
                }
                break;
            case ROTATE_LEFT_180:
                switch (gameType) {
                    case FIGURES_3:
                    case FIGURES_6:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_figures_right_180);
                        break;
                    case DICE:
                    case STARTING:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_dice_right_180);
                        break;
                    case XYZ:
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_xyz_right_180);
                        break;
                }
                break;
        }
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.platform_side);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 10);
    }

    @Override
    public void setVertices(float dx, float dz) {
        verticesList.add(new Vector3DFloat(0,0,0).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,1).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(1,0,0).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(1,0,1).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));

        verticesList.add(new Vector3DFloat(0,-THICKNESS,0)                              .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,GameBoard.TILE_SIDE)               .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,0)               .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));


        verticesList.add(new Vector3DFloat(0,0,0)                                            .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,0)                                      .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,GameBoard.TILE_SIDE)                             .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,GameBoard.TILE_SIDE)                       .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,0,GameBoard.TILE_SIDE)              .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,GameBoard.TILE_SIDE)        .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,0,0)                             .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,0)                       .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,0)                                            .sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,0)                                      .sum(new Vector3DFloat(dx, 0, dz)));



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
        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
    }

    @Override
    public void setIndexes() {
        indexArr = new short[0];
    }

    public boolean isDeactivated() {
        return status == Status.DEACTIVATED;
    }
    public boolean isWaiting() {
        return status == Status.WAITING;
    }

    public void activate() {
        animationData.setAnimationActivationParams();
    }

    public void deactivate() {
        if(status == Status.WAITING) {
            animationData.setAnimationDeactivationParams();
        }
    }

    public void rotate(GameCube gameCube) {
        animationData.setAnimationRotationParams();
        gameCube.rotateOnPlatformCubePosition(platformType);
    }




    public void moveAnimation() {
        animationData.moveAnimation();
    }

    public class AnimationData  extends Animation {
        private final int frameCountMovingUpRotation = 4;
        private final int frameCountPauseBeforeRotation = frameCountMovingUpRotation + 5;
        private final int frameCountRotation = frameCountPauseBeforeRotation + 15;
        private final int frameCountPauseAfterRotation = frameCountRotation + 5;
        private final int totalRotationFrameCount = frameCountPauseAfterRotation + 4;

        private final int frameCountMovingDownActivation = 4;
        private final int frameCountPauseBeforeActivation = frameCountMovingDownActivation + 2;
        private final int frameCountActivation = frameCountPauseBeforeActivation + 15;
        private final int frameCountPauseAfterActivation = frameCountActivation + 2;
        private final int totalActivationFrameCount = frameCountPauseAfterActivation + 4;
        private float angle;

        public AnimationData() {

        }


        private void setAnimationActivationParams() {
            if (!isAnimating) {
                status = Status.ACTIVATING;
                frameCounter = 0;
                angle = 180f / (frameCountPauseBeforeActivation - frameCountActivation);
                isAnimating = true;
            }
        }

        private void setAnimationDeactivationParams() {
            if (!isAnimating && status == Status.WAITING) {
                status = Status.DEACTIVATING;
                frameCounter = 0;
                angle = 180f / (frameCountPauseBeforeActivation - frameCountActivation);
                isAnimating = true;
            }
        }

        private void setAnimationRotationParams() {
            if (!isAnimating) {
                status = Status.ROTATING;
                frameCounter = 0;
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
                isAnimating = true;
            }
        }

        @Override
        public void moveAnimation() {
            if (isAnimating) {
                if (frameCounter == 0) {
                    //sound_rotating.start();
                }
                if (status == Status.ACTIVATING) {
                    if (frameCounter < totalActivationFrameCount) {
                        if (frameCounter < frameCountMovingDownActivation) {
                            moveDown();
                        }
                        else if (frameCounter < frameCountPauseBeforeActivation) {

                        }
                        else if (frameCounter < frameCountActivation) {
                            if (frameCounter == frameCountPauseBeforeActivation) {
                                mainManager.soundManager.playTileTwist();
                            }
                            moveSpin(angle);
                        }
                        else if (frameCounter < frameCountPauseAfterActivation) {

                        }
                        else {
                            moveUp();
                        }
                        frameCounter++;
                    }
                    else {
                        status = Status.WAITING;
                        isAnimating = false;
                    }
                }
                else if (status == Status.ROTATING) {
                    if (frameCounter < totalRotationFrameCount) {
                        if (frameCounter < frameCountMovingUpRotation) {
                            if (frameCounter == 0) {
                                mainManager.soundManager.playWallUp();
                            }
                            moveUp();
                        }
                        else if (frameCounter < frameCountPauseBeforeRotation) {

                        }
                        else if (frameCounter < frameCountRotation) {
                            if (frameCounter == frameCountPauseBeforeRotation) {
                                mainManager.soundManager.playPlatformRotation();
                            }
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
                        status = Status.WAITING;
                        changeCondition();
                        isAnimating = false;
                    }
                }
                else if (status == Status.DEACTIVATING) {
                    if (frameCounter < totalActivationFrameCount) {
                        if (frameCounter < frameCountMovingDownActivation) {
                            moveDown();
                        }
                        else if (frameCounter < frameCountPauseBeforeActivation) {

                        }
                        else if (frameCounter < frameCountActivation) {
                            if (frameCounter == frameCountPauseBeforeActivation) {
                                mainManager.soundManager.playTileTwist();
                            }
                            moveSpin(angle);
                        }
                        else if (frameCounter < frameCountPauseAfterActivation) {

                        }
                        else {
                            moveUp();
                        }
                        frameCounter++;
                    }
                    else {
                        status = Status.DEACTIVATED;
                        isAnimating = false;
                    }
                }
            }
        }

        public void moveSpin(float angle) {
            float shiftX = cellCoords.n * GameBoard.TILE_SIDE + GameBoard.TILE_SIDE / 2 + (cellCoords.n + 1) * GameBoard.GAP_WIDTH;
            float shiftY = -(3f/2) * THICKNESS;
            rotate(shiftX, shiftY, 0f,
                    0f, 0f, 1f,
                    angle,
                    mModelMatrix,
                    I1, mMatrix, I2);
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
            shift(0f, -THICKNESS / frameCountMovingUpRotation, 0f, mModelMatrix, I1);
        }

        public void moveUp() {
            shift(0f, THICKNESS / frameCountMovingUpRotation, 0f, mModelMatrix, I1);
        }
    }

    private void changeCondition() {
        condition++;
        switch (platformType) {
            case ROTATE_RIGHT_180:
            case ROTATE_LEFT_180:
                if(condition == 2) {
                    condition = 0;
                }
                break;
            case ROTATE_RIGHT_90:
            case ROTATE_LEFT_90:
                if(condition == 4) {
                    condition = 0;
                }
                break;
        }
    }


}
