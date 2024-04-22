package com.example.rollcube.gameinterface.pointboard;

import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.Drawable;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.gameinterface.timeboard.BoardBackground;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class PointBoard extends PictureGroup implements Drawable {
    protected boolean isVisible = true;

    protected final BoardBackground background;

    protected final PointDigit hundredsDigit;
    protected final PointDigit tensDigit;
    protected final PointDigit onesDigit;

    protected int currentNumber;


    public PointBoard(MainManager mainManager, float height, int startingNumber) {
        super(new RectF(0.00f, 0.00f, 0.02f, 0.00f));

        float k = 1f;
        float digitMargin = height / 4;
        float spaceWidth = height * 0.05f / 0.256f;
        float digitWidth = height / 2;

        Point2DFloat digitSize = new Point2DFloat(digitWidth, height).prod(k);
        hundredsDigit = new PointDigit(mainManager, digitSize, new RectF(digitMargin * k, 0.0f, spaceWidth * k, 0.0f));
        tensDigit = new PointDigit(mainManager, digitSize, new RectF(0.0f, 0.0f, spaceWidth * k, 0.0f));
        onesDigit = new PointDigit(mainManager, digitSize);

        float width = 3 * digitWidth + 2 * spaceWidth + 2 * digitMargin;
        background = createBackground(mainManager, width, height, k);
        setBackground(background);

        addRelative(hundredsDigit,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(tensDigit,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, hundredsDigit,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(onesDigit,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, tensDigit,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);

        setNumber(startingNumber);
    }

    protected BoardBackground createBackground(MainManager mainManager, float width, float height, float k) {
        return new BoardBackground(mainManager, new Point2DFloat(width, height).prod(k), Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.point_records_background;
            }
        };
    }


    @Override
    public void draw() {
        if(isVisible) {
            super.draw();
        }
    }

    public void animate(float[] animMatrix) {
        background.animate(animMatrix);
        hundredsDigit.animate(animMatrix);
        tensDigit.animate(animMatrix);
        onesDigit.animate(animMatrix);
    }

    public void setNumber(int number) {
        this.currentNumber = number;

        if (!(number >= 0 && number < 1000)) {
            number = 10;
        }
        int hundreds = number / 100;
        int tens = (number / 10) % 10;
        int ones = number % 10;

        hundredsDigit.set(hundreds);
        tensDigit.set(tens);
        onesDigit.set(ones);
    }


    public void show() {
        isVisible = true;
    }
    public void hide() {
        isVisible = false;
    }




    public void setDashes() {
        hundredsDigit.setDash();
        tensDigit.setDash();
        onesDigit.setDash();
    }


}
