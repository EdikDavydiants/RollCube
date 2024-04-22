package com.example.rollcube.gameinterface;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GestureController;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class PictureGroupSwitcher extends SwitchGroup {

    public PictureGroupSwitcher(RectF margins) {
        super(margins);
    }




    public abstract class SwitchButton extends SwitchableButton {

        public SwitchButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
        }

        public SwitchButton(MainManager mainManager, Point2DFloat inSize) {
            super(mainManager, inSize);
        }




        public class SwitchablePictureGroup extends PictureGroup {

            public SwitchablePictureGroup() {
                super();
            }

            public SwitchablePictureGroup(RectF margins) {
                super(margins);
            }


            @Override
            public ClickEventData checkClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen,
                                             Point2DFloat interfaceInSize) {
                if(isChosen) {
                    return super.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
                } else {
                    return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
                }
            }

            @Override
            public boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen,
                                      Point2DFloat interfaceInSize) {
                if(isChosen) {
                    return super.checkSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize);
                } else {
                    return false;
                }
            }

            @Override
            public void draw() {
                if(isChosen) {
                    super.draw();
                }
            }
        }



        public class SwitchableSwitchGroup extends SwitchGroup {

            public SwitchableSwitchGroup() {
                super();
            }

            public SwitchableSwitchGroup(RectF margins) {
                super(margins);
            }


            @Override
            public ClickEventData checkClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen,
                                                       Point2DFloat interfaceInSize) {
                if(isChosen) {
                    return super.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
                } else {
                    return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
                }
            }

            @Override
            public boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen,
                                      Point2DFloat interfaceInSize) {
                if(isChosen) {
                    return super.checkSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize);
                } else {
                    return false;
                }
            }

            @Override
            public void draw() {
                if(isChosen) {
                    super.draw();
                }
            }
        }




    }







}
