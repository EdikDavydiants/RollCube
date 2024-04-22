package com.example.rollcube.gameinterface.mainmenu.newgame;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class PlayButton extends Button {

    private boolean isActive = false;
    private final NewGame newGame;

    public PlayButton(MainManager mainManager, NewGame newGame, Point2DFloat inSize, RectF margins) {
        super(mainManager, inSize, margins);
        this.newGame = newGame;
    }



    @Override
    public void draw() {
        glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mainManager.INTERFACE_matrix, 0);
        if (isActive) {
            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.button_play_active);
        } else {
            glBindTexture(GL_TEXTURE_2D, mainManager.texturesId.button_play_unactive);
        }
        mainManager.buffers.indexBuffer_t.position(startIndex);
        glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
    }


    @Override
    public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        GameBoard.GameInfo gameInfo = newGame.collectGameRegime();
        if (gameInfo != null) {
            ClickEventData clickEventData = new ClickEventData(ClickEventData.ClickEvent.NEW_GAME);
            clickEventData.gameInfo = gameInfo;
            ((GameInterface) newGame.getParent()).closeMainMenu();
            return clickEventData;
        } else {
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }
    }


    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }

}
