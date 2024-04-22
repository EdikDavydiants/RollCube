package com.example.rollcube.gameinterface;

import com.example.rollcube.Drawable;
import com.example.rollcube.linearalgebra.Point2DFloat;

public interface Relative extends Drawable {


    void updateCoords(float dx, float dy, boolean isRoot);

    void setVertices();

    boolean isInteractive();

    float getInLeft();
    float getInRight();
    float getInBottom();
    float getInTop();

    float getExLeft();
    float getExRight();
    float getExBottom();
    float getExTop();

    Point2DFloat getInSize();
    Point2DFloat getInPos();
    Point2DFloat getExSize();
    Point2DFloat getExPos();

    void setParent(PictureGroup parent);

    void makeMenuObject();

}
