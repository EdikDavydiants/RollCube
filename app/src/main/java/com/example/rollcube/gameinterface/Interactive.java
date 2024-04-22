package com.example.rollcube.gameinterface;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GestureController;
import com.example.rollcube.linearalgebra.Point2DFloat;


public interface Interactive extends Relative {

    ClickEventData checkClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize);
    ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize);

    boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize);
    void onSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize);

    ClickEventData checkLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize);
    ClickEventData onLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize);

    RectF getParamsOnScreen(RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize);

}
