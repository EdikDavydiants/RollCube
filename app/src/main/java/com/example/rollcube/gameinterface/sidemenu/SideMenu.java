package com.example.rollcube.gameinterface.sidemenu;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.PullOutMenu;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class SideMenu extends PullOutMenu {

    public SideMenu(MainManager mainManager, PullingOutType pullingOutType, RectF margins,
                    Point2DFloat mainButtonInSize, RectF mainButtonMargins, boolean isAnimated) {
        super(mainManager, pullingOutType, margins, mainButtonInSize, mainButtonMargins, isAnimated);
    }

    @Override
    protected void addMainButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
        mainButton = new MainMenuButton(mainManager, inSize, margins);
        addRelative(mainButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
    }



    private class MainMenuButton extends MainButton {
        public MainMenuButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
        }

        @Override
        public void draw() {
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
            switch(status) {
                case CLOSED:
                case OPENING:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.menu_button_closed_menu);
                    break;
                case OPENED:
                case CLOSING:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.menu_button_open_menu);
                    break;
            }
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }



        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            ClickEventData clickEventData;
            if (status == Status.CLOSED) {
                clickEventData = new ClickEventData(ClickEventData.ClickEvent.OPEN_MENU);
            }
            else if (status == Status.OPENED) {
                clickEventData = new ClickEventData(ClickEventData.ClickEvent.CLOSE_MENU);
            }
            else {
                clickEventData = new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }
            super.onClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
            return clickEventData;
        }


    }


    public abstract class PullOutButtonTimeRace extends PullOutButton {
        public PullOutButtonTimeRace(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
        }

        @Override
        public void draw() {
            Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
            glBindTexture(GL_TEXTURE_2D, getTextureId());
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }

    }




}
