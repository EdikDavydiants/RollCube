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
import com.example.rollcube.gameobjects.sides.SideXYZ;
import com.example.rollcube.linearalgebra.Point3DInt;

import java.util.LinkedList;
import java.util.Random;


public class WallXYZ extends Wall {


    public WallXYZ(MainManager mainManager, Wall.Type type) {
        super(mainManager, type, GameBoard.GameType.XYZ);
    }



    @Override
    public void setRandomSide(Random rndm) {
        switch(type) {
            case N:
                side = new SideXYZ().generateRandomXZ(rndm);
                break;
            case M:
                side = new SideXYZ().generateRandomYZ(rndm);
                break;
        }
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
            SideXYZ side = (SideXYZ) this.side;
            SideXYZ.SignTypeXYZ signType =  side.getSignType();
            Point3DInt orientStrght = side.getOrientStrght();
            Point3DInt orientDiag = side.getOrientDiag();

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

            int texture_id = 0;
            int texture_tr_id = 0;
            switch(signType) {
                case X:
                    texture_id = texturesIdData.tile_XYZ_X;
                    texture_tr_id = texturesIdData.tile_tr_XYZ_X;
                    break;
                case Y:
                    if (orientStrght.c == 1) {
                        texture_id = texturesIdData.tile_XYZ_Y3;//
                        texture_tr_id = texturesIdData.tile_tr_XYZ_Y3;//
                    } else if (orientStrght.c == -1) {
                        texture_id = texturesIdData.tile_XYZ_Y1;//
                        texture_tr_id = texturesIdData.tile_tr_XYZ_Y1;//
                    } else if (orientStrght.a == 1) {
                        texture_id = texturesIdData.tile_XYZ_Y2;//
                        texture_tr_id = texturesIdData.tile_tr_XYZ_Y2;//
                    } else if (orientStrght.a == -1) {
                        texture_id = texturesIdData.tile_XYZ_Y4;//
                        texture_tr_id = texturesIdData.tile_tr_XYZ_Y4;//
                    } else if (orientStrght.b == 1) {
                        texture_id = texturesIdData.tile_XYZ_Y4;
                        texture_tr_id = texturesIdData.tile_tr_XYZ_Y4;
                    } else if (orientStrght.b == -1) {
                        texture_id = texturesIdData.tile_XYZ_Y2;
                        texture_tr_id = texturesIdData.tile_tr_XYZ_Y2;
                    }
                    break;
                case Z:
                    switch(type) {
                        case N:
                            if (orientStrght.c != 0) {
                                if (orientDiag.c * orientDiag.a == 1) {
                                    texture_id = texturesIdData.tile_XYZ_Z2;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z2;
                                } else {
                                    texture_id = texturesIdData.tile_XYZ_Z4;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z4;
                                }
                            } else {
                                if (orientDiag.c * orientDiag.a == 1) {
                                    texture_id = texturesIdData.tile_XYZ_Z3;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z3;
                                } else {
                                    texture_id = texturesIdData.tile_XYZ_Z1;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z1;
                                }
                            }
                            break;
                        case M:
                            if (orientStrght.c != 0) {
                                if (orientDiag.c * orientDiag.b == 1) {
                                    texture_id = texturesIdData.tile_XYZ_Z4;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z4;
                                } else {
                                    texture_id = texturesIdData.tile_XYZ_Z2;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z2;
                                }
                            } else {
                                if (orientDiag.c * orientDiag.b == 1) {
                                    texture_id = texturesIdData.tile_XYZ_Z1;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z1;
                                } else {
                                    texture_id = texturesIdData.tile_XYZ_Z3;
                                    texture_tr_id = texturesIdData.tile_tr_XYZ_Z3;
                                }
                            }
                            break;
                    }
                    break;
            }

            if(isTransparent) {
                if (isFirstExist) {
                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texture_tr_id, uMatrixLocation, mMatrix, true, true));
                }
                if (isSecondExist) {
                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texture_tr_id, uMatrixLocation, mMatrix, true, true));
                }
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 8, 4, texturesIdData.tr_wall_side, uMatrixLocation, mMatrix, true, false));
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 12, 4, texturesIdData.tr_wall_cap, uMatrixLocation, mMatrix, true, false));
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 16, 4, texturesIdData.tr_wall_side, uMatrixLocation, mMatrix, true, false));
            }

            else {
                glBindTexture(GL_TEXTURE_2D, texture_id);

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



}
