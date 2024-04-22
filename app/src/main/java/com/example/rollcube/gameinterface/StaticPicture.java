package com.example.rollcube.gameinterface;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class StaticPicture extends Picture{
    public StaticPicture(MainManager mainManager, Point2DFloat inSize, RectF margins, Point2DFloat inPos, Orientation orientation) {
        super(mainManager, inSize, margins, inPos, orientation);
    }
    public StaticPicture(MainManager mainManager, Point2DFloat inSize, RectF margins, Orientation orientation) {
        super(mainManager, inSize, margins, orientation);
    }
    public StaticPicture(MainManager mainManager, Point2DFloat inSize, Orientation orientation) {
        super(mainManager, inSize, orientation);
    }
    public StaticPicture(MainManager mainManager, Relative relative, Orientation orientation) {
        super(mainManager, relative, orientation);
    }



    @Override
    public void draw() {
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
        glBindTexture(GL_TEXTURE_2D, getTexture());
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }

    protected abstract int getTexture();




}
