package com.example.rollcube.gameinterface;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GestureController;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class Button extends Picture implements Interactive {


    public Button(MainManager mainManager, Point2DFloat inSize, RectF margins, Point2DFloat inPos) {
        super(mainManager, inSize, margins, inPos, Orientation.NORMAL);
    }

    public Button(MainManager mainManager, Point2DFloat inSize, RectF margins) {
        super(mainManager, inSize, margins, Orientation.NORMAL);
    }

    public Button(MainManager mainManager, Point2DFloat inSize, RectF margins, Orientation orientation) {
        super(mainManager, inSize, margins, orientation);
    }

    public Button(MainManager mainManager, Point2DFloat inSize) {
        super(mainManager, inSize, Orientation.NORMAL);
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
    public boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        return false;
    }

    @Override
    public void onSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {

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
        return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
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

    public boolean isInteractive() {
        return true;
    }



}
