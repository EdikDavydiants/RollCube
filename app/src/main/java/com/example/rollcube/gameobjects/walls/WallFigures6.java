package com.example.rollcube.gameobjects.walls;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.TransparentDrawingData;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.managers.TexturesIdData;
import com.example.rollcube.gameobjects.sides.SideFigures6;

import java.util.LinkedList;
import java.util.Random;


public class WallFigures6 extends Wall {


    public WallFigures6(MainManager mainManager, Wall.Type type) {
        super(mainManager, type, GameBoard.GameType.FIGURES_6);
    }



    @Override
    public void draw() {
        moveAnimation();

        int uMatrixLocation = mainManager.uMatrixLocation_tp;
        float[] mProjectionMatrix = mainManager.PROJ_matrix;
        float[] mViewMatrix = mainManager.VIEW_matrix;
        LinkedList<TransparentDrawingData> transparentDrawingDataList = mainManager.transparentDrawingDataList;
        TexturesIdData texturesIdData = mainManager.texturesId;

        Matrix.multiplyMM(mMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mMatrix, 0);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0);

        if (!isDeactivated()) {
            SideFigures6 side = (SideFigures6) this.side;
            SideFigures6.SignTypeFigures6 signType =  side.getSignType();

            boolean isFirstExist = true;
            boolean isSecondExist = true;

            if (n_vect != null) {
                if (type == Type.N) {
                    if (zVect.scalarProd(n_vect) > 0) {
                        isFirstExist = false;
                    }
                    else if (zVect.scalarProd(n_vect) < 0) {
                        isSecondExist = false;
                    }
                } else {
                    if (xVect.scalarProd(n_vect) < 0) {
                        isFirstExist = false;
                    }
                    else if (xVect.scalarProd(n_vect) > 0) {
                        isSecondExist = false;
                    }
                }
            }

            if(isTransparent) {
                switch(signType) {
                    case CROSS:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_cross, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_cross, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case CIRCLE:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_circle, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_circle, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case SQUARE:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_square, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_square, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case FOUR_CROSSES:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_four_crosses, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_four_crosses, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case FOUR_CIRCLES:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_four_circles, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_four_circles, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case FOUR_SQUARES:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_four_squares, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_four_squares, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                }
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 8, 4, texturesIdData.tr_wall_side, uMatrixLocation, mMatrix, true, false));
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 12, 4, texturesIdData.tr_wall_cap, uMatrixLocation, mMatrix, true, false));
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 16, 4, texturesIdData.tr_wall_side, uMatrixLocation, mMatrix, true, false));
            }
            else {
                switch (signType){
                    case CROSS:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_cross);
                        break;
                    case CIRCLE:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_circle);
                        break;
                    case SQUARE:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_square);
                        break;
                    case FOUR_CROSSES:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_four_crosses);
                        break;
                    case FOUR_CIRCLES:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_four_circles);
                        break;
                    case FOUR_SQUARES:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_four_squares);
                        break;
                }
                if (!isFirstExist) {
                    glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                }
                if (!isSecondExist) {
                    glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);
                }

                glBindTexture(GL_TEXTURE_2D, texturesIdData.wall_side);
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 4);

                glBindTexture(GL_TEXTURE_2D, texturesIdData.gap_cap);
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 12, 4);

                glBindTexture(GL_TEXTURE_2D, texturesIdData.wall_side);
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 16, 4);
            }
        }
    }



    @Override
    public void setRandomSide(Random rndm) {
        side = new SideFigures6().generateRandom(rndm);
    }



}
