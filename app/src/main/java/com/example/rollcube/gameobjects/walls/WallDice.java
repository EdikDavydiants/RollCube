package com.example.rollcube.gameobjects.walls;


import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point3DInt;
import com.example.rollcube.gameobjects.sides.SideDice;
import com.example.rollcube.managers.TransparentDrawingData;
import com.example.rollcube.managers.TexturesIdData;

import java.util.LinkedList;
import java.util.Random;


public class WallDice extends Wall {


    public WallDice(MainManager mainManager, Wall.Type type) {
        super(mainManager, type, GameBoard.GameType.DICE);
    }



    @Override
    public void setRandomSide(Random rndm) {
        switch(type) {
            case N:
                side = new SideDice().generateRandomXZ(rndm);
                break;
            case M:
                side = new SideDice().generateRandomYZ(rndm);
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
            SideDice side = (SideDice) this.side;
            SideDice.SignTypeDice signType =  side.getSignType();
            Point3DInt orient;

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
                    case NUMB_1:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_1, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_1, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case NUMB_2:
                        orient = side.getOrient();
                        if(orient.c == 0) {
                            if (isFirstExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_2y, uMatrixLocation, mMatrix, true, true));
                            }
                            if (isSecondExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_2y, uMatrixLocation, mMatrix, true, true));
                            }
                        }
                        else {
                            if (isFirstExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_2x, uMatrixLocation, mMatrix, true, true));
                            }
                            if (isSecondExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_2x, uMatrixLocation, mMatrix, true, true));
                            }
                        }
                        break;
                    case NUMB_3:
                        orient = side.getOrient();
                        if (orient.a == 0) {
                            if(orient.b == orient.c) {
                                if (isFirstExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_3xy2, uMatrixLocation, mMatrix, true, true));
                                }
                                if (isSecondExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_3xy2, uMatrixLocation, mMatrix, true, true));
                                }
                            } //xy2
                            else {
                                if (isFirstExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_3xy, uMatrixLocation, mMatrix, true, true));
                                }
                                if (isSecondExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_3xy, uMatrixLocation, mMatrix, true, true));
                                }
                            } //xy
                        }
                        if (orient.b == 0) {
                            if(orient.a == orient.c) {
                                if (isFirstExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_3xy, uMatrixLocation, mMatrix, true, true));
                                }
                                if (isSecondExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_3xy, uMatrixLocation, mMatrix, true, true));
                                }
                            } //xy
                            else {
                                if (isFirstExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_3xy2, uMatrixLocation, mMatrix, true, true));
                                }
                                if (isSecondExist) {
                                    transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_3xy2, uMatrixLocation, mMatrix, true, true));
                                }
                            } //xy2
                        }
                        break;
                    case NUMB_4:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_4, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_4, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case NUMB_5:
                        if (isFirstExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_5, uMatrixLocation, mMatrix, true, true));
                        }
                        if (isSecondExist) {
                            transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_5, uMatrixLocation, mMatrix, true, true));
                        }
                        break;
                    case NUMB_6:
                        orient = side.getOrient();
                        if(orient.c == 0) {
                            if (isFirstExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_6y, uMatrixLocation, mMatrix, true, true));
                            }
                            if (isSecondExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_6y, uMatrixLocation, mMatrix, true, true));
                            }
                        }
                        else {
                            if (isFirstExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex, 4, texturesIdData.tile_tr_numb_6x, uMatrixLocation, mMatrix, true, true));
                            }
                            if (isSecondExist) {
                                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 4, 4, texturesIdData.tile_tr_numb_6x, uMatrixLocation, mMatrix, true, true));
                            }
                        }
                        break;

                }
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 8, 4, texturesIdData.tr_wall_side, uMatrixLocation, mMatrix, true, false));
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 12, 4, texturesIdData.tr_wall_cap, uMatrixLocation, mMatrix, true, false));
                transparentDrawingDataList.add(new TransparentDrawingData(startVertex + 16, 4, texturesIdData.tr_wall_side, uMatrixLocation, mMatrix, true, false));
            }
            else {
                switch(signType) {
                    case NUMB_1:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_1);
                        break;
                    case NUMB_2:
                        orient = side.getOrient();
                        if(orient.c == 0) {
                            glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_2y);
                            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                        }
                        else {
                            glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_2x);
                            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                        }
                        break;
                    case NUMB_3:
                        orient = side.getOrient();
                        if (orient.a == 0) {
                            if(orient.b == orient.c) {
                                glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_3xy2);
                                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                            } //xy2
                            else {
                                glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_3xy);
                                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                            } //xy
                        }
                        if (orient.b == 0) {
                            if(orient.a == orient.c) {
                                glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_3xy);
                                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                            } //xy
                            else {
                                glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_3xy2);
                                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                            } //xy2
                        }
                        break;
                    case NUMB_4:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_4);
                        break;
                    case NUMB_5:
                        glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_5);
                        break;
                    case NUMB_6:
                        orient = side.getOrient();
                        if(orient.c == 0) {
                            glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_6y);
                            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                        }
                        else {
                            glBindTexture(GL_TEXTURE_2D, texturesIdData.tile_numb_6x);
                            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
                        }
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



}
