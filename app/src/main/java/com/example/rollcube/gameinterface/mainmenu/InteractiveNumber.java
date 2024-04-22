package com.example.rollcube.gameinterface.mainmenu;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GestureController;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.linearalgebra.Point2DFloat;

public class InteractiveNumber extends PictureGroup {

    private final Digit integerPartOnesView;
    private final Digit fractionalPartTensView;
    private final Digit fractionalPartOnesView;

    private final float bottomValue;
    private final float topValue;


    public InteractiveNumber(MainManager mainManager,
                             float startingNumber, float bottomValue, float topValue,
                             float digitMargin, float height, RectF margins) {
        super(margins);
        this.bottomValue = bottomValue;
        this.topValue = topValue;

        if (startingNumber <= 0f || startingNumber > 10f) {
            startingNumber = 1f;
        }
        int intOnes = ((int) startingNumber) % 10;
        int fracTens = ((int) (startingNumber * 10)) % 10;
        int fracOnes = ((int) (startingNumber * 100)) % 10;

        float digitWidth = height * 52f / 80f;
        float pointWidth = height * 20f / 80f;

        integerPartOnesView = new Digit(mainManager, new Point2DFloat(digitWidth, height), new RectF(0f, 0f, 3 * digitMargin, 0f), intOnes);
        fractionalPartTensView = new Digit(mainManager, new Point2DFloat(digitWidth, height), new RectF(0f, 0f, digitMargin, 0f), fracTens);
        fractionalPartOnesView = new Digit(mainManager, new Point2DFloat(digitWidth, height), new RectF(0f, 0f, 0f, 0f), fracOnes);

        Picture pointView = new Picture(mainManager, new Point2DFloat(pointWidth, height),
                new RectF(0f, 0f, 3 * digitMargin, 0f), Picture.Orientation.NORMAL) {
            @Override
            public void draw() {
                glUniformMatrix4fv(this.mainManager.uMatrixLocation_tp, 1, false, this.mainManager.INTERFACE_matrix, 0);
                glBindTexture(GL_TEXTURE_2D, this.mainManager.texturesId.common_font_point);
                this.mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, this.mainManager.buffers.indexBuffer_t);
            }
        };

        addRelative(integerPartOnesView,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(pointView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, integerPartOnesView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(fractionalPartTensView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, pointView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(fractionalPartOnesView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, fractionalPartTensView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
    }



    @Override
    public ClickEventData onLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        setNumber(bottomValue);
        return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
    }

    public float getNumber() {
        return (float) integerPartOnesView.value +
                0.1f * fractionalPartTensView.value +
                0.01f * fractionalPartOnesView.value;
    }

    public void setNumber(float value) {
        if (value <= 0f || value > 10f) {
            value = 1f;
        }
        int intOnes = ((int) value) % 10;
        int fracTens = ((int) (value * 10)) % 10;
        int fracOnes = Math.round(value * 100) % 10;

        integerPartOnesView.value = intOnes;
        fractionalPartTensView.value = fracTens;
        fractionalPartOnesView.value = fracOnes;
    }




    public class Digit extends Button {
        private int value;

        public Digit(MainManager mainManager, Point2DFloat inSize, RectF margins, int startingValue) {
            super(mainManager, inSize, margins);
            value = startingValue;
        }


        @Override
        public void draw() {
            Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
            if (value == 0) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_0);
            }
            else if (value == 1) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_1);
            }
            else if (value == 2) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_2);
            }
            else if (value == 3) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_3);
            }
            else if (value == 4) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_4);
            }
            else if (value == 5) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_5);
            }
            else if (value == 6) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_6);
            }
            else if (value == 7) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_7);
            }
            else if (value == 8) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_8);
            }
            else {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.common_font_digit_9);
            }
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }

        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            increment();
            float value = getNumber();
            if (value < bottomValue || value > topValue) {
                decrement();
            }
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
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





}
