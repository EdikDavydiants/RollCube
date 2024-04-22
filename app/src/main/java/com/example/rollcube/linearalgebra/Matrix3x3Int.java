package com.example.rollcube.linearalgebra;

/**
 * Integer 3x3 matrix.
 */
public class Matrix3x3Int {

    public int[][] a;


    /**
     * Diagonal matrix.
     * @param a diagonal elements.
     */
    public Matrix3x3Int(int a) {
        this.a = new int[][] {
                {a, 0, 0},
                {0, a, 0},
                {0, 0, a}
        };
    }

    public Matrix3x3Int(int[][] a) {
        this.a = a;
    }

    public Matrix3x3Int(
            int a00, int a01, int a02,
            int a10, int a11, int a12,
            int a20, int a21, int a22 ) {
        this.a = new int[][] {
                {a00, a01, a02},
                {a10, a11, a12},
                {a20, a21, a22}
        };
    }



    /**
     * Method product matrix on the integer point and return point as a NEW(!) object.
     */
    public Point3DInt product(Point3DInt p) {
        int a = this.a[0][0] * p.a + this.a[0][1] * p.b + this.a[0][2] * p.c;
        int b = this.a[1][0] * p.a + this.a[1][1] * p.b + this.a[1][2] * p.c;
        int c = this.a[2][0] * p.a + this.a[2][1] * p.b + this.a[2][2] * p.c;

        return new Point3DInt(a, b, c);
    }

    /**
     * Method product two matrix and returns matrix as NEW(!) object.
     */
    public Matrix3x3Int product(Matrix3x3Int M) {
        int a00 = this.a[0][0] * M.a[0][0] + this.a[0][1] * M.a[1][0] + this.a[0][2] * M.a[2][0];
        int a01 = this.a[0][0] * M.a[0][1] + this.a[0][1] * M.a[1][1] + this.a[0][2] * M.a[2][1];
        int a02 = this.a[0][0] * M.a[0][2] + this.a[0][1] * M.a[1][2] + this.a[0][2] * M.a[2][2];

        int a10 = this.a[1][0] * M.a[0][0] + this.a[1][1] * M.a[1][0] + this.a[1][2] * M.a[2][0];
        int a11 = this.a[1][0] * M.a[0][1] + this.a[1][1] * M.a[1][1] + this.a[1][2] * M.a[2][1];
        int a12 = this.a[1][0] * M.a[0][2] + this.a[1][1] * M.a[1][2] + this.a[1][2] * M.a[2][2];

        int a20 = this.a[2][0] * M.a[0][0] + this.a[2][1] * M.a[1][0] + this.a[2][2] * M.a[2][0];
        int a21 = this.a[2][0] * M.a[0][1] + this.a[2][1] * M.a[1][1] + this.a[2][2] * M.a[2][1];
        int a22 = this.a[2][0] * M.a[0][2] + this.a[2][1] * M.a[1][2] + this.a[2][2] * M.a[2][2];

        return new Matrix3x3Int(
                a00, a01, a02,
                a10, a11, a12,
                a20, a21, a22
                );
    }



}
