package com.example.rollcube.gameobjects.staticobjects;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.GameTextureObject;


public class SmallCap extends GameTextureObject {


    public SmallCap(MainManager mainManager, int n, int m) {
        super(mainManager);
        float dX = n * (GameBoard.TILE_SIDE + GameBoard.GAP_WIDTH);
        float dZ = m * (GameBoard.TILE_SIDE + GameBoard.GAP_WIDTH);
        float side = GameBoard.GAP_WIDTH;

        initVertices(dX, dZ, side);
    }



    public void initVertices(float dX, float dZ, float side) {
        verticesList.add(new Vector3DFloat(dX, 0, dZ));
        verticesList.add(new Vector3DFloat(dX + side, 0, dZ));
        verticesList.add(new Vector3DFloat(dX, 0, dZ + side));
        verticesList.add(new Vector3DFloat(dX + side, 0, dZ + side));

        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
    }

    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.VIEW_matrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMatrix, 0, mainManager.PROJ_matrix, 0, mMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);

        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.stab);
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }

    @Override
    public void setVertices() {

    }

    @Override
    public void setIndexes() {
        indexArr = new short[] {
                (short) (startVertex + 0), (short) (startVertex + 1), (short) (startVertex + 2), (short) (startVertex + 3)
        };
    }

}
