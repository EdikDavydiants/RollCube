package com.example.rollcube;

import android.opengl.Matrix;


public abstract class Animation {

    public boolean isAnimating = false;
    public int frameCounter = 0;


    public abstract void moveAnimation();

    public void shift(float shiftX, float shiftY,  float shiftZ,
                      float[] mModelMatrix, float[] M) {
        Matrix.setIdentityM(M, 0);
        Matrix.translateM(M, 0, shiftX, shiftY, shiftZ);
        Matrix.multiplyMM(mModelMatrix, 0, M, 0, mModelMatrix, 0);
    }

    public void rotate(float shiftX, float shiftY, float shiftZ,
                       float rotAxeX, float rotAxeY, float rotAxeZ,
                       float angle,
                       float[] mModelMatrix,
                       float[] M_shift, float[] M_rotate, float[] M_shift_back ) {
        Matrix.setIdentityM(M_shift, 0);
        Matrix.setIdentityM(M_shift_back, 0);

        Matrix.translateM(M_shift, 0, -shiftX, -shiftY, -shiftZ);
        Matrix.setRotateM(M_rotate, 0, angle, rotAxeX, rotAxeY, rotAxeZ);
        Matrix.translateM(M_shift_back, 0, shiftX, shiftY, shiftZ);

        Matrix.multiplyMM(mModelMatrix, 0, M_shift, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mModelMatrix, 0, M_rotate, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mModelMatrix, 0, M_shift_back, 0, mModelMatrix, 0);
    }

}
