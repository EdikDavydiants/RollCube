package com.example.rollcube.gameobjects.gamecubes;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.sides.SideFigures3;


public class GameCubeFigures3 extends GameCube {

    public GameCubeFigures3(MainManager mainManager, int n, int m, GameBoard gameBoard) {
        super(mainManager, n, m, gameBoard, GameBoard.GameType.FIGURES_3);
    }



    @Override
    public void setDefaultPosition() {
        SideFigures3 leftSide = new SideFigures3(SideFigures3.SignTypeFigures3.SQUARE);
        SideFigures3 rightSide = new SideFigures3(SideFigures3.SignTypeFigures3.SQUARE);
        SideFigures3 downSide = new SideFigures3(SideFigures3.SignTypeFigures3.CIRCLE);
        SideFigures3 upSide = new SideFigures3(SideFigures3.SignTypeFigures3.CIRCLE);
        SideFigures3 backSide = new SideFigures3(SideFigures3.SignTypeFigures3.CROSS);
        SideFigures3 forwardSide = new SideFigures3(SideFigures3.SignTypeFigures3.CROSS);

        position = new Position(leftSide, rightSide, upSide, downSide, backSide, forwardSide);
    }





    @Override
    public void draw() {
        moveAnimation();

        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_figures_side_1);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_figures_side_2);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 2, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_figures_side_1);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_figures_side_4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_figures_side_2);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 10, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_figures_side_4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 12, 4);
    }




}
