package com.example.rollcube.gameinterface;

import android.graphics.RectF;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.gameobjects.GameTextureObject;


public abstract class Picture extends GameTextureObject implements Relative {
    public enum Orientation { NORMAL, VERTICAL_REFLECTION, RIGHT_ROTATION, LEFT_ROTATION, TWICE_RIGHT_ROTATION, HORIZONTAL_REFLECTION }

    protected PictureGroup parent;
    protected Orientation orientation;
    protected Point2DFloat inSize;  // internal size
    protected Point2DFloat inPos;   // internal position
    protected Point2DFloat exSize;  // external size
    protected Point2DFloat exPos;   // external position
    private boolean isMenuObject = false;


    public Picture(MainManager mainManager, Point2DFloat inSize, RectF margins, Point2DFloat inPos, Orientation orientation) {
        super(mainManager);
        this.orientation = orientation;
        this.inSize = inSize;
        this.inPos = inPos;
        this.exSize = new Point2DFloat(
                inSize.x + margins.left + margins.right,
                inSize.y + margins.top + margins.bottom);
        this.exPos = new Point2DFloat(inPos.x - margins.left, inPos.y + margins.top);
    }

    public Picture(MainManager mainManager, Point2DFloat inSize, RectF margins, Orientation orientation) {
        super(mainManager);
        this.orientation = orientation;
        this.inSize = new Point2DFloat(inSize);
        this.inPos = new Point2DFloat(margins.left, -margins.top);
        this.exSize = new Point2DFloat(
                inSize.x + margins.left + margins.right,
                inSize.y + margins.top + margins.bottom);
        this.exPos = new Point2DFloat(0f, 0f);
    }

    public Picture(MainManager mainManager, Point2DFloat inSize, Orientation orientation) {
        super(mainManager);
        this.orientation = orientation;
        this.inSize = new Point2DFloat(inSize);
        this.inPos = new Point2DFloat(0f, 0f);
        this.exSize = new Point2DFloat(inSize.x, inSize.y);
        this.exPos = new Point2DFloat(0f, 0f);
    }

    public Picture(MainManager mainManager, Relative relative, Orientation orientation) {
        super(mainManager);
        this.orientation = orientation;
        this.inSize = new Point2DFloat(relative.getInSize().x, relative.getInSize().y);
        this.inPos = new Point2DFloat(relative.getInPos().x, relative.getInPos().y);
        this.exSize = new Point2DFloat(inSize.x, inSize.y);
        this.exPos = new Point2DFloat(inPos.x, inPos.y);
    }



    @Override
    public void setVertices() {
        verticesList.add(new Vector3DFloat(inPos.x, inPos.y - inSize.y, 0f));
        verticesList.add(new Vector3DFloat(inPos.x + inSize.x, inPos.y - inSize.y, 0f));
        verticesList.add(new Vector3DFloat(inPos.x, inPos.y, 0f));
        verticesList.add(new Vector3DFloat(inPos.x + inSize.x, inPos.y, 0f));

        switch (orientation) {
            case NORMAL:
                texturePointsList.add(new Point2DFloat(0, 1)); // 1
                texturePointsList.add(new Point2DFloat(1, 1)); // 2
                texturePointsList.add(new Point2DFloat(0, 0)); // 3
                texturePointsList.add(new Point2DFloat(1, 0)); // 4
                break;
            case VERTICAL_REFLECTION:
                texturePointsList.add(new Point2DFloat(0, 0));
                texturePointsList.add(new Point2DFloat(1, 0));
                texturePointsList.add(new Point2DFloat(0, 1));
                texturePointsList.add(new Point2DFloat(1, 1));
                break;
            case HORIZONTAL_REFLECTION:
                texturePointsList.add(new Point2DFloat(1, 1));
                texturePointsList.add(new Point2DFloat(0, 1));
                texturePointsList.add(new Point2DFloat(1, 0));
                texturePointsList.add(new Point2DFloat(0, 0));
                break;
            case RIGHT_ROTATION:
                texturePointsList.add(new Point2DFloat(1, 1)); // 2
                texturePointsList.add(new Point2DFloat(1, 0)); // 4
                texturePointsList.add(new Point2DFloat(0, 1)); // 1
                texturePointsList.add(new Point2DFloat(0, 0)); // 3
                break;
            case LEFT_ROTATION:
                texturePointsList.add(new Point2DFloat(0, 0)); // 3
                texturePointsList.add(new Point2DFloat(0, 1)); // 1
                texturePointsList.add(new Point2DFloat(1, 0)); // 4
                texturePointsList.add(new Point2DFloat(1, 1)); // 2
                break;
            case TWICE_RIGHT_ROTATION:
                texturePointsList.add(new Point2DFloat(1, 0)); // 4
                texturePointsList.add(new Point2DFloat(0, 0)); // 3
                texturePointsList.add(new Point2DFloat(1, 1)); // 2
                texturePointsList.add(new Point2DFloat(0, 1)); // 1
                break;
        }
    }

    @Override
    public void setIndexes() {
        indexArr = new short[] {
                (short) (startVertex + 0), (short) (startVertex + 1), (short) (startVertex + 2), (short) (startVertex + 3)
        };
    }

    @Override
    public void updateCoords(float dx, float dy, boolean isRoot) {
        if(!isRoot) {
            inPos.x += dx;
            inPos.y += dy;
            exPos.x += dx;
            exPos.y += dy;
        }
    }

    @Override
    public float getInLeft() {
        return inPos.x;
    }
    @Override
    public float getInRight() {
        return inPos.x + inSize.x;
    }
    @Override
    public float getInBottom() {
        return inPos.y - inSize.y;
    }
    @Override
    public float getInTop() {
        return inPos.y;
    }

    @Override
    public float getExLeft() {
        return exPos.x;
    }
    @Override
    public float getExRight() {
        return exPos.x + exSize.x;
    }
    @Override
    public float getExBottom() {
        return exPos.y - exSize.y;
    }
    @Override
    public float getExTop() {
        return exPos.y;
    }

    @Override
    public Point2DFloat getInSize() {
        return inSize;
    }
    @Override
    public Point2DFloat getInPos() {
        return inPos;
    }
    @Override
    public Point2DFloat getExSize() {
        return exSize;
    }
    @Override
    public Point2DFloat getExPos() {
        return exPos;
    }

    public boolean isInteractive() {
        return false;
    }

    @Override
    public void setParent(PictureGroup parent) {
        this.parent = parent;
    }

    @Override
    public void makeMenuObject() {
        isMenuObject = true;
    }


}
