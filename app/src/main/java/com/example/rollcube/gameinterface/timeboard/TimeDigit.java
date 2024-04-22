package com.example.rollcube.gameinterface.timeboard;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.Animated;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class TimeDigit extends Picture implements Animated {
    public enum Value {
        NUMBER_0, NUMBER_1, NUMBER_2, NUMBER_3, NUMBER_4, NUMBER_5,
        NUMBER_6, NUMBER_7, NUMBER_8, NUMBER_9, DASH }

    private Value value = Value.NUMBER_0;


    public TimeDigit(MainManager mainManager, Point2DFloat inSize) {
        super(mainManager, inSize, new RectF(0f, 0f, 0f, 0f), Orientation.NORMAL);
    }

    public TimeDigit(MainManager mainManager, Point2DFloat inSize, RectF margins) {
        super(mainManager, inSize, margins, Orientation.NORMAL);
    }



    @Override
    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
        switch (value) {
            case NUMBER_0:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_0);
                break;
            case NUMBER_1:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_1);
                break;
            case NUMBER_2:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_2);
                break;
            case NUMBER_3:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_3);
                break;
            case NUMBER_4:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_4);
                break;
            case NUMBER_5:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_5);
                break;
            case NUMBER_6:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_6);
                break;
            case NUMBER_7:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_7);
                break;
            case NUMBER_8:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_8);
                break;
            case NUMBER_9:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_number_9);
                break;
            case DASH:
                glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_dash);
                break;
        }
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }

    @Override
    public void animate(float[] animMatrix) {
        Matrix.multiplyMM(mModelMatrix, 0, animMatrix, 0, mModelMatrix, 0);
    }


    public void set(int digit) {
        if (digit == 0) {
            value = Value.NUMBER_0;
        } else if (digit == 1) {
            value = Value.NUMBER_1;
        } else if (digit == 2) {
            value = Value.NUMBER_2;
        } else if (digit == 3) {
            value = Value.NUMBER_3;
        } else if (digit == 4) {
            value = Value.NUMBER_4;
        } else if (digit == 5) {
            value = Value.NUMBER_5;
        } else if (digit == 6) {
            value = Value.NUMBER_6;
        } else if (digit == 7) {
            value = Value.NUMBER_7;
        } else if (digit == 8) {
            value = Value.NUMBER_8;
        } else if (digit == 9) {
            value = Value.NUMBER_9;
        }
    }

    public void setDash() {
        value = Value.DASH;
    }

}
