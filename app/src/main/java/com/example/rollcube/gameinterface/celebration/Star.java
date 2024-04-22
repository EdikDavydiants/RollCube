package com.example.rollcube.gameinterface.celebration;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.opengl.Matrix;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public class Star extends FeastObject {

    private int currentColor = RED;
    public final static int RED = 4;
    public final static int GOLD = 0;
    public final static int SILVER = 1;
    public final static int BRONZE = 2;
    public final static int IRON = 3;

    public Star(MainManager mainManager, Celebration celebration, Point2DFloat size) {
        super(mainManager, celebration, size);
        init();
    }

    public Star(MainManager mainManager, Celebration celebration, Point2DFloat size, Point2DFloat V, float Vw) {
        super(mainManager, celebration, size, V, Vw);
    }

    private void init() {

    }

    @Override
    public void setVertices() {
        float k = 0.15f;
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.000f, 0.103f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.042f, 0.177f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.051f, 0.264f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.130f, 0.247f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.215f, 0.267f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.222f, 0.180f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.266f, 0.105f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.188f, 0.070f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.133f, 0.000f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
        verticesList.add(new Vector3DFloat(pos.x, pos.y, 0f).sum(new Vector3DFloat(0.078f, 0.068f, 0f).sum(new Vector3DFloat(0f, -0.026f, 0f)).prod(k)));
    }

    @Override
    public void setIndexes() {
        indexArr = new short[] {
                (short) (startVertex + 1), (short) (startVertex + 3), (short) (startVertex + 5), (short) (startVertex + 7), (short) (startVertex + 9),
                (short) (startVertex + 0), (short) (startVertex + 1), (short) (startVertex + 9),
                (short) (startVertex + 2), (short) (startVertex + 1), (short) (startVertex + 3),
                (short) (startVertex + 4), (short) (startVertex + 3), (short) (startVertex + 5),
                (short) (startVertex + 6), (short) (startVertex + 5), (short) (startVertex + 7),
                (short) (startVertex + 8), (short) (startVertex + 7), (short) (startVertex + 9),
        };
    }


    public void draw() {
        Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
        glUniformMatrix4fv(mainManager.uMatrixLocation_cp, 1, false, mMatrix, 0);

        for (EdgeGroup edgeGroup: edgeGroups) {
            edgeGroup.draw();
        }

//        edgeGroups[0].draw();
//        edgeGroups[1].draw();
//        edgeGroups[2].draw();
//        edgeGroups[3].draw();
//        edgeGroups[4].draw();
//        edgeGroups[5].draw();
    }




    @Override
    protected void initPalette() {
        palette = new float[5][4];

        palette[RED][R] = ((float) 0x0000009c) / 0x000000ff;
        palette[RED][G] = 0f;
        palette[RED][B] = 0f;
        palette[RED][A] = 1f;

        palette[GOLD][R] = ((float) 0x00000073) / 0x000000ff;
        palette[GOLD][G] = ((float) 0x00000066) / 0x000000ff;
        palette[GOLD][B] = ((float) 0x00000027) / 0x000000ff;
        palette[GOLD][A] = 1f;

        palette[SILVER][R] = ((float) 0x00000063) / 0x000000ff;
        palette[SILVER][G] = ((float) 0x0000006f) / 0x000000ff;
        palette[SILVER][B] = ((float) 0x00000073) / 0x000000ff;
        palette[SILVER][A] = 1f;

        palette[BRONZE][R] = ((float) 0x00000073) / 0x000000ff;
        palette[BRONZE][G] = ((float) 0x0000004a) / 0x000000ff;
        palette[BRONZE][B] = ((float) 0x00000028) / 0x000000ff;
        palette[BRONZE][A] = 1f;

        palette[IRON][R] = ((float) 0x00000038) / 0x000000ff;
        palette[IRON][G] = ((float) 0x0000003b) / 0x000000ff;
        palette[IRON][B] = ((float) 0x00000040) / 0x000000ff;
        palette[IRON][A] = 1f;
    }


    @Override
    protected void initEdgeGroups() {
        edgeGroups = new EdgeGroup[6];

        edgeGroups[0] = new EdgeGroup() {
            @Override
            protected void moveColor() {}

            @Override
            protected void draw() {
                glUniform4f(mainManager.uColorLocation, palette[currentColor][R], palette[currentColor][G], palette[currentColor][B], 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex);
                glDrawElements(GL_TRIANGLE_FAN, 5, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };

        edgeGroups[1] = new EdgeGroup(0.7f, 72f - 180f) {
            @Override
            protected void moveColor() {
                float light_coeff = light_coeff(colorRange, angle - angle0 + Celebration.LIGHT_ANGLE);
                color_r = palette[currentColor][R] * light_coeff;
                color_g = palette[currentColor][G] * light_coeff;
                color_b = palette[currentColor][B] * light_coeff;
            }
            @Override
            protected void draw() {
                moveColor();
                glUniform4f(mainManager.uColorLocation, color_r, color_g, color_b, 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex + 5);
                glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };
        edgeGroups[2] = new EdgeGroup(0.7f, 72f * 2 - 180f) {
            @Override
            protected void moveColor() {
                float light_coeff = light_coeff(colorRange, angle - angle0 + Celebration.LIGHT_ANGLE);
                color_r = palette[currentColor][R] * light_coeff;
                color_g = palette[currentColor][G] * light_coeff;
                color_b = palette[currentColor][B] * light_coeff;
            }
            @Override
            protected void draw() {
                moveColor();
                glUniform4f(mainManager.uColorLocation, color_r, color_g, color_b, 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex + 8);
                glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };
        edgeGroups[3] = new EdgeGroup(0.7f, 72f * 3 - 180f) {
            @Override
            protected void moveColor() {
                float light_coeff = light_coeff(colorRange, angle - angle0 + Celebration.LIGHT_ANGLE);
                color_r = palette[currentColor][R] * light_coeff;
                color_g = palette[currentColor][G] * light_coeff;
                color_b = palette[currentColor][B] * light_coeff;
            }
            @Override
            protected void draw() {
                moveColor();
                glUniform4f(mainManager.uColorLocation, color_r, color_g, color_b, 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex + 11);
                glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };
        edgeGroups[4] = new EdgeGroup(0.7f, 72f * 4 - 180f) {
            @Override
            protected void moveColor() {
                float light_coeff = light_coeff(colorRange, angle - angle0 + Celebration.LIGHT_ANGLE);
                color_r = palette[currentColor][R] * light_coeff;
                color_g = palette[currentColor][G] * light_coeff;
                color_b = palette[currentColor][B] * light_coeff;
            }
            @Override
            protected void draw() {
                moveColor();
                glUniform4f(mainManager.uColorLocation, color_r, color_g, color_b, 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex + 14);
                glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };
        edgeGroups[5] = new EdgeGroup(0.7f, 0f - 180f) {
            @Override
            protected void moveColor() {
                float light_coeff = light_coeff(colorRange, angle - angle0 + Celebration.LIGHT_ANGLE);
                color_r = palette[currentColor][R] * light_coeff;
                color_g = palette[currentColor][G] * light_coeff;
                color_b = palette[currentColor][B] * light_coeff;
            }
            @Override
            protected void draw() {
                moveColor();
                glUniform4f(mainManager.uColorLocation, color_r, color_g, color_b, 1f);
                mainManager.buffers.indexBuffer_c.position(startIndex + 17);
                glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_c);
            }
        };
    }


    public void changeColor(int idx){
        currentColor = idx;
    }

}

