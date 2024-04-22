package com.example.rollcube.gameinterface;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import com.example.rollcube.Animated;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class Background extends Picture implements Animated {

    public Background(MainManager mainManager, Relative relative, Orientation orientation) {
        super(mainManager, relative, orientation);
    }

    public Background(MainManager mainManager, Point2DFloat inSize, Orientation orientation) {
        super(mainManager, inSize, orientation);
    }



    @Override
    public void draw() {
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
        glBindTexture(GL_TEXTURE_2D, getTexture());
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }

    @Override
    public abstract void animate(float[] animMatrix);

    protected abstract int getTexture();



}
