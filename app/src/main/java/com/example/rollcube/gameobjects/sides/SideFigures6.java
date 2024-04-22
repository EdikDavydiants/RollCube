package com.example.rollcube.gameobjects.sides;

import com.example.rollcube.linearalgebra.Matrix3x3Int;
import com.example.rollcube.linearalgebra.Point3DInt;

import java.util.Random;


public class SideFigures6 extends Side {
    public enum SignTypeFigures6 { CROSS, CIRCLE, SQUARE, FOUR_CROSSES, FOUR_CIRCLES, FOUR_SQUARES;
        public static SignTypeFigures6 getSignType(int number) {
            SignTypeFigures6 signTypeFigures6 = null;
            for (SignTypeFigures6 signTypeFigures6Itr : SignTypeFigures6.values()) {
                if(number == signTypeFigures6Itr.ordinal()) {
                    signTypeFigures6 = signTypeFigures6Itr;
                    break;
                }
            }
            return signTypeFigures6;
        }
        public static SignTypeFigures6 getRandomSign(Random rand) {
            int sideIdx = rand.nextInt(6);
            return getSignType(sideIdx);
        }
    }

    private SignTypeFigures6 signType;


    public SideFigures6() {

    }

    public SideFigures6(SignTypeFigures6 signType) {
        this.signType = signType;
    }



    public void rotate(Matrix3x3Int rotM) {}

    public boolean compare(Side side) {
        return ((SideFigures6) side).signType == signType;
    }

    @Override
    public Side generateRandom(Random rand) {
        return new SideFigures6(SignTypeFigures6.getRandomSign(rand));
    }

    @Override
    public Side generateRandomXY(Random rand) {return null;}

    @Override
    public Side generateRandomXZ(Random rand) {return null;}

    @Override
    public Side generateRandomYZ(Random rand) {return null;}

    public SignTypeFigures6 getSignType() {
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
