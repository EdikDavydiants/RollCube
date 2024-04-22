package com.example.rollcube.gameinterface;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GestureController;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;

import java.util.LinkedList;


public class SwitchBlankList extends PictureGroup {

    private final ButtonPrev buttonPrev;
    private final ButtonNext buttonNext;
    private final LinkedList<PictureGroup> blankList;
    private int focusedBlankIdx = 0;


    public SwitchBlankList(MainManager mainManager, RectF margins, float navigButtonsHeight,
                           RectF ButtonsMargins) {
        super(margins);

        blankList = new LinkedList<>();

        float navigButtonsWidth = 1.25f * navigButtonsHeight;
        buttonPrev = new ButtonPrev(mainManager, new Point2DFloat(navigButtonsWidth, navigButtonsHeight), ButtonsMargins);
        buttonNext = new ButtonNext(mainManager, new Point2DFloat(navigButtonsWidth, navigButtonsHeight), ButtonsMargins);
    }



    @Override
    public void draw() {
        blankList.get(focusedBlankIdx).draw();
        buttonPrev.draw();
        buttonNext.draw();
    }

    public void addBlank(PictureGroup blank) {
        addRelative(blank,
                GameInterface.HorizontalRelationType.IN_CENTER, this,
                GameInterface.VerticalRelativeType.IN_CENTER, this);
        blankList.add(blank);
    }

    public void addButtonPrev(PictureGroup groupAddedInto,
            GameInterface.HorizontalRelationType horizontalRelationType, Relative horizontalRelative,
            GameInterface.VerticalRelativeType verticalRelativeType, Relative verticalRelative ) {
        groupAddedInto.addRelative(buttonPrev,
                horizontalRelationType, horizontalRelative,
                verticalRelativeType, verticalRelative);
    }

    public void addButtonNext(PictureGroup groupAddedInto,
            GameInterface.HorizontalRelationType horizontalRelationType, Relative horizontalRelative,
            GameInterface.VerticalRelativeType verticalRelativeType, Relative verticalRelative ) {
        groupAddedInto.addRelative(buttonNext,
                horizontalRelationType, horizontalRelative,
                verticalRelativeType, verticalRelative);
    }

    @Override
    public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        return blankList.get(focusedBlankIdx).checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
    }

    @Override
    public ClickEventData onLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        return blankList.get(focusedBlankIdx).onLongPress(longPressData, interfaceInPosOnScreen, interfaceInSize);
    }

    @Override
    public void onSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        blankList.get(focusedBlankIdx).onSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize);
    }




    public class ButtonPrev extends StaticButton {

        public ButtonPrev(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins, Orientation.HORIZONTAL_REFLECTION);
        }



        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            if(focusedBlankIdx > 0) {
                focusedBlankIdx--;
            }
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }

        @Override
        protected int getTexture() {
            return mainManager.texturesId.main_menu_button_next;
        }


    }




    public class ButtonNext extends StaticButton {

        public ButtonNext(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins, Orientation.NORMAL);
        }



        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            if(focusedBlankIdx < blankList.size() - 1) {
                focusedBlankIdx++;
            }
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }

        @Override
        protected int getTexture() {
            return mainManager.texturesId.main_menu_button_next;
        }


    }




    public ButtonPrev getButtonPrev() {
        return buttonPrev;
    }
    public ButtonNext getButtonNext() {
        return buttonNext;
    }


}
