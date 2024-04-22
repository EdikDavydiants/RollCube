package com.example.rollcube.gameinterface.buttons;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class StartButton extends Button {
    public enum Status {SWITCH_OFF, READY_TO_START, RUNNING, PAUSED, FINISHED }

    private boolean isClickable = true;
    private Status status = Status.SWITCH_OFF;


    public StartButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
        super(mainManager, inSize, margins);
    }



    @Override
    public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if(isClickable) {
            ClickEventData clickEventData = null;
            switch(status) {
                case READY_TO_START:
                    clickEventData = new ClickEventData(ClickEventData.ClickEvent.FIRST_RUN);
                    status = Status.RUNNING;
                    break;
                case RUNNING:
                    clickEventData = new ClickEventData(ClickEventData.ClickEvent.PAUSE);
                    status = Status.PAUSED;
                    break;
                case PAUSED:
                    clickEventData = new ClickEventData(ClickEventData.ClickEvent.RESUME);
                    status = Status.RUNNING;
                    break;
                case FINISHED:
                    clickEventData = new ClickEventData(ClickEventData.ClickEvent.RESTART);
                    status = Status.READY_TO_START;
                    break;
                case SWITCH_OFF:
                    clickEventData = new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
                    break;
            }
            return clickEventData;
        }
        else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }
    }


    @Override
    public void draw() {
        if(status != Status.SWITCH_OFF) {
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
            switch(status) {
                case READY_TO_START:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.button_start);
                    break;
                case RUNNING:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.button_pause);
                    break;
                case PAUSED:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.button_resume);
                    break;
                case FINISHED:
                    glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.button_finish);
                    break;
            }
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }
    }



    public void setCondition(Status condition) {
        status = condition;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

}
