package com.example.rollcube.gameobjects.twisttiles;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.sides.SideXYZ;
import com.example.rollcube.linearalgebra.Point3DInt;

import java.util.Random;


public class TwistTileXYZ extends TwistTile {


    public TwistTileXYZ(MainManager mainManager, int n, int m) {
        super(mainManager, n, m, GameBoard.GameType.XYZ);
    }




    @Override
    public void setRandomSide(Random rndm) {
        side = new SideXYZ().generateRandomXY(rndm);
    }


    @Override
    public void draw() {
        moveAnimation();

        int uMatrixLocation = mainManager.uMatrixLocation_tp;
        float[] mProjectionMatrix = mainManager.PROJ_matrix;
        float[] mViewMatrix = mainManager.VIEW_matrix;

        Matrix.multiplyMM(mMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mMatrix, 0);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0);

        if (!isDeactivated()) {
            SideXYZ side = (SideXYZ) this.side;
            Point3DInt orientStrgth = side.getOrientStrght();
            Point3DInt orientDiag = side.getOrientDiag();
            switch(side.getSignType()) {
                case X:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_X);
                    break;
                case Y:
                    if (orientStrgth.a == 1) {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Y3);
                    } else if (orientStrgth.a == -1) {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Y1);
                    } else if (orientStrgth.b == 1) {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Y4);
                    } else {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Y2);
                    }
                    break;
                case Z:
                    if (orientStrgth.a == 1) {
                        if (orientDiag.a * orientDiag.b == 1) {
                            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Z4);
                        } else {
                            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Z2);
                        }

                    } else {
                        if (orientDiag.a * orientDiag.b == 1) {
                            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Z1);
                        } else {
                            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_Z3);
                        }
                    }
                    break;

            }
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);

            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.wall_side);
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 10);
        }
    }



}
