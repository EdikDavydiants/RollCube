package com.example.rollcube.gameinterface;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;

import java.util.LinkedList;


public class SwitchGroup extends PictureGroup {
    private final LinkedList<SwitchableButton> switchGroup;


    public SwitchGroup() {
        super();
        switchGroup = new LinkedList<>();
    }

    public SwitchGroup(RectF margins) {
        super(margins);
        switchGroup = new LinkedList<>();
    }



    public void addSwitchableButton(SwitchableButton switchableButton,
                                    GameInterface.HorizontalRelationType horizontalRelationType, Relative horizontalRelative,
                                    GameInterface.VerticalRelativeType verticalRelativeType, Relative verticalRelative) {
        addRelative(switchableButton, horizontalRelationType, horizontalRelative, verticalRelativeType, verticalRelative);
        switchGroup.add(switchableButton);
    }



    public abstract class SwitchableButton extends Button {
        protected boolean isChosen = false;

        public SwitchableButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
        }

        public SwitchableButton(MainManager mainManager, Point2DFloat inSize) {
            super(mainManager, inSize);
        }


        @Override
        public void draw() {
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
            if(isChosen) {
                glBindTexture(GL_TEXTURE_2D, getChosenTexture());
            } else {
                glBindTexture(GL_TEXTURE_2D, getUnchosenTexture());
            }
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }

        public abstract int getChosenTexture();
        public abstract int getUnchosenTexture();

        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            if(!isChosen) {
                for (SwitchableButton switchableButton: switchGroup) {
                    switchableButton.isChosen = false;
                }
                isChosen = true;
                actSmth();
            }
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }

        protected abstract void actSmth();

        public boolean isChosen() {
            return isChosen;
        }

    }




}
