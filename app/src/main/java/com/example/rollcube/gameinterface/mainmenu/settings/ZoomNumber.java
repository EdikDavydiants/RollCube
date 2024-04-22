package com.example.rollcube.gameinterface.mainmenu.settings;

import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.gameinterface.StaticButton;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class ZoomNumber extends PictureGroup {

    private final Sign signView;
    private final Digit digitView;


    public ZoomNumber(MainManager mainManager, int startingNumber, float height, RectF margins) {
        super(margins);

        Point2DFloat digitInSize = new Point2DFloat(height * 52f / 80f, height);
        RectF digitMargins = new RectF(0, height / 8, height / 8, height / 8);
        RectF signMargins = new RectF(height / 8, height / 8, height / 8, height / 8);

        if (startingNumber < -9 || startingNumber > 9) {
            startingNumber = 0;
        }
        if (startingNumber >= 0) {
            digitView = new Digit(mainManager, digitInSize, digitMargins, Math.abs(startingNumber));
            signView = new Sign(mainManager, digitInSize, signMargins, 1);
        } else {
            digitView = new Digit(mainManager, digitInSize, digitMargins, -startingNumber);
            signView = new Sign(mainManager, digitInSize, signMargins, -1);
        }


        addRelative(signView,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(digitView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, signView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
    }





    public class Sign extends StaticButton {
        private int value = 1;

        public Sign(MainManager mainManager, Point2DFloat inSize, RectF margins, int startingValue) {
            super(mainManager, inSize, margins);
            value = startingValue;
        }


        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            value *= -1;
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }


        @Override
        protected int getTexture() {
            if (value == 1) {
                return mainManager.texturesId.common_font_plus;
            }
            else {
                return mainManager.texturesId.common_font_minus;
            }
        }



    }




    public class Digit extends StaticButton {
        private int value;

        public Digit(MainManager mainManager, Point2DFloat inSize, RectF margins, int startingValue) {
            super(mainManager, inSize, margins);
            value = startingValue;
        }


        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            increment();
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }


        @Override
        protected int getTexture() {
            if (value == 0) {
                return mainManager.texturesId.common_font_digit_0;
            }
            else if (value == 1 || value == -1) {
                return mainManager.texturesId.common_font_digit_1;
            }
            else if (value == 2 || value == -2) {
                return mainManager.texturesId.common_font_digit_2;
            }
            else if (value == 3 || value == -3) {
                return mainManager.texturesId.common_font_digit_3;
            }
            else if (value == 4 || value == -4) {
                return mainManager.texturesId.common_font_digit_4;
            }
            else if (value == 5 || value == -5) {
                return mainManager.texturesId.common_font_digit_5;
            }
            else if (value == 6 || value == -6) {
                return mainManager.texturesId.common_font_digit_6;
            }
            else if (value == 7 || value == -7) {
                return mainManager.texturesId.common_font_digit_7;
            }
            else if (value == 8 || value == -8) {
                return mainManager.texturesId.common_font_digit_8;
            }
            else {
                return mainManager.texturesId.common_font_digit_9;
            }
        }


        public void increment() {
            if(value == 9) {
                value = 0;
            } else {
                value++;
            }
        }

        public void decrement() {
            if(value == 0) {
                value = 9;
            } else {
                value--;
            }
        }

    }




    public int getValue() {
        return digitView.value * signView.value;
    }



}
