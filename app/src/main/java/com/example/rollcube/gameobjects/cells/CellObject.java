package com.example.rollcube.gameobjects.cells;

import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.GameTextureObject;
import com.example.rollcube.managers.MainManager;


public abstract class CellObject extends GameTextureObject {

    protected CellCoords cellCoords;

    public CellObject(MainManager mainManager, int n, int m) {
        super(mainManager);
        cellCoords = new CellCoords(n, m);

        Matrix.setIdentityM(I1, 0);
        Matrix.setIdentityM(I2, 0);

        float dX = n * GameBoard.TILE_SIDE + (n + 1) * GameBoard.GAP_WIDTH;
        float dZ = m * GameBoard.TILE_SIDE + (m + 1) * GameBoard.GAP_WIDTH;
        setVertices(dX, dZ);
    }

    public CellObject(MainManager mainManager, int n, int m, GameBoard.GameType gameType) {
        super(mainManager, gameType);
        cellCoords = new CellCoords(n, m);

        Matrix.setIdentityM(I1, 0);
        Matrix.setIdentityM(I2, 0);

        float dX = n * GameBoard.TILE_SIDE + (n + 1) * GameBoard.GAP_WIDTH;
        float dZ = m * GameBoard.TILE_SIDE + (m + 1) * GameBoard.GAP_WIDTH;
        setVertices(dX, dZ);
    }


    public abstract void setVertices(float dx, float dz);

    @Override
    public void setVertices() {}


    public void moveTo(int n, int m) {
        Matrix.setIdentityM(I1, 0);
        float dX = (n - cellCoords.n) * GameBoard.CELL_SIDE;
        float dZ = (m - cellCoords.m) * GameBoard.CELL_SIDE;
        Matrix.translateM(I1, 0, dX, 0, dZ);
        Matrix.multiplyMM(mModelMatrix, 0, I1, 0, mModelMatrix, 0);
        cellCoords.n = n;
        cellCoords.m = m;
    }


    public void setGameType(GameBoard.GameType gameType) {
        this.gameType = gameType;
    }

    public CellCoords getCoords() {
        return cellCoords;
    }


    public static class CellCoords {
        public int n;
        public int m;

        public CellCoords(int n, int m) {
            this.n = n;
            this.m = m;
        }

    }




}
