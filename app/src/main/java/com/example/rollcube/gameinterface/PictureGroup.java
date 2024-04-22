package com.example.rollcube.gameinterface;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GestureController;
import com.example.rollcube.linearalgebra.Point2DFloat;

import java.util.LinkedList;


public class PictureGroup implements Interactive {
    protected PictureGroup parent;
    protected Background groupBackground = null;
    protected final boolean isExpandable;
    protected Point2DFloat inSize;  // internal size
    protected Point2DFloat inPos;   // internal position
    protected Point2DFloat exSize;  // external size
    protected Point2DFloat exPos;   // external position

    protected LinkedList<Relative> relativeGroup;
    protected LinkedList<Interactive> interactiveGroup;


    public PictureGroup() {
        isExpandable = true;
        relativeGroup = new LinkedList<>();
        interactiveGroup = new LinkedList<>();
        inSize = new Point2DFloat(0f, 0f);
        exSize = new Point2DFloat(0f, 0f);
        inPos = new Point2DFloat(0f, 0f);
        exPos = new Point2DFloat(0f, 0f);
    }

    public PictureGroup(RectF margins) {
        isExpandable = true;
        relativeGroup = new LinkedList<>();
        interactiveGroup = new LinkedList<>();
        inSize = new Point2DFloat(0f, 0f);
        exSize = new Point2DFloat(margins.left + margins.right, margins.top + margins.bottom);
        inPos = new Point2DFloat(margins.left, -margins.top);
        exPos = new Point2DFloat(0f, 0f);
    }

    public PictureGroup(Point2DFloat inSize, RectF margins) {
        isExpandable = false;
        relativeGroup = new LinkedList<>();
        interactiveGroup = new LinkedList<>();
        this.inSize = inSize;
        exSize = new Point2DFloat(inSize.x + margins.left + margins.right, inSize.y + margins.top + margins.bottom);
        inPos = new Point2DFloat(margins.left, -margins.top);
        exPos = new Point2DFloat(0f, 0f);
    }

    public PictureGroup(Point2DFloat inPos, Point2DFloat inSize, RectF margins) {
        isExpandable = false;
        relativeGroup = new LinkedList<>();
        interactiveGroup = new LinkedList<>();
        this.inSize = inSize;
        exSize = new Point2DFloat(inSize.x + margins.left + margins.right, inSize.y + margins.top + margins.bottom);
        this.inPos = inPos;
        exPos = new Point2DFloat(inPos.x - margins.left, inPos.y + margins.top);
    }



    public void updateVerticalPosition(GameInterface.VerticalRelativeType verticalRelativeType, Relative verticalRelative, Relative ... relatives) {
        float oldGroupExPos = relatives[0].getExPos().y;
        float topMargin = oldGroupExPos - relatives[0].getInPos().y;
        float groupExSize = oldGroupExPos - relatives[relatives.length - 1].getExBottom();
        float bottomMargin = relatives[relatives.length - 1].getInBottom() - relatives[relatives.length - 1].getExBottom();
        float newGroupExPos = 0f;
        switch(verticalRelativeType) {
            case IN_CENTER:
                float center = (verticalRelative.getInBottom() + verticalRelative.getInTop()) / 2;
                newGroupExPos = center + groupExSize / 2;
                break;
            case BOTTOM_TO_IN_BOTTOM:
                newGroupExPos = verticalRelative.getInBottom() + groupExSize;
                break;
            case BOTTOM_TO_IN_TOP:
                newGroupExPos = verticalRelative.getInTop() + groupExSize;
                break;
            case TOP_TO_IN_BOTTOM:
                newGroupExPos = verticalRelative.getInBottom();
                break;
            case TOP_TO_IN_TOP:
                newGroupExPos = verticalRelative.getInTop();
                break;
            case BOTTOM_TO_EX_BOTTOM:
                newGroupExPos = verticalRelative.getExBottom() + groupExSize;
                break;
            case BOTTOM_TO_EX_TOP:
                newGroupExPos = verticalRelative.getExTop() + groupExSize;
                break;
            case TOP_TO_EX_BOTTOM:
                newGroupExPos = verticalRelative.getExBottom();
                break;
            case TOP_TO_EX_TOP:
                newGroupExPos = verticalRelative.getExTop();
                break;
        }

        if (isExpandable) {
            float bottom = newGroupExPos - groupExSize;
            float topExpansion = 0f;
            float bottomExpansion = 0f;
            if (newGroupExPos > inPos.y) {
                topExpansion = newGroupExPos - inPos.y;
            }
            if (bottom < inPos.y - inSize.y) {
                bottomExpansion = (inPos.y - inSize.y) - bottom;
            }
            inSize.y += topExpansion + bottomExpansion;
            exSize.y += topExpansion + bottomExpansion;
            inPos.y += topExpansion;
            exPos.y += topExpansion;
        }

        float dY = newGroupExPos - oldGroupExPos;

        for (Relative relative: relatives) {
            relative.getExPos().y += dY;
            relative.getInPos().y += dY;
            relative.updateCoords(0f, dY, true);
        }
    }

