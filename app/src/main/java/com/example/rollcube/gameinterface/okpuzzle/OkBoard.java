package com.example.rollcube.gameinterface.okpuzzle;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.app.Activity;
import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.managers.DataManager;
import com.example.rollcube.GestureController;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.Background;
import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.gameinterface.StaticPicture;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class OkBoard extends PictureGroup {

    private boolean isActive = false;
    private ResetButton resetButton;
    private OkPuzzle okPuzzle;

    public OkBoard(MainManager mainManager) {
        StaticPicture warningPic = new StaticPicture(mainManager, new Point2DFloat((1281f / 150) * 0.1f, 0.1f),
                new RectF(0.05f, 0.05f, 0.05f, 0.02f), Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.ok_board_warning_pic;
            }
        };
        okPuzzle = new OkPuzzle(mainManager);
        resetButton = new ResetButton(mainManager, new Point2DFloat(0.15f, 0.15f),
                new RectF(0.01f, 0.02f, 0.01f, 0.04f));

        addRelative(warningPic,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(okPuzzle,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, warningPic);
        addRelative(resetButton,
                GameInterface.HorizontalRelationType.IN_CENTER, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, okPuzzle);

        updateHorizontalPosition(okPuzzle, GameInterface.HorizontalRelationType.IN_CENTER, this);
        updateHorizontalPosition(resetButton, GameInterface.HorizontalRelationType.IN_CENTER, this);

        setBackground(new Background(mainManager, this, Picture.Orientation.NORMAL) {
            @Override
            public void animate(float[] animMatrix) {

            }

            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.menu_background;
            }
        });
    }



    @Override
    public void draw() {
        if(isActive) {
            super.draw();
        }
    }

    @Override
    public ClickEventData checkClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if(isActive) {
            return super.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        }
    }

    @Override
    public ClickEventData checkLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if(isActive) {
            return super.checkLongPress(longPressData, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_CLICK);
        }
    }

    @Override
    public boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if(isActive) {
            return super.checkSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize);
        } else {
            return false;
        }
    }

    public void activate() {
        isActive = true;
    }

    public class ResetButton extends Button {
        private boolean isOk = false;

        public ResetButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
        }


        @Override
        public void draw() {
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
            if(isOk) {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.main_menu_button_yes);
            } else {
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.main_menu_button_no);
            }
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }

        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            isActive = false;
            okPuzzle.setGreyOk();
            if(isOk) {
                isOk = false;
                new DataManager().setDefaultInterfaceSizeValues(mainManager.getContext());
                ((Activity)(mainManager.getContext())).finish();
            }
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }
    }

    public void setResetButtonStatus(boolean status) {
        resetButton.isOk = status;
    }

}
