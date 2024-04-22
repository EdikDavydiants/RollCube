package com.example.rollcube.gameobjects.gamecubes;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point3DInt;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.sides.SideDice;


public class GameCubeDice extends GameCube {

    public GameCubeDice(MainManager mainManager, int n, int m, GameBoard gameBoard) {
        super(mainManager, n, m, gameBoard, GameBoard.GameType.DICE);
    }


    @Override
    public void setDefaultPosition() {
        SideDice leftSide = new SideDice(SideDice.SignTypeDice.NUMB_4);
        SideDice rightSide = new SideDice(SideDice.SignTypeDice.NUMB_3, new Point3DInt(0, 1, 1));
        SideDice downSide = new SideDice(SideDice.SignTypeDice.NUMB_5);
        SideDice upSide = new SideDice(SideDice.SignTypeDice.NUMB_2, new Point3DInt(1, 0, 0));
        SideDice backSide = new SideDice(SideDice.SignTypeDice.NUMB_1);
        SideDice forwardSide = new SideDice(SideDice.SignTypeDice.NUMB_6, new Point3DInt(0, 0, 1));

        position = new Position(leftSide, rightSide, upSide, downSide, backSide, forwardSide);
    }


    @Override
    public void draw() {
        moveAnimation();

        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_dice_side_1);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_dice_side_2);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 2, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_dice_side_6);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_dice_side_3);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_dice_side_5);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 10, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_dice_side_4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 12, 4);
    }









}
