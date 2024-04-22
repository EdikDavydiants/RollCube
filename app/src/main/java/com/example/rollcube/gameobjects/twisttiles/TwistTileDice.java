package com.example.rollcube.gameobjects.twisttiles;

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

import java.util.Random;


public class TwistTileDice extends TwistTile {


    public TwistTileDice(MainManager mainManager, int n, int m) {
        super(mainManager, n, m, GameBoard.GameType.DICE);
    }




    @Override
    public void setRandomSide(Random rndm) {
        side = new SideDice().generateRandomXY(rndm);
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

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_dice_empty);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);

        if (!isDeactivated()) {
            SideDice side = (SideDice) this.side;
            Point3DInt orient;
            switch(side.getSignType()) {
                case NUMB_1:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_1);
                    break;
                case NUMB_2:
                    orient = side.getOrient();
                    if(orient.a == 1) {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_2x);
                    }
                    else {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_2y);
                    }
                    break;
                case NUMB_3:
                    orient = side.getOrient();
                    if(orient.a == orient.b) {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_3xy2);
                    }
                    else {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_3xy);
                    }
                    break;
                case NUMB_4:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_4);
                    break;
                case NUMB_5:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_5);
                    break;
                case NUMB_6:
                    orient = side.getOrient();
                    if(orient.a == 1) {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_6x);
                    }
                    else {
                        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_numb_6y);
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
