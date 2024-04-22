package com.example.rollcube.gameinterface.celebration;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public class GoldPiece extends FeastObject {
    private final int GOLD = 0;

    public GoldPiece(MainManager mainManager, Celebration celebration, Point2DFloat size) {
        super(mainManager, celebration, size);
    }

    public GoldPiece(MainManager mainManager, Celebration celebration, Point2DFloat size, Point2DFloat V, float Vw) {
        super(mainManager, celebration, size, V, Vw);
    }


    @Override
    public void setVertices() {
        float k = 0.15f;
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.031f, 0.036f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.028f, 0.110f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.000f, 0.167f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.044f, 0.210f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.070f, 0.264f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.135f, 0.236f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.200f, 0.264f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.228f, 0.206f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.268f, 0.162f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.238f, 0.107f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.233f, 0.037f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.187f, 0.064f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.134f, 0.027f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.081f, 0.066f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
    }

    @Override
    public void setIndexes() {
        indexArr = new short[] {
                (short) (startVertex + 0), (short) (startVertex + 1), (short) (startVertex + 13), (short) (startVertex + 9), (short) (startVertex + 11), (short) (startVertex + 10),
                (short) (startVertex + 5), (short) (startVertex + 4), (short) (startVertex + 3), (short) (startVertex + 7), (short) (startVertex + 6),

                (short) (startVertex + 11), (short) (startVertex + 12), (short) (startVertex + 13),
                (short) (startVertex + 1), (short) (startVertex + 2), (short) (startVertex + 9), (short) (startVertex + 8),

                (short) (startVertex + 2), (short) (startVertex + 3), (short) (startVertex + 8), (short) (startVertex + 7),
        };
    }


    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_cp, 1, false, mMatrix, 0);

        for (EdgeGroup edgeGroup: edgeGroups) {
            edgeGroup.draw();
        }
    }


    @Override
    protected void initPalette() {
        palette = new float[1][4];

        palette[GOLD][R] = 1f;
        palette[GOLD][G] = ((float) 0x0000009c) / 0x000000ff;
        palette[GOLD][B] = 0f;
        palette[GOLD][A] = 1f;
    }


    @Override
    protected void initEdgeGroups() {
        edgeGroups = new EdgeGroup[3];

        edgeGroups[0] = new EdgeGroup() {
            @Override
            protected void moveColor() {}

            @Override
            protected void draw() {
                glUniform4f(mainManager.uColorLocation, palette[GOLD][R], palette[GOLD][G], palette[GOLD][B], palette[GOLD][A]);
                mainManager.buffers.indexBuffer_c.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 6, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
                mainManager.buffers.indexBuffer_c.position(startIndex + 6);
                glDrawElements(GL_TRIANGLE_FAN, 5, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };

        edgeGroups[1] = new EdgeGroup(0.7f, 180f) {
            @Override
            protected void moveColor() {
                float light_coeff = light_coeff(colorRange, angle - angle0 + Celebration.LIGHT_ANGLE);
                color_r = palette[GOLD][R] * light_coeff;
                color_g = palette[GOLD][G] * light_coeff;
                color_b = palette[GOLD][B] * light_coeff;
            }

            @Override
            protected void draw() {
                moveColor();
                glUniform4f(mainManager.uColorLocation, color_r, color_g, color_b, 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex + 11);
                glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
                mainManager.buffers.indexBuffer_c.position(startIndex + 14);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };
        edgeGroups[2] = new EdgeGroup(0.7f, 0f) {
            @Override
            protected void moveColor() {
                float light_coeff = light_coeff(colorRange, angle - angle0 + Celebration.LIGHT_ANGLE);
                color_r = palette[GOLD][R] * light_coeff;
                color_g = palette[GOLD][G] * light_coeff;
                color_b = palette[GOLD][B] * light_coeff;
            }

            @Override
            protected void draw() {
                moveColor();
                glUniform4f(mainManager.uColorLocation, color_r, color_g, color_b, 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex + 18);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };
    }



}
