package com.example.rollcube.gameobjects.sides;

import com.example.rollcube.linearalgebra.Matrix3x3Int;
import com.example.rollcube.linearalgebra.Point3DInt;

import java.util.Random;

public class SideXYZ extends Side {
    public enum SignTypeXYZ { X, Y, Z;

        public static SignTypeXYZ getSignType(int number) {
            SignTypeXYZ signTypeXYZ = null;
            for (SignTypeXYZ signTypeXYZItr : SignTypeXYZ.values()) {
                if(number == signTypeXYZItr.ordinal()) {
                    signTypeXYZ = signTypeXYZItr;
                    break;
                }
            }
            return signTypeXYZ;
        }
        public static SignTypeXYZ getRandomSign(Random rand) {
            int sideIdx = rand.nextInt(3);
            return getSignType(sideIdx);
        }
    }

    private SignTypeXYZ signType;
    protected Point3DInt orientStrght;
    protected Point3DInt orientDiag;


    public SideXYZ() {

    }

    public SideXYZ(SignTypeXYZ signType) {
        this.signType = signType;
        orientStrght = new Point3DInt(0, 0, 0);
        orientDiag = new Point3DInt(0, 0, 0);
    }

    public SideXYZ(SignTypeXYZ signType, Point3DInt orientStrght, Point3DInt orientDiag) {
        this.signType = signType;
        this.orientStrght = orientStrght;
        this.orientDiag = orientDiag;
    }



    public void rotate(Matrix3x3Int rotM) {
        orientStrght = rotM.product(orientStrght);
        orientDiag = rotM.product(orientDiag);
    }

    public boolean compare(Side side) {
        SideXYZ sideXYZ = (SideXYZ) side;
        boolean isMatch = false;
        if (signType == sideXYZ.signType) {
            switch (signType) {
                case X:
                    isMatch = true;
                    break;
                case Y:
                    if (orientStrght.scalarProd(sideXYZ.orientStrght) == 1) {
                        isMatch = true;
                    }
                case Z:
                    if (orientStrght.scalarProd(sideXYZ.orientStrght) != 0 &&
                            orientDiag.scalarProd(sideXYZ.orientDiag) != 0) {
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
        SignTypeXYZ signType = SignTypeXYZ.getRandomSign(rand);
        SideXYZ randSide = null;
        Point3DInt randStrghtOrient;
        Point3DInt randDiagOrient;
        switch (signType) {
            case X:
                randSide = new SideXYZ(signType);
                break;
            case Y:
                int randY = rand.nextInt(4);
                randStrghtOrient =
                        (randY == 0) ? new Point3DInt(1, 0, 0) :
                                (randY == 1) ? new Point3DInt(-1, 0, 0) :
                                        (randY == 2) ? new Point3DInt(0, 1, 0) :
                                                new Point3DInt(0, -1, 0);
                randSide = new SideXYZ(signType, randStrghtOrient, new Point3DInt(0, 0, 0));
                break;
            case Z:
                if(rand.nextBoolean()) {
                    randStrghtOrient = new Point3DInt(1, 0, 0);
                } else {
                    randStrghtOrient = new Point3DInt(0, 1, 0);
                }
                if(rand.nextBoolean()) {
                    randDiagOrient = new Point3DInt(1, 1, 0);
                } else {
                    randDiagOrient = new Point3DInt(1, -1, 0);
                }
                randSide = new SideXYZ(signType, randStrghtOrient, randDiagOrient);
                break;
        }
        return randSide;
    }

    @Override
    public Side generateRandomXZ(Random rand) {
        SignTypeXYZ signType = SignTypeXYZ.getRandomSign(rand);
        SideXYZ randSide = null;
        Point3DInt randStrghtOrient;
        Point3DInt randDiagOrient;
        switch (signType) {
            case X:
                randSide = new SideXYZ(signType);
                break;
            case Y:
                int randY = rand.nextInt(4);
                randStrghtOrient =
                        (randY == 0) ? new Point3DInt(1, 0, 0) :
                                (randY == 1) ? new Point3DInt(-1, 0, 0) :
                                        (randY == 2) ? new Point3DInt(0, 0, 1) :
                                                new Point3DInt(0, 0, -1);
                randSide = new SideXYZ(signType, randStrghtOrient, new Point3DInt(0, 0, 0));
                break;
            case Z:
                if(rand.nextBoolean()) {
                    randStrghtOrient = new Point3DInt(1, 0, 0);
                } else {
                    randStrghtOrient = new Point3DInt(0, 0, 1);
                }
                if(rand.nextBoolean()) {
                    randDiagOrient = new Point3DInt(1, 0, 1);
                } else {
                    randDiagOrient = new Point3DInt(1, 0, -1);
                }
                randSide = new SideXYZ(signType, randStrghtOrient, randDiagOrient);
                break;
        }
        return randSide;
    }

    @Override
    public Side generateRandomYZ(Random rand) {
        SignTypeXYZ signType = SignTypeXYZ.getRandomSign(rand);
        SideXYZ randSide = null;
        Point3DInt randStrghtOrient;
        Point3DInt randDiagOrient;
        switch (signType) {
            case X:
                randSide = new SideXYZ(signType);
                break;
            case Y:
                int randY = rand.nextInt(4);
                randStrghtOrient =
                        (randY == 0) ? new Point3DInt(0, 1, 0) :
                                (randY == 1) ? new Point3DInt(0, -1, 0) :
                                        (randY == 2) ? new Point3DInt(0, 0, 1) :
                                                new Point3DInt(0, 0, -1);
                randSide = new SideXYZ(signType, randStrghtOrient, new Point3DInt(0, 0, 0));
                break;
            case Z:
                if(rand.nextBoolean()) {
                    randStrghtOrient = new Point3DInt(0, 1, 0);
                } else {
                    randStrghtOrient = new Point3DInt(0, 0, 1);
                }
                if(rand.nextBoolean()) {
                    randDiagOrient = new Point3DInt(0, 1, 1);
                } else {
                    randDiagOrient = new Point3DInt(0, 1, -1);
                }
                randSide = new SideXYZ(signType, randStrghtOrient, randDiagOrient);
                break;
        }
        return randSide;
    }



    public SignTypeXYZ getSignType() {
        return signType;
    }


    public int saveData() {
        return 1000 * (orientStrght.a + 2) + 100 * (orientStrght.b + 2) + 10 * (orientStrght.c + 2) + signType.ordinal();
    }

    public Side uploadData(int data) {
        int a = (data / 1000) % 10 - 2;
        int b = (data / 100) % 10 - 2;
        int c = (data / 10) % 10 - 2;

        SideDice.SignTypeDice sign = SideDice.SignTypeDice.getSignType(data % 10);

        return new SideDice(sign, new Point3DInt(a, b, c));
    }


    public Point3DInt getOrientStrght() {
        return orientStrght;
    }
    public Point3DInt getOrientDiag() {
        return orientDiag;
    }
}
