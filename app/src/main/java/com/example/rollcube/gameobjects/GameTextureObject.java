package com.example.rollcube.gameobjects;

import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;

import java.util.ArrayList;


public abstract class GameTextureObject extends GameObject {

    protected ArrayList<Point2DFloat> texturePointsList;
    protected GameBoard.GameType gameType = GameBoard.GameType.STARTING;


    public GameTextureObject(MainManager mainManager) {
        super(mainManager);
        addObjectToList(mainManager);
        texturePointsList = new ArrayList<>();
    }

    public GameTextureObject(MainManager mainManager, GameBoard.GameType gameType) {
        super(mainManager);
        this.gameType = gameType;
        addGameTypeObjectToList(mainManager);
        texturePointsList = new ArrayList<>();
    }



    @Override
    public int initCoordsArr(int startCoord, float[] vertArr) {
        startVertex = startCoord / 5;

        int k = 0;
        for (Vector3DFloat point: verticesList) {
            vertArr[startCoord + k] = point.x;
            vertArr[startCoord + k + 1] = point.y;
            vertArr[startCoord + k + 2] = point.z;

            k += 5;
        }

        k = 0;
        for (Point2DFloat texturePoint: texturePointsList) {
            vertArr[startCoord + k + 3] = texturePoint.x;
            vertArr[startCoord + k + 4] = texturePoint.y;

            k += 5;
        }

        verticesList = null;
        texturePointsList = null;

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



    protected void addObjectToList(MainManager mainManager) {
        mainManager.addTextureObject(this);
    }

    protected void addGameTypeObjectToList(MainManager mainManager) {
        mainManager.addGameTypeObject(this);
    }

}
