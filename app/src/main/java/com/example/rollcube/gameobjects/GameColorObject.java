package com.example.rollcube.gameobjects;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public abstract class GameColorObject extends GameObject {
    protected float[][] palette;
    protected final int R = 0;
    protected final int G = 1;
    protected final int B = 2;
    protected final int A = 3;


    public GameColorObject(MainManager mainManager) {
        super(mainManager);
        addObjectToList(mainManager);
        initPalette();
    }



    @Override
    public int initCoordsArr(int startCoord, float[] vertArr) {
        startVertex = startCoord / 3;

        int k = 0;
        for (Vector3DFloat point: verticesList) {
            vertArr[startCoord + k] = point.x;
            vertArr[startCoord + k + 1] = point.y;
            vertArr[startCoord + k + 2] = point.z;

            k += 3;
        }

        verticesList = null;
        return startCoord + k;
    }


    public int initIndexArr(int startIndex, short[] indexArr) {
        this.startIndex = startIndex;
        int length = this.indexArr.length;

        for (int i = 0; i < length; i++) {
            indexArr[startIndex + i] = this.indexArr[i];
        }

        this.indexArr = null;
        return startIndex + length;
    }



    protected abstract void initPalette();


    protected void addObjectToList(MainManager mainManager) {
        mainManager.addColorObject(this);
    }



}
