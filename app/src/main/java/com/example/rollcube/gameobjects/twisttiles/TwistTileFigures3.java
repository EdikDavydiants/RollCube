package com.example.rollcube.gameobjects.twisttiles;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.sides.SideFigures3;

import java.util.Random;


public class TwistTileFigures3 extends TwistTile {

    public TwistTileFigures3(MainManager mainManager, int n, int m) {
        super(mainManager, n, m, GameBoard.GameType.FIGURES_3);
    }



    @Override
    public void setRandomSide(Random rndm) {
        side = new SideFigures3().generateRandom(rndm);
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
            SideFigures3 side = (SideFigures3) this.side;
            switch(side.getSignType()) {
                case CROSS:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_cross);
                    break;
                case CIRCLE:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_circle);
                    break;
                case SQUARE:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_square);
                    break;
            }
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);

            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.wall_side);
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 10);
        }
    }

}
