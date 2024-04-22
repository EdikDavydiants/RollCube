package com.example.rollcube.gameobjects.staticobjects;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.GameTextureObject;


public class BoardSide extends GameTextureObject {

    public BoardSide(MainManager mainManager) {
        super(mainManager);
        float gapWidth = GameBoard.GAP_WIDTH;
        float bigWidthX =  GameBoard.N * GameBoard.CELL_SIDE - GameBoard.GAP_WIDTH;
        float bigWidthZ =  GameBoard.M * GameBoard.CELL_SIDE - GameBoard.GAP_WIDTH;
        float height = GameBoard.TILE_SIDE;

        initVertices(gapWidth, height, bigWidthX, bigWidthZ);
    }



    @Override
    public void setVertices() {}

    @Override
    public void setIndexes() {
        indexArr = new short[0];
    }

    public void initVertices(float gapWidth, float height, float bigWidthX, float bigWidthZ) {
        verticesList.add(new Vector3DFloat(0, 0, 0));
        verticesList.add(new Vector3DFloat(0, -height, 0));
        verticesList.add(new Vector3DFloat(0, 0, bigWidthZ + 2*gapWidth));
        verticesList.add(new Vector3DFloat(0, -height, bigWidthZ + 2*gapWidth));

        verticesList.add(new Vector3DFloat(0, 0, bigWidthZ + 2*gapWidth));
        verticesList.add(new Vector3DFloat(0, -height, bigWidthZ + 2*gapWidth));
        verticesList.add(new Vector3DFloat(bigWidthX + 2*gapWidth, 0, bigWidthZ + 2*gapWidth));
        verticesList.add(new Vector3DFloat(bigWidthX + 2*gapWidth, -height, bigWidthZ + 2*gapWidth));


        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));

        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
    }

    @Override
    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.board_side);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex + 4, 4);

    }




}
