package com.example.rollcube.gameinterface.scrollmenu;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public abstract class ScrollPicture extends Picture {
    public enum Status {FOCUSED, UNFOCUSED, HIDDEN}

    private Status status = Status.UNFOCUSED;


    public ScrollPicture(MainManager mainManager, float side, float margin) {
        super(mainManager, new Point2DFloat(side, side), new RectF(margin, margin, margin, margin), Orientation.NORMAL);
    }


    public void animate(Vector3DFloat center, boolean isVertical, boolean direction, float dfi) {
        if(direction) { dfi = -dfi; }
        moveAnimation(isVertical, center, dfi);
    }

    public void fastMove(Vector3DFloat center, boolean isVertical, boolean direction, int n) {
        if(direction) { n = -n; }
        moveAnimation(isVertical, center, 45f * n);
    }

    public void moveAnimation(boolean isVertical, Vector3DFloat center, float dfi) {
        Matrix.setIdentityM(I1, 0);
        Matrix.setIdentityM(I2, 0);

        Matrix.translateM(I1, 0, -center.x, -center.y, -center.z);

        if(isVertical) {
            Matrix.setRotateM(mMatrix, 0, dfi, 1, 0, 0);
        } else {
            Matrix.setRotateM(mMatrix, 0, dfi, 0, 1, 0);
        }
        Matrix.translateM(I2, 0, center.x, center.y, center.z);

        Matrix.multiplyMM(mModelMatrix, 0, I1, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mModelMatrix, 0, mMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mModelMatrix, 0, I2, 0, mModelMatrix, 0);
    }

    @Override
    public void draw() {
        if (status != Status.HIDDEN) {
            Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
            glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
            switch(status) {
                case FOCUSED:
                    glBindTexture(GL_TEXTURE_2D, getColorTextureId());
                    break;
                case UNFOCUSED:
                    glBindTexture(GL_TEXTURE_2D, getGrayTextureId());
                    break;
            }
            mainManager.buffers.indexBuffer_t.position(startIndex);
            glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
        }
    }

    public abstract int getGrayTextureId();
    public abstract int getColorTextureId();

    public void focus() {
        status = Status.FOCUSED;
    }
    public void defocus() {
        status = Status.UNFOCUSED;
    }
    public void hide() {
        status = Status.HIDDEN;
    }




}
