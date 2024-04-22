package com.example.rollcube.gameinterface.timeboard;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.gameinterface.Background;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.Relative;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class BoardBackground extends Background {

    public BoardBackground(MainManager mainManager, Relative relative, Picture.Orientation orientation) {
        super(mainManager, relative, orientation);
    }

    public BoardBackground(MainManager mainManager, Point2DFloat inSize, Picture.Orientation orientation) {
        super(mainManager, inSize, orientation);
    }



    @Override
    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
        glBindTexture(GL_TEXTURE_2D, getTexture());
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }

    @Override
    public void animate(float[] animMatrix) {
        Matrix.multiplyMM(mModelMatrix, 0, animMatrix, 0, mModelMatrix, 0);
    }

    @Override
    protected abstract int getTexture();


}
