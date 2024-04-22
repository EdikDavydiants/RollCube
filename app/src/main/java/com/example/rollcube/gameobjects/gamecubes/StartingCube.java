package com.example.rollcube.gameobjects.gamecubes;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.sides.StartingSide;


public class StartingCube extends GameCube {

    public StartingCube(MainManager mainManager, int n, int m, GameBoard gameBoard) {
        super(mainManager, n, m, gameBoard, GameBoard.GameType.STARTING);
    }



    @Override
    public void setDefaultPosition() {
        StartingSide leftSide = new StartingSide();
        StartingSide rightSide = new StartingSide();
        StartingSide downSide = new StartingSide();
        StartingSide upSide = new StartingSide();
        StartingSide backSide = new StartingSide();
        StartingSide forwardSide = new StartingSide();

        position = new Position(leftSide, rightSide, upSide, downSide, backSide, forwardSide);
    }





    @Override
    public void draw() {
        moveAnimation();

        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_dice_empty);

        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 2, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 10, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 12, 4);
    }



    @Override
    protected void onCubeAnimEnd() {}


}
