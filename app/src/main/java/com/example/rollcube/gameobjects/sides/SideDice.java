package com.example.rollcube.gameobjects.sides;

import com.example.rollcube.linearalgebra.Matrix3x3Int;
import com.example.rollcube.linearalgebra.Point3DInt;

import java.util.Random;


public class SideDice extends Side {
    public enum SignTypeDice { NUMB_1, NUMB_2, NUMB_3, NUMB_4, NUMB_5, NUMB_6;
        public static SignTypeDice getSignType(int number) {
            SignTypeDice signTypeDice = null;
            for (SignTypeDice signTypeDiceItr: SignTypeDice.values()) {
                if(number == signTypeDiceItr.ordinal()) {
                    signTypeDice = signTypeDiceItr;
                    break;
                }
            }
            return signTypeDice;
        }
        public static SignTypeDice getRandomSign(Random rand) {
            int sideIdx = rand.nextInt(6);
            return getSignType(sideIdx);
        }
    }

    private SignTypeDice signType;
    protected Point3DInt orient;


    public SideDice() {

    }

    public SideDice(SignTypeDice signType) {
        this.signType = signType;
        orient = new Point3DInt(0, 0, 0);
    }

    public SideDice(SignTypeDice signType, Point3DInt orient) {
        this.signType = signType;
        this.orient = orient;
    }



    public void rotate(Matrix3x3Int rotM) {
        orient = rotM.product(orient);
    }

    public boolean compare(Side side) {
        SideDice sideDice = (SideDice) side;
        boolean isMatch = false;
        if (signType == sideDice.signType) {
            switch (signType) {
                case NUMB_1:
                case NUMB_4:
                case NUMB_5:
                    isMatch = true;
                    break;
                case NUMB_2:
                case NUMB_3:
                case NUMB_6:
                    if (orient.scalarProd(sideDice.orient) != 0) {
                        isMatch = true;
                    }
                    break;
            }
        }
        return isMatch;
    }

    @Override
    public Side generateRandom(Random rand) {
        return null;
    }

    @Override
    public Side generateRandomXY(Random rand) {
        SignTypeDice signType = SignTypeDice.getRandomSign(rand);
        SideDice randSide = null;
        Point3DInt randOrient;
        switch (signType) {
            case NUMB_1:
            case NUMB_4:
            case NUMB_5:
                randSide = new SideDice(signType);
                break;
            case NUMB_2:
            case NUMB_6:
                if(rand.nextBoolean()) {
                    randOrient = new Point3DInt(0, 1, 0);
                }
                else {
                    randOrient = new Point3DInt(1, 0, 0);
                }
                randSide = new SideDice(signType, randOrient);
                break;
            case NUMB_3:
                if(rand.nextBoolean()) {
                    randOrient = new Point3DInt(1, 1, 0);
                }
                else {
                    randOrient = new Point3DInt(1, -1, 0);
                }
                randSide = new SideDice(signType, randOrient);
                break;
        }
        return randSide;
    }

    @Override
    public Side generateRandomXZ(Random rand) {
        SignTypeDice signType = SignTypeDice.getRandomSign(rand);
        SideDice randSide = null;
        Point3DInt randOrient;
        switch (signType) {
            case NUMB_1:
            case NUMB_4:
            case NUMB_5:
                randSide = new SideDice(signType);
                break;
            case NUMB_2:
            case NUMB_6:
                if(rand.nextBoolean()) {
                    randOrient = new Point3DInt(1, 0, 0);
                }
                else {
                    randOrient = new Point3DInt(0, 0, 1);
                }
                randSide = new SideDice(signType, randOrient);
                break;
            case NUMB_3:
                if(rand.nextBoolean()) {
                    randOrient = new Point3DInt(1, 0, 1);
                }
                else {
                    randOrient = new Point3DInt(1, 0, -1);
                }
                randSide = new SideDice(signType, randOrient);
                break;
        }
        return randSide;
    }

    @Override
    public Side generateRandomYZ(Random rand) {
        SignTypeDice signType = SignTypeDice.getRandomSign(rand);
        SideDice randSide = null;
        Point3DInt randOrient;
        switch (signType) {
            case NUMB_1:
            case NUMB_4:
            case NUMB_5:
                randSide = new SideDice(signType);
                break;
            case NUMB_2:
            case NUMB_6:
                if(rand.nextBoolean()) {
                    randOrient = new Point3DInt(0, 1, 0);
                }
                else {
                    randOrient = new Point3DInt(0, 0, 1);
                }
                randSide = new SideDice(signType, randOrient);
                break;
            case NUMB_3:
                if(rand.nextBoolean()) {
                    randOrient = new Point3DInt(0, 1, 1);
                }
                else {
                    randOrient = new Point3DInt(0, 1, -1);
                }
                randSide = new SideDice(signType, randOrient);
                break;
        }
        return randSide;
    }

    public SignTypeDice getSignType() {
        return signType;
    }


    public int saveData() {
        return 1000 * (orient.a + 2) + 100 * (orient.b + 2) + 10 * (orient.c + 2) + signType.ordinal();
    }

    public Side uploadData(int data) {
        int a = (data / 1000) % 10 - 2;
        int b = (data / 100) % 10 - 2;
        int c = (data / 10) % 10 - 2;

        SignTypeDice sign = SignTypeDice.getSignType(data % 10);

        return new SideDice(sign, new Point3DInt(a, b, c));
    }


    public Point3DInt getOrient() {
        return orient;
    }

}
