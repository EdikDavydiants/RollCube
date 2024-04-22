package com.example.rollcube.gameobjects.sides;

import com.example.rollcube.linearalgebra.Matrix3x3Int;

import java.util.Random;


public abstract class Side {




    public abstract void rotate(Matrix3x3Int rotM);



    public abstract Side generateRandom(Random rand);
    public abstract Side generateRandomXY(Random rand);
    public abstract Side generateRandomXZ(Random rand);
    public abstract Side generateRandomYZ(Random rand);

    public abstract boolean compare(Side side);
    public abstract int saveData();
    public abstract Side uploadData(int data);



}