    public void updateHorizontalPosition(GameInterface.HorizontalRelationType horizontalRelationType, Relative horizontalRelative, Relative ... relatives) {
        float oldGroupExPos = relatives[0].getExPos().x;
        //float topMargin = oldGroupExPos - relatives[0].getInPos().y;
        float groupExSize = oldGroupExPos - relatives[relatives.length - 1].getExRight();
        //float bottomMargin = relatives[relatives.length - 1].getInBottom() - relatives[relatives.length - 1].getExBottom();
        float newGroupExPos = 0f;
        switch(horizontalRelationType) {
            case IN_CENTER:
                float center = (horizontalRelative.getInLeft() + horizontalRelative.getInRight()) / 2;
                newGroupExPos = center + groupExSize / 2;
                break;
            case RIGHT_TO_IN_RIGHT:
                newGroupExPos = horizontalRelative.getInRight() - groupExSize;
                break;
            case RIGHT_TO_IN_LEFT:
                newGroupExPos = horizontalRelative.getInLeft() - groupExSize;
                break;
            case LEFT_TO_IN_RIGHT:
                newGroupExPos = horizontalRelative.getInRight();
                break;
            case LEFT_TO_IN_LEFT:
                newGroupExPos = horizontalRelative.getInLeft();
                break;
            case RIGHT_TO_EX_RIGHT:
                newGroupExPos = horizontalRelative.getExRight() - groupExSize;
                break;
            case RIGHT_TO_EX_LEFT:
                newGroupExPos = horizontalRelative.getExLeft() - groupExSize;
                break;
            case LEFT_TO_EX_RIGHT:
                newGroupExPos = horizontalRelative.getExRight();
                break;
            case LEFT_TO_EX_LEFT:
                newGroupExPos = horizontalRelative.getExLeft();
                break;
        }

        if (isExpandable) {
            float right = newGroupExPos + groupExSize;
            float leftExpansion = 0f;
            float rightExpansion = 0f;
            if (newGroupExPos < inPos.x) {
                leftExpansion = inPos.x - newGroupExPos;
            }
            if (right > inPos.x + inSize.x) {
                rightExpansion =  right - (inPos.x + inSize.x);
            }
            inSize.x += leftExpansion + rightExpansion;
            exSize.x += leftExpansion + rightExpansion;
            inPos.x += leftExpansion;
            exPos.x += leftExpansion;
        }

        float dX = newGroupExPos - oldGroupExPos;

        for (Relative relative: relatives) {
            relative.getExPos().x += dX;
            relative.getInPos().x += dX;
            relative.updateCoords(dX, 0f, true);
        }
    }

