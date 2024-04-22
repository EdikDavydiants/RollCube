package com.example.rollcube.gameobjects;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.CubeController;
import com.example.rollcube.GameBoard;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.managers.MainManager;


public class MainBackground extends GameTextureObject {

    private final AnimationData animationData;
    private boolean isFirstDrawing = true;


    public MainBackground(MainManager mainManager, float cameraRatio, GameBoard.GameType gameType) {
        super(mainManager, gameType);
        animationData = new AnimationData();
        setVertices(cameraRatio);
    }



    @Override
    public void draw() {
        if (isAnimating()) {
            animationData.moveAnimation();
            Matrix.multiplyMM(mMatrix, 0, mModelMatrix, 0, mainManager.INTERFACE_matrix, 0);
        } else {
            checkFirstDrawing();
        }
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
        switch(gameType) {
            case STARTING:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.background_starting);
                break;
            case FIGURES_3:
            case FIGURES_6:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.background_figures);
                break;
            case DICE:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.background_dice);
                break;
            case XYZ:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.background_XYZ);
                break;
        }
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 12, 4);
    }

    private void checkFirstDrawing() {
        if(isFirstDrawing) {
            System.arraycopy(mainManager.INTERFACE_matrix, 0, mMatrix, 0, 16);
            isFirstDrawing = false;
        }
    }


    @Override
    public void setVertices() {}
    public void setVertices(float cameraRatio) {
        float k = 1.1f;

        verticesList.add(new Vector3DFloat(-1, 0, 0f).sum(new Vector3DFloat(0, 0, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 0, 0f).sum(new Vector3DFloat(0, 0, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(-1, 1, 0f).sum(new Vector3DFloat(0, 0, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 1, 0f).sum(new Vector3DFloat(0, 0, 0f)).prod(k));

        texturePointsList.add(new Point2DFloat(0, 1)); // 1
        texturePointsList.add(new Point2DFloat(1, 1)); // 2
        texturePointsList.add(new Point2DFloat(0 + 0.f, 0)); // 3
        texturePointsList.add(new Point2DFloat(1 - 0.f, 0)); // 4


        verticesList.add(new Vector3DFloat(-1, 0, 0f).sum(new Vector3DFloat(1, 0, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 0, 0f).sum(new Vector3DFloat(1, 0, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(-1, 1, 0f).sum(new Vector3DFloat(1, 0, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 1, 0f).sum(new Vector3DFloat(1, 0, 0f)).prod(k));

        texturePointsList.add(new Point2DFloat(0, 1)); // 1
        texturePointsList.add(new Point2DFloat(1, 1)); // 2
        texturePointsList.add(new Point2DFloat(0 + 0.f, 0)); // 3
        texturePointsList.add(new Point2DFloat(1 - 0.f, 0)); // 4


        verticesList.add(new Vector3DFloat(-1, 0, 0f).sum(new Vector3DFloat(0, -1, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 0, 0f).sum(new Vector3DFloat(0, -1, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(-1, 1, 0f).sum(new Vector3DFloat(0, -1, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 1, 0f).sum(new Vector3DFloat(0, -1, 0f)).prod(k));

        texturePointsList.add(new Point2DFloat(0, 1)); // 1
        texturePointsList.add(new Point2DFloat(1, 1)); // 2
        texturePointsList.add(new Point2DFloat(0 + 0.f, 0)); // 3
        texturePointsList.add(new Point2DFloat(1 - 0.f, 0)); // 4


        verticesList.add(new Vector3DFloat(-1, 0, 0f).sum(new Vector3DFloat(1, -1, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 0, 0f).sum(new Vector3DFloat(1, -1, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(-1, 1, 0f).sum(new Vector3DFloat(1, -1, 0f)).prod(k));
        verticesList.add(new Vector3DFloat(0, 1, 0f).sum(new Vector3DFloat(1, -1, 0f)).prod(k));

        texturePointsList.add(new Point2DFloat(0, 1)); // 1
        texturePointsList.add(new Point2DFloat(1, 1)); // 2
        texturePointsList.add(new Point2DFloat(0 + 0.f, 0)); // 3
        texturePointsList.add(new Point2DFloat(1 - 0.f, 0)); // 4
    }


    @Override
    public void setIndexes()  {
        indexArr = new short[0];
    }





    public boolean isAnimating() {
        return animationData.isAnimating;
    }

    public void move(CubeController.Queue.MoveDirection move) {
        animationData.setAnimationParams(move);
    }





    public class AnimationData {
        private boolean isAnimating = false;
        private int frameCounter = 0;
        private final int maxFrameCount = 15;
        private final float[] shiftingMatrix = new float[16];
        private final float shiftX = 0.025f;
        private final float shiftY = 0.05f;


        public AnimationData() {

        }


        public void moveAnimation() {
            if(isAnimating) {
                if(frameCounter < maxFrameCount) {
                    setModelMatrixShifting();
                    frameCounter++;
                } else {
                    isAnimating = false;
                }
            }
        }

        public void setAnimationParams(CubeController.Queue.MoveDirection move) {
            frameCounter = 0;
            Matrix.setIdentityM(shiftingMatrix, 0);
            switch(move) {
                case LEFT:
                    Matrix.translateM(shiftingMatrix, 0, shiftX/maxFrameCount, shiftY/maxFrameCount, 0);
                    break;
                case RIGHT:
                    Matrix.translateM(shiftingMatrix, 0, -shiftX/maxFrameCount, -shiftY/maxFrameCount, 0);
                    break;
                case BACK:
                    Matrix.translateM(shiftingMatrix, 0,  -shiftX/maxFrameCount, shiftY/maxFrameCount, 0);
                    break;
                case FORWARD:
                    Matrix.translateM(shiftingMatrix, 0,  shiftX/maxFrameCount, -shiftY/maxFrameCount, 0);
                    break;
            }
            isAnimating = true;
        }

        public void setModelMatrixShifting() {
            Matrix.multiplyMM(mModelMatrix, 0, shiftingMatrix, 0, mModelMatrix, 0);
        }




    }






}
