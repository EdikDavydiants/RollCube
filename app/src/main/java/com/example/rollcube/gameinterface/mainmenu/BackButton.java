package com.example.rollcube.gameinterface.mainmenu;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class BackButton extends Button {

    public BackButton(MainManager mainManager, float size, RectF margins) {
        super(mainManager, new Point2DFloat(size, size), margins);
    }

    public BackButton(MainManager mainManager, float size) {
        super(mainManager, new Point2DFloat(size, size));
    }



    @Override
    public void draw() {
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
        glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.main_menu_button_back);
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }


}