    public void updateVerticalPosition(Relative relative,
                               GameInterface.VerticalRelativeType verticalRelativeType, Relative verticalRelative) {
        float oldY = relative.getExPos().y;
        float topMargin = relative.getExPos().y - relative.getInPos().y;
        switch (verticalRelativeType) {
            case IN_CENTER:
                float center = (verticalRelative.getInBottom() + verticalRelative.getInTop()) / 2;
                relative.getInPos().y = center + relative.getInSize().y / 2 - topMargin;
                relative.getExPos().y = center + relative.getInSize().y / 2;
                break;
            case BOTTOM_TO_IN_BOTTOM:
                relative.getInPos().y = verticalRelative.getInBottom() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getInBottom() + relative.getExSize().y;
                break;
            case BOTTOM_TO_IN_TOP:
                relative.getInPos().y = verticalRelative.getInTop() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getInTop() + relative.getExSize().y;
                break;
            case TOP_TO_IN_BOTTOM:
                relative.getInPos().y = verticalRelative.getInBottom() - topMargin;
                relative.getExPos().y = verticalRelative.getInBottom();
                break;
            case TOP_TO_IN_TOP:
                relative.getInPos().y = verticalRelative.getInTop() - topMargin;
                relative.getExPos().y = verticalRelative.getInTop();
                break;
            case BOTTOM_TO_EX_BOTTOM:
                relative.getInPos().y = verticalRelative.getExBottom() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getExBottom() + relative.getExSize().y;
                break;
            case BOTTOM_TO_EX_TOP:
                relative.getInPos().y = verticalRelative.getExTop() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getExTop() + relative.getExSize().y;
                break;
            case TOP_TO_EX_BOTTOM:
                relative.getInPos().y = verticalRelative.getExBottom() - topMargin;
                relative.getExPos().y = verticalRelative.getExBottom();
                break;
            case TOP_TO_EX_TOP:
                relative.getInPos().y = verticalRelative.getExTop() - topMargin;
                relative.getExPos().y = verticalRelative.getExTop();
                break;
        }

        if (isExpandable) {
            float top = relative.getExPos().y;
            float bottom = top - relative.getExSize().y;

            float topExpansion = 0f;
            float bottomExpansion = 0f;
            if (top > inPos.y) {
                topExpansion = top - inPos.y;
            }
            if (bottom < inPos.y - inSize.y) {
                bottomExpansion = (inPos.y - inSize.y) - bottom;
            }
            inSize.y += topExpansion + bottomExpansion;
            exSize.y += topExpansion + bottomExpansion;
            inPos.y += topExpansion;
            exPos.y += topExpansion;
        }

        float dY = relative.getExPos().y - oldY;

        relative.updateCoords(0f, dY, true);
    }

    public void updateVerticalPosition(Relative relative, Relative verticalRelative, float relPos) {
        float oldY = relative.getExPos().y;
        float topMargin = relative.getExPos().y - relative.getInPos().y;

        float verRelBottom = verticalRelative.getInBottom();
        float verRelTop = verticalRelative.getInTop();
        float verticalSize = relative.getExSize().y;
        float range = verRelTop - verRelBottom - verticalSize;

        relative.getExPos().y = verRelBottom + verticalSize + relPos * range;
        relative.getInPos().y = relative.getExPos().y - topMargin;

        if (isExpandable) {
            float top = relative.getExPos().y;
            float bottom = top - relative.getExSize().y;

            float topExpansion = 0f;
            float bottomExpansion = 0f;
            if (top > inPos.y) {
                topExpansion = top - inPos.y;
            }
            if (bottom < inPos.y - inSize.y) {
                bottomExpansion = (inPos.y - inSize.y) - bottom;
            }
            inSize.y += topExpansion + bottomExpansion;
            exSize.y += topExpansion + bottomExpansion;
            inPos.y += topExpansion;
            exPos.y += topExpansion;
        }

        float dY = relative.getExPos().y - oldY;

        relative.updateCoords(0f, dY, true);
    }

