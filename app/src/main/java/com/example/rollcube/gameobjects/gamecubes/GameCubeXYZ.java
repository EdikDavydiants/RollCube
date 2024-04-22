package com.example.rollcube.gameobjects.gamecubes;

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

public class GameCubeXYZ extends GameCube{


    public GameCubeXYZ(MainManager mainManager, int n, int m, GameBoard gameBoard) {
        super(mainManager, n, m, gameBoard, GameBoard.GameType.XYZ);
    }



    @Override
    public void setDefaultPosition() {
        SideXYZ leftSide = new SideXYZ(SideXYZ.SignTypeXYZ.Y,
                new Point3DInt(0, 0, 1), new Point3DInt(0, 0, 0));
        SideXYZ rightSide = new SideXYZ(SideXYZ.SignTypeXYZ.Y,
                new Point3DInt(0, 0, -1), new Point3DInt(0, 0, 0));

        SideXYZ downSide = new SideXYZ(SideXYZ.SignTypeXYZ.Z,
                new Point3DInt(0, 1, 0), new Point3DInt(1, -1, 0));
        SideXYZ upSide = new SideXYZ(SideXYZ.SignTypeXYZ.Z,
                new Point3DInt(1, 0, 0), new Point3DInt(1, 1, 0));

        SideXYZ backSide = new SideXYZ(SideXYZ.SignTypeXYZ.X);
        SideXYZ forwardSide = new SideXYZ(SideXYZ.SignTypeXYZ.X);

        position = new Position(leftSide, rightSide, upSide, downSide, backSide, forwardSide);
    }


    @Override
    public void draw() {
        moveAnimation();

        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_XYZ_X); // b
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_XYZ_Z);  // u
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 2, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_XYZ_X); // f
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_XYZ_Y); // r
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 8, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_XYZ_Z); // d
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 10, 4);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.cube_XYZ_Y); // l
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 12, 4);
    }


}
