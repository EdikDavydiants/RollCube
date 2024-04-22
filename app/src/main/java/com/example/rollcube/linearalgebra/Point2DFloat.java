package com.example.rollcube.linearalgebra;



/**
 *  2-dimensional float point.
 */
public class Point2DFloat {

    public float x;
    public float y;


    public Point2DFloat() {

    }

    public Point2DFloat(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point2DFloat(Point2DFloat p) {
        this.x = p.x;
        this.y = p.y;
    }


    /**
     * Method returns sum of two points.
     * @return Returns a new point as NEW(!) object.
     */
    public Point2DFloat sum(Point2DFloat p) {
        return new Point2DFloat(x + p.x, y + p.y);
    }


    /**
     * The method multiplies current point by a number k.
     * @return Returns a new point as NEW(!) object.
     */
    public Point2DFloat prod(float k) {
        return new Point2DFloat(x * k, y * k);
    }


    /**
     * Method returns length^2 of this point, or x^2 + y^2 in other words.
     */
    public float length2() {
        return x*x + y*y;
    }

    /**
     * Method returns length of this point.
     */
    public float length() {
        return (float) Math.sqrt(length2());
    }






}
