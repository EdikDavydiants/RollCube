package com.example.rollcube.gameobjects.sides;

import com.example.rollcube.linearalgebra.Matrix3x3Int;
import com.example.rollcube.linearalgebra.Point3DInt;

import java.util.Random;


public class SideFigures3 extends Side {
    public enum SignTypeFigures3 {
        CROSS, CIRCLE, SQUARE;

        public static SignTypeFigures3 getSignType(int number) {
            SignTypeFigures3 signTypeFigures3 = null;
            for (SignTypeFigures3 signTypeFigures3Itr : SignTypeFigures3.values()) {
                if (number == signTypeFigures3Itr.ordinal()) {
                    signTypeFigures3 = signTypeFigures3Itr;
                    break;
                }
            }
            return signTypeFigures3;
        }

        public static SignTypeFigures3 getRandomSign(Random rand) {
            int sideIdx = rand.nextInt(3);
            return getSignType(sideIdx);
        }
    }

    private SignTypeFigures3 signType;


    public SideFigures3() {

    }

    public SideFigures3(SignTypeFigures3 signType) {
        this.signType = signType;
    }


    public void rotate(Matrix3x3Int rotM) {
    }

    public boolean compare(Side side) {
        return ((SideFigures3) side).signType == signType;
    }

    @Override
    public Side generateRandom(Random rand) {
        return new SideFigures3(SignTypeFigures3.getRandomSign(rand));
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

    public SignTypeFigures3 getSignType() {
        return signType;
    }


    public int saveData() {
        return 1000 * (0 + 2) + 100 * (0 + 2) + 10 * (0 + 2) + signType.ordinal();
    }

    public Side uploadData(int data) {
        int a = (data / 1000) % 10 - 2;
        int b = (data / 100) % 10 - 2;
        int c = (data / 10) % 10 - 2;

        SideDice.SignTypeDice sign = SideDice.SignTypeDice.getSignType(data % 10);

        return new SideDice(sign, new Point3DInt(a, b, c));
    }

}