    public void updateHorizontalPosition(Relative relative,
                                       GameInterface.HorizontalRelationType horizontalRelationType, Relative horizontalRelative) {
        float oldX = relative.getExPos().x;
        float leftMargin = relative.getInPos().x - relative.getExPos().x;
        switch (horizontalRelationType) {
            case IN_CENTER:
                float center = (horizontalRelative.getInLeft() + horizontalRelative.getInRight()) / 2;
                relative.getInPos().x = center - relative.getInSize().x / 2 + leftMargin;
                relative.getExPos().x = center - relative.getInSize().x / 2;
                break;
            case LEFT_TO_IN_LEFT:
                relative.getInPos().x = horizontalRelative.getInLeft() + leftMargin;
                relative.getExPos().x = horizontalRelative.getInLeft();
                break;
            case LEFT_TO_IN_RIGHT:
                relative.getInPos().x = horizontalRelative.getInRight() + leftMargin;
                relative.getExPos().x = horizontalRelative.getInRight();
                break;
            case RIGHT_TO_IN_LEFT:
                relative.getInPos().x = horizontalRelative.getInLeft() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getInLeft() - relative.getExSize().x;
                break;
            case RIGHT_TO_IN_RIGHT:
                relative.getInPos().x = horizontalRelative.getInRight() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getInRight() - relative.getExSize().x;
                break;
            case LEFT_TO_EX_LEFT:
                relative.getInPos().x = horizontalRelative.getExLeft() + leftMargin;
                relative.getExPos().x = horizontalRelative.getExLeft();
                break;
            case LEFT_TO_EX_RIGHT:
                relative.getInPos().x = horizontalRelative.getExRight() + leftMargin;
                relative.getExPos().x = horizontalRelative.getExRight();
                break;
            case RIGHT_TO_EX_LEFT:
                relative.getInPos().x = horizontalRelative.getExLeft() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getExLeft() - relative.getExSize().x;
                break;
            case RIGHT_TO_EX_RIGHT:
                relative.getInPos().x = horizontalRelative.getExRight() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getExRight() - relative.getExSize().x;
                break;
        }

        if (isExpandable) {
            float left = relative.getExPos().x;
            float right = left + relative.getExSize().x;

            float leftExpansion = 0f;
            float rightExpansion = 0f;
            if (left < inPos.x) {
                leftExpansion = inPos.x - left;
            }
            if (right > inPos.x + inSize.x) {
                rightExpansion = right - (inPos.x + inSize.x);
            }
            inSize.x += leftExpansion + rightExpansion;
            exSize.x += leftExpansion + rightExpansion;
            inPos.x -= leftExpansion;
            exPos.x -= leftExpansion;
        }

        float dX = relative.getExPos().x - oldX;

        relative.updateCoords(dX, 0f, true);
    }

    public void updatePosition(Relative relative,
                            GameInterface.HorizontalRelationType horizontalRelationType, Relative horizontalRelative,
                            GameInterface.VerticalRelativeType verticalRelativeType, Relative verticalRelative) {
        float oldX = relative.getExPos().x;
        float oldY = relative.getExPos().y;
        float leftMargin = relative.getInPos().x - relative.getExPos().x;
        float topMargin = relative.getExPos().y - relative.getInPos().y;
        switch (horizontalRelationType) {
            case IN_CENTER:
                float center = (horizontalRelative.getInLeft() + horizontalRelative.getInRight()) / 2;
                relative.getInPos().x = center - relative.getInSize().x / 2 + leftMargin;
                relative.getExPos().x = center - relative.getInSize().x / 2;
                break;
            case LEFT_TO_IN_LEFT:
                relative.getInPos().x = horizontalRelative.getInLeft() + leftMargin;
                relative.getExPos().x = horizontalRelative.getInLeft();
                break;
            case LEFT_TO_IN_RIGHT:
                relative.getInPos().x = horizontalRelative.getInRight() + leftMargin;
                relative.getExPos().x = horizontalRelative.getInRight();
                break;
            case RIGHT_TO_IN_LEFT:
                relative.getInPos().x = horizontalRelative.getInLeft() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getInLeft() - relative.getExSize().x;
                break;
            case RIGHT_TO_IN_RIGHT:
                relative.getInPos().x = horizontalRelative.getInRight() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getInRight() - relative.getExSize().x;
                break;
            case LEFT_TO_EX_LEFT:
                relative.getInPos().x = horizontalRelative.getExLeft() + leftMargin;
                relative.getExPos().x = horizontalRelative.getExLeft();
                break;
            case LEFT_TO_EX_RIGHT:
                relative.getInPos().x = horizontalRelative.getExRight() + leftMargin;
                relative.getExPos().x = horizontalRelative.getExRight();
                break;
            case RIGHT_TO_EX_LEFT:
                relative.getInPos().x = horizontalRelative.getExLeft() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getExLeft() - relative.getExSize().x;
                break;
            case RIGHT_TO_EX_RIGHT:
                relative.getInPos().x = horizontalRelative.getExRight() - relative.getExSize().x + leftMargin;
                relative.getExPos().x = horizontalRelative.getExRight() - relative.getExSize().x;
                break;
        }
        switch (verticalRelativeType) {
            case IN_CENTER:
                float center = (verticalRelative.getInBottom() + verticalRelative.getInTop()) / 2;
                relative.getInPos().y = center + relative.getInSize().y / 2 - topMargin;
                relative.getExPos().y = center + relative.getInSize().y / 2;
                break;
            case BOTTOM_TO_IN_BOTTOM:
                relative.getInPos().y = verticalRelative.getInBottom() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getInBottom() + relative.getExSize().y;
                break;
            case BOTTOM_TO_IN_TOP:
                relative.getInPos().y = verticalRelative.getInTop() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getInTop() + relative.getExSize().y;
                break;
            case TOP_TO_IN_BOTTOM:
                relative.getInPos().y = verticalRelative.getInBottom() - topMargin;
                relative.getExPos().y = verticalRelative.getInBottom();
                break;
            case TOP_TO_IN_TOP:
                relative.getInPos().y = verticalRelative.getInTop() - topMargin;
                relative.getExPos().y = verticalRelative.getInTop();
                break;
            case BOTTOM_TO_EX_BOTTOM:
                relative.getInPos().y = verticalRelative.getExBottom() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getExBottom() + relative.getExSize().y;
                break;
            case BOTTOM_TO_EX_TOP:
                relative.getInPos().y = verticalRelative.getExTop() + relative.getExSize().y - topMargin;
                relative.getExPos().y = verticalRelative.getExTop() + relative.getExSize().y;
                break;
            case TOP_TO_EX_BOTTOM:
                relative.getInPos().y = verticalRelative.getExBottom() - topMargin;
                relative.getExPos().y = verticalRelative.getExBottom();
                break;
            case TOP_TO_EX_TOP:
                relative.getInPos().y = verticalRelative.getExTop() - topMargin;
                relative.getExPos().y = verticalRelative.getExTop();
                break;
        }

        if (isExpandable) {
            float left = relative.getExPos().x;
            float top = relative.getExPos().y;
            float right = left + relative.getExSize().x;
            float bottom = top - relative.getExSize().y;

            float leftExpansion = 0f;
            float rightExpansion = 0f;
            float topExpansion = 0f;
            float bottomExpansion = 0f;
            if (left < inPos.x) {
                leftExpansion = inPos.x - left;
            }
            if (right > inPos.x + inSize.x) {
                rightExpansion = right - (inPos.x + inSize.x);
            }
            if (top > inPos.y) {
                topExpansion = top - inPos.y;
            }
            if (bottom < inPos.y - inSize.y) {
                bottomExpansion = (inPos.y - inSize.y) - bottom;
            }
            inSize.x += leftExpansion + rightExpansion;
            exSize.x += leftExpansion + rightExpansion;
            inSize.y += topExpansion + bottomExpansion;
            exSize.y += topExpansion + bottomExpansion;
            inPos.x -= leftExpansion;
            exPos.x -= leftExpansion;
            inPos.y += topExpansion;
            exPos.y += topExpansion;
        }

        float dX = relative.getExPos().x - oldX;
        float dY = relative.getExPos().y - oldY;

        relative.updateCoords(dX, dY, true);
    }

    public void addRelative(Relative relative,
                            GameInterface.HorizontalRelationType horizontalRelationType, Relative horizontalRelative,
                            GameInterface.VerticalRelativeType verticalRelativeType, Relative verticalRelative) {
        relative.setParent(this);
        relativeGroup.add(relative);

        updatePosition(relative, horizontalRelationType, horizontalRelative, verticalRelativeType, verticalRelative);

        if(relative.isInteractive()) {
            interactiveGroup.add((Interactive) relative);
        }
    }

    @Override
    public void draw() {
        if(groupBackground != null) {
            groupBackground.draw();
        }
        for (Relative relative: relativeGroup) {
            relative.draw();
        }
    }

