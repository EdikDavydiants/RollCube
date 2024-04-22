package com.example.rollcube.gameinterface.timeboard;

import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.Drawable;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class TimeBoard extends PictureGroup implements Drawable {
    private boolean isVisible = true;
    protected boolean isDashes;

    protected int min_tens;
    protected int min_ones;
    protected int sec_tens;
    protected int sec_ones;
    protected int decisec;

    protected final BoardBackground background;
    protected final TimeDigit minTensView;
    protected final TimeDigit minOnesView;
    protected final Colon colon;
    protected final TimeDigit secTensView;
    protected final TimeDigit secOnesView;
    protected final Point point;
    protected final TimeDigit decisecView;


    protected final MainManager mainManager;


    public TimeBoard(MainManager mainManager, RectF margins, float height, long time) {
        super(margins);
        this.mainManager = mainManager;

        float left_margin = height * 0.2f;
        float min_tens_viewWidth = height * 0.5f;
        float min_ones_viewWidth = height * 0.5f;
        float double_points_viewWidth = height * 0.2f;
        float sec_tens_viewWidth = height * 0.5f;
        float sec_ones_viewWidth = height * 0.5f;
        float point_viewWidth = height * 0.2f;
        float decisec_viewWidth = height * 0.3f;
        float decisecBottomMargin = 0.006f;
        float decisecHeight = height * 3f / 5;

        float width = 2 * left_margin + 4 * min_tens_viewWidth + double_points_viewWidth + point_viewWidth + decisec_viewWidth;

        minTensView = new TimeDigit(mainManager, new Point2DFloat(min_tens_viewWidth, height), new RectF(left_margin, 0f, 0f, 0f));
        minOnesView = new TimeDigit(mainManager, new Point2DFloat(min_ones_viewWidth, height));
        colon = new Colon(mainManager, new Point2DFloat(double_points_viewWidth, height));
        secTensView = new TimeDigit(mainManager, new Point2DFloat(sec_tens_viewWidth, height));
        secOnesView = new TimeDigit(mainManager, new Point2DFloat(sec_ones_viewWidth, height));
        point = new Point(mainManager, new Point2DFloat(point_viewWidth, height));
        decisecView = new TimeDigit(mainManager, new Point2DFloat(decisec_viewWidth, decisecHeight),
                new RectF(0f, 0f, 0f, decisecBottomMargin));

        background = createBackground(mainManager, width, height);
        setBackground(background);

        addRelative(minTensView,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(minOnesView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, minTensView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(colon,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, minOnesView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(secTensView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, colon,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(secOnesView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, secTensView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(point,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, secOnesView,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(decisecView,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, point,
                GameInterface.VerticalRelativeType.BOTTOM_TO_IN_BOTTOM, this);

        setTime(time);
    }

    protected BoardBackground createBackground(MainManager mainManager, float width, float height) {
        return new BoardBackground(mainManager, new Point2DFloat(width, height), Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.time_records_background;
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
        minTensView.animate(animMatrix);
        minOnesView.animate(animMatrix);
        colon.animate(animMatrix);
        secTensView.animate(animMatrix);
        secOnesView.animate(animMatrix);
        point.animate(animMatrix);
        decisecView.animate(animMatrix);
    }

    public void setTime(long time) {
        isDashes = false;

        decisec = ((int) (time / 100)) % 10;
        int sec = ((int) (time / 1000)) % 60;
        sec_tens = sec / 10;
        sec_ones = sec % 10;
        int min = (int) (time / (1000 * 60));
        min_tens = min / 10;
        min_ones = min % 10;

        minTensView.set(min_tens);
        minOnesView.set(min_ones);
        secTensView.set(sec_tens);
        secOnesView.set(sec_ones);
        decisecView.set(decisec);
    }

    public void setDashes() {
        minTensView.setDash();
        minOnesView.setDash();
        secTensView.setDash();
        secOnesView.setDash();
        decisecView.setDash();
    }

    public void hide() {
        isVisible = false;
    }
    public void show() {
        isVisible = true;
    }





}