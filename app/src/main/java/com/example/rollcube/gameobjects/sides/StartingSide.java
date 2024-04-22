package com.example.rollcube.gameobjects.sides;

import com.example.rollcube.linearalgebra.Matrix3x3Int;

import java.util.Random;


public class StartingSide extends Side {




    @Override
    public void rotate(Matrix3x3Int rotM) {
    }

    @Override
    public boolean compare(Side side) {
        return false;
    }


    @Override
    public Side generateRandom(Random rand) {
        return null;
    }

    @Override
    public Side generateRandomXY(Random rand) {
        return null;
    }

    @Override
    public Side generateRandomXZ(Random rand) {
        return null;
    }

    @Override
    public Side generateRandomYZ(Random rand) {
        return null;
    }


    @Override
    public int saveData() {
        return 0;
    }

    @Override
    public Side uploadData(int data) {
        return null;
    }

}