package com.example.rollcube.gameobjects.gaps;

import android.opengl.Matrix;

import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.GameTextureObject;
import com.example.rollcube.managers.MainManager;


public abstract class GapObject extends GameTextureObject {
    public enum Type {N, M}

    protected Type type;
    protected int n;
    protected int m;
    protected float[] I1 = new float[16];
    protected float[] I2 = new float[16];



    public GapObject(MainManager mainManager, Type type, int n, int m) {
        super(mainManager);
        this.type = type;
        this.n = n;
        this.m = m;

        Matrix.setIdentityM(I1, 0);
        Matrix.setIdentityM(I2, 0);
    }

    public GapObject(MainManager mainManager, Type type, int n, int m, GameBoard.GameType gameType) {
        super(mainManager, gameType);
        this.type = type;
        this.n = n;
        this.m = m;

        Matrix.setIdentityM(I1, 0);
        Matrix.setIdentityM(I2, 0);
    }




    @Override
    public void setVertices() {}

    public void moveTo(int n, int m) {
        Matrix.setIdentityM(I1, 0);
        float dX = (n - this.n) * GameBoard.CELL_SIDE;
        float dZ = (m - this.m) * GameBoard.CELL_SIDE;
        Matrix.translateM(I1, 0, dX, 0, dZ);
        Matrix.multiplyMM(mModelMatrix, 0, I1, 0, mModelMatrix, 0);
        this.n = n;
        this.m = m;
    }

}
