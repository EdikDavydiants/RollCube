package com.example.rollcube;

import android.content.Context;

import com.example.rollcube.activities.GameActivity;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameobjects.gamecubes.GameCube;


/**
 *  Class provides quick connection between main classes.
 */
public class ClassMediator {
    public GameActivity gameActivity;
    public GameActivity.ScreenParams screenParams;
    public GameView gameView;
    public OpenGLRend openGLRend;
    public Scene scene;
    public Camera camera;
    public GameBoard gameBoard;
    public GameInterface gameInterface;
    public GameCube gameCube;



    public Context getContext() {
        return gameActivity.getApplicationContext();
    }



}
