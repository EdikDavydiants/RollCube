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

public class Colon extends Picture implements Animated {

    public Colon(MainManager mainManager, Point2DFloat inSize) {
        super(mainManager, inSize, new RectF(0f, 0f, 0f, 0f), Orientation.NORMAL);
    }



    @Override
    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.timer_colon);
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }

    @Override
    public void animate(float[] animMatrix) {
        Matrix.multiplyMM(mModelMatrix, 0, animMatrix, 0, mModelMatrix, 0);
    }



}