    @Override
    public void setVertices() {
        for (Relative relative: relativeGroup) {
            relative.setVertices();
        }
    }

    @Override
    public void updateCoords(float dx, float dy, boolean isRoot) {
        if(!isRoot) {
            inPos.x += dx;
            inPos.y += dy;
            exPos.x += dx;
            exPos.y += dy;
        }
        for (Relative relative: relativeGroup) {
            relative.updateCoords(dx, dy, false);
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

    @Override
    public void setParent(PictureGroup parent) {
        this.parent = parent;
    }

    @Override
    public void makeMenuObject() {
        for (Relative relative: relativeGroup) {
            relative.makeMenuObject();
        }
    }

    public void setBackground(Background background) {
        addRelative(background,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        relativeGroup.addFirst(relativeGroup.removeLast());
    }


    @Override
    public RectF getParamsOnScreen(RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        float kx = (interfaceInPosOnScreen.right - interfaceInPosOnScreen.left) / interfaceInSize.x;
        float ky = (interfaceInPosOnScreen.bottom - interfaceInPosOnScreen.top) / interfaceInSize.y;
        float sizeOnScreenX = inSize.x * kx;
        float sizeOnScreenY = inSize.y * ky;
        float posOnScreenX = (interfaceInSize.x / 2 + inPos.x) * kx;
        float posOnScreenY = (interfaceInSize.y / 2 - inPos.y) * ky;

        return new RectF(posOnScreenX, posOnScreenY, posOnScreenX + sizeOnScreenX, posOnScreenY + sizeOnScreenY);
    }


    @Override
    public boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        RectF paramsOnScreen = getParamsOnScreen(interfaceInPosOnScreen, interfaceInSize);
        if (
                swipeData.x1 > paramsOnScreen.left && swipeData.x1 < paramsOnScreen.right  &&
                swipeData.y1 > paramsOnScreen.top  && swipeData.y1 < paramsOnScreen.bottom
        ) {
            onSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        for (Interactive interactive : interactiveGroup) {
            if (interactive.checkSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize)) {
                break;
            }
        }
    }


    @Override
    public ClickEventData checkClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        RectF paramsOnScreen = getParamsOnScreen(interfaceInPosOnScreen, interfaceInSize);
        if (clickCoords.x > paramsOnScreen.left &&
                clickCoords.x < paramsOnScreen.right &&
                clickCoords.y > paramsOnScreen.top &&
                clickCoords.y < paramsOnScreen.bottom) {
            return onClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        }
    }
    @Override
    public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        ClickEventData clickEventData = new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        for (Interactive interactive: interactiveGroup) {
            clickEventData = interactive.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
            if (clickEventData.clickEvent != ClickEventData.ClickEvent.NO_CLICK) {
                break;
            }
        }
        return clickEventData;
    }


    @Override
    public ClickEventData checkLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        RectF paramsOnScreen = getParamsOnScreen(interfaceInPosOnScreen, interfaceInSize);
        float coordX = longPressData.x;
        float coordY = longPressData.y;
        if (coordX > paramsOnScreen.left &&
                coordX < paramsOnScreen.right &&
                coordY > paramsOnScreen.top &&
                coordY < paramsOnScreen.bottom) {
            return onLongPress(longPressData, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        }
    }
    @Override
    public ClickEventData onLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        ClickEventData clickEventData = new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        for (Interactive interactive : interactiveGroup) {
            clickEventData = interactive.checkLongPress(longPressData, interfaceInPosOnScreen, interfaceInSize);
            if (clickEventData.clickEvent != ClickEventData.ClickEvent.NO_CLICK) {
                break;
            }
        }
        return clickEventData;
    }



    public boolean isInteractive() {
        return true;
    }

    public PictureGroup getParent() {
        return parent;
    }


    public RectF getExBorders() {
        return new RectF(exPos.x, exPos.y, exPos.x + exSize.x, exPos.y - exSize.y);
    }
    public RectF getInBorders() {
        return new RectF(inPos.x, inPos.y, inPos.x + inSize.x, inPos.y - inSize.y);
    }

}
