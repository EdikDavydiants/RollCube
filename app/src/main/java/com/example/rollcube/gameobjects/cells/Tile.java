package com.example.rollcube.gameobjects.cells;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public class Tile extends CellObject {


    public Tile(MainManager mainManager, int n, int m, GameBoard.GameType gameType) {
        super(mainManager, n, m);
        this.gameType = gameType;
    }



    @Override
    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
        switch(gameType) {
            case FIGURES_3:
            case FIGURES_6:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_figures_empty);
                break;
            case STARTING:
            case DICE:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_dice_empty);
                break;
            case XYZ:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.tile_XYZ_empty);
                break;
        }
        glDrawArrays(GL_TRIANGLE_STRIP, startVertex, 4);
    }



    @Override
    public void setVertices(float dx, float dz) {
        verticesList.add(new Vector3DFloat(0,0,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,0,0).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(100,0,100).prod(GameBoard.TILE_SIDE /100).sum(new Vector3DFloat(dx, 0, dz)));

        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
    }

    @Override
    public void setIndexes() {
        indexArr = new short[0];
    }



}
