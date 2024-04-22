package com.example.rollcube.gameobjects.gamecubes;

import com.example.rollcube.linearalgebra.Matrix3x3Int;
import com.example.rollcube.gameobjects.sides.Side;


public class Position {

    private final Matrix3x3Int Mx = new Matrix3x3Int(
            1, 0, 0,
            0, 0, -1,
            0, 1, 0
    );
    private final Matrix3x3Int Mx2 = new Matrix3x3Int(
            1, 0, 0,
            0, 0, 1,
            0, -1, 0
    );

    private final Matrix3x3Int My = new Matrix3x3Int(
            0, 0, 1,
            0, 1, 0,
            -1, 0, 0
    );
    private final Matrix3x3Int My2 = new Matrix3x3Int(
            0, 0, -1,
            0, 1, 0,
            1, 0, 0
    );

    private final Matrix3x3Int Mz = new Matrix3x3Int(
            0, -1, 0,
            1, 0, 0,
            0, 0, 1
    );
    private final Matrix3x3Int Mz2 = new Matrix3x3Int(
            0, 1, 0,
            -1, 0, 0,
            0, 0, 1
    );

    private Side leftSide;
    private Side rightSide;
    private Side upSide;
    private Side downSide;
    private Side backSide;
    private Side forwardSide;
    private Matrix3x3Int matrixPosition;


    public Position(Side leftSide, Side rightSide, Side upSide, Side downSide, Side backSide, Side forwardSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.upSide = upSide;
        this.downSide = downSide;
        this.backSide = backSide;
        this.forwardSide = forwardSide;

        matrixPosition = new Matrix3x3Int(
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        );
    }




    void rotateX() {
        Side side = backSide;
        backSide = upSide;
        upSide = forwardSide;
        forwardSide = downSide;
        downSide = side;

        leftSide.rotate(Mx);
        rightSide.rotate(Mx);
        backSide.rotate(Mx);
        forwardSide.rotate(Mx);
        upSide.rotate(Mx);
        downSide.rotate(Mx);

        matrixPosition = Mx.product(matrixPosition);
    }

    void rotateX2() {
        Side side = backSide;
        backSide = downSide;
        downSide = forwardSide;
        forwardSide = upSide;
        upSide = side;

        leftSide.rotate(Mx2);
        rightSide.rotate(Mx2);
        backSide.rotate(Mx2);
        forwardSide.rotate(Mx2);
        upSide.rotate(Mx2);
        downSide.rotate(Mx2);

        matrixPosition = Mx2.product(matrixPosition);
    }

    void rotateY() {
        Side side = leftSide;
        leftSide = downSide;
        downSide = rightSide;
        rightSide = upSide;
        upSide = side;

        leftSide.rotate(My);
        rightSide.rotate(My);
        backSide.rotate(My);
        forwardSide.rotate(My);
        upSide.rotate(My);
        downSide.rotate(My);

        matrixPosition = My.product(matrixPosition);
    }

    void rotateY2() {
        Side side = leftSide;
        leftSide = upSide;
        upSide = rightSide;
        rightSide = downSide;
        downSide = side;

        leftSide.rotate(My2);
        rightSide.rotate(My2);
        backSide.rotate(My2);
        forwardSide.rotate(My2);
        upSide.rotate(My2);
        downSide.rotate(My2);

        matrixPosition = My2.product(matrixPosition);
    }

    void rotateZ() {
        Side side = backSide;
        backSide = leftSide;
        leftSide = forwardSide;
        forwardSide = rightSide;
        rightSide = side;

        leftSide.rotate(Mz);
        rightSide.rotate(Mz);
        backSide.rotate(Mz);
        forwardSide.rotate(Mz);
        upSide.rotate(Mz);
        downSide.rotate(Mz);

        matrixPosition = Mz.product(matrixPosition);
    }

    void rotateZ2() {
        Side side = backSide;
        backSide = rightSide;
        rightSide = forwardSide;
        forwardSide = leftSide;
        leftSide = side;

        leftSide.rotate(Mz2);
        rightSide.rotate(Mz2);
        backSide.rotate(Mz2);
        forwardSide.rotate(Mz2);
        upSide.rotate(Mz2);
        downSide.rotate(Mz2);

        matrixPosition = Mz2.product(matrixPosition);
    }




    public Side getLeftSide() {
        return leftSide;
    }
    public Side getRightSide() {
        return rightSide;
    }
    public Side getUpSide() {
        return upSide;
    }
    public Side getDownSide() {
        return downSide;
    }
    public Side getBackSide() {
        return backSide;
    }
    public Side getForwardSide() {
        return forwardSide;
    }



    public long getMatrixPositionData(){
        return  (long) (matrixPosition.a[0][0] + 2) * 1000*1000*100 + (long) (matrixPosition.a[0][1] + 2) * 1000*1000*10 + (long) (matrixPosition.a[0][2] + 2) * 1000*1000 +
                (long) (matrixPosition.a[1][0] + 2) * 1000*100 + (long) (matrixPosition.a[1][1] + 2) * 1000*10 + (long) (matrixPosition.a[1][2] + 2) * 1000 +
                (long) (matrixPosition.a[2][0] + 2) * 100 + (long) (matrixPosition.a[2][1] + 2) * 10 + (long) (matrixPosition.a[2][2] + 2);
    }







}