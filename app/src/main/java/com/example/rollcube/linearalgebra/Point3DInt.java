package com.example.rollcube.linearalgebra;


/**
 * 3-dimensional integer point
 */
public class Point3DInt {

    public int a;
    public int b;
    public int c;


    public Point3DInt(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Scalar product of points.
     */
    public int scalarProd(Point3DInt orient) {
        return a * orient.a + b * orient.b + c * orient.c;
    }



}
