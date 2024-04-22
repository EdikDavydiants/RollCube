package com.example.rollcube.managers;


import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glUniformMatrix4fv;

import java.util.LinkedList;


public class TransparentDrawingData {

    public int startIndex;
    public int pointNumber;
    public int textureId;
    public int uMatrixLocation;
    public float[] mMatrix;
    public boolean isTextureChanged;
    public boolean isMatrixChanged;



    public TransparentDrawingData() {

    }

    public TransparentDrawingData(int startIndex, int pointNumber, int textureId, int uMatrixLocation, float[] mMatrix, boolean isTextureChanged, boolean isMatrixChanged) {
        this.startIndex = startIndex;
        this.pointNumber = pointNumber;
        this.textureId = textureId;
        this.uMatrixLocation = uMatrixLocation;
        this.mMatrix = mMatrix;
        this.isTextureChanged = isTextureChanged;
        this.isMatrixChanged = isMatrixChanged;
    }

    public void drawTransparentTextures(MainManager mainManager, LinkedList<TransparentDrawingData> transparentDrawingDataList) {
        mainManager.bindGameTypeObjects();
        glEnable(GL_BLEND);
        boolean isFirst = true;
        for (TransparentDrawingData transparentDrawingData: transparentDrawingDataList) {
            if (isFirst) {
                isFirst = false;
                glBindTexture(GL_TEXTURE_2D, transparentDrawingData.textureId);
                glUniformMatrix4fv(transparentDrawingData.uMatrixLocation, 1, false, transparentDrawingData.mMatrix, 0);
            }
            else {
                if (transparentDrawingData.isTextureChanged) {
                    glBindTexture(GL_TEXTURE_2D, transparentDrawingData.textureId);
                }
                if (transparentDrawingData.isMatrixChanged) {
                    glUniformMatrix4fv(transparentDrawingData.uMatrixLocation, 1, false, transparentDrawingData.mMatrix, 0);
                }
            }
            glDrawArrays(GL_TRIANGLE_STRIP, transparentDrawingData.startIndex, transparentDrawingData.pointNumber);
        }
        glDisable(GL_BLEND);
        mainManager.bindMainObjects();
    }

}