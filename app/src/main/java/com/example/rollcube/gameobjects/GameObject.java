package com.example.rollcube.gameobjects;

import android.opengl.Matrix;

import com.example.rollcube.DrawableObject;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Vector3DFloat;

import java.util.LinkedList;


public abstract class GameObject  implements DrawableObject {

    protected LinkedList<Vector3DFloat> verticesList;
    protected short[] indexArr;
    protected int startVertex;
    protected int startIndex;

    protected float[] I1 = new float[16];
    protected float[] I2 = new float[16];
    protected float[] mModelMatrix = new float[16];
    protected float[] mMatrix = new float[16];
    protected boolean isTransparent = false;

    protected MainManager mainManager;


    public GameObject(MainManager mainManager) {
        this.mainManager = mainManager;
        verticesList = new LinkedList<>();
        Matrix.setIdentityM(mModelMatrix, 0);
    }


    public abstract void setVertices();
    public abstract void setIndexes();


    public int countVertices() {
        return verticesList.size();
    }
    public int countIndexes() {
        return indexArr.length;
    }


    protected abstract void addObjectToList(MainManager mainManager);





}
