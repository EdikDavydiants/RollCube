package com.example.rollcube.gameinterface.mainmenu;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GestureController;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class MenuBoard extends PictureGroup {
    private boolean isActive = false;

    public MenuBoard() {

    }



    @Override
    public void draw() {
        if (isActive) {
            super.draw();
        }
    }


    @Override
    public boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if (isActive) {
            return super.checkSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return false;
        }

    }

    @Override
    public ClickEventData checkClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if (isActive) {
            return super.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        }
    }

    @Override
    public ClickEventData checkLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if (isActive) {
            return super.checkLongPress(longPressData, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        }
    }


    public void open() {
        isActive = true;
    }
    public void close() {
        isActive = false;
    }


}
