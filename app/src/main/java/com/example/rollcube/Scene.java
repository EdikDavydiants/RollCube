package com.example.rollcube;


import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Pointer;
import com.example.rollcube.gameinterface.Timer;
import com.example.rollcube.gameinterface.mainmenu.records.Records;
import com.example.rollcube.gameobjects.MainBackground;
import com.example.rollcube.managers.TransparentDrawingData;
import com.example.rollcube.managers.MainManager;

import java.util.LinkedList;


public class Scene implements Drawable {
    public enum AnimationType {
            GAME_CUBE_LEFT, GAME_CUBE_RIGHT, GAME_CUBE_UP, GAME_CUBE_DOWN,
            CAMERA_BACK, CAMERA_FORWARD }

    private final ClassMediator classMediator;
    private final MainManager mainManager;

    private MainBackground mainBackground;
    public GameInterface gameInterface;
    public GameBoard gameBoard;
    public Camera camera;



    public Scene(ClassMediator classMediator, MainManager mainManager, float cameraRatio) {
        this.classMediator = classMediator;
        this.classMediator.scene = this;
        this.mainManager = mainManager;

        createMainBackground(mainManager, cameraRatio, GameBoard.GameType.STARTING);

        Records.RecordArrays recordArrays = new Records.RecordArrays();

        int cameraZoom = mainManager.getSaveData().cameraZoom;
        gameInterface = new GameInterface(mainManager, cameraRatio, recordArrays, new RectF(0f, 0f, 0f, 0f));
        gameBoard = new GameBoard(mainManager, classMediator, 6, 6, recordArrays);
        camera = new Camera(mainManager, (float) (Math.PI/2 - 2 * Math.PI/2 / 4), 2, cameraRatio, cameraZoom);

        classMediator.gameInterface = gameInterface;
        classMediator.camera = camera;

        gameBoard.getCubeController().setCamera(camera);
        setTimer(gameInterface.getTimer());
        setPointer(gameInterface.getPointCounter());
    }



    @Override
    public void draw() {
        camera.moveAnimation();

        // Set transparency for walls.
        ///// Method set wall transparency depending on camera position.
        ///// It's useful in case of rotation of the camera.
        gameBoard.setTransparency(camera.getCenter().diff(camera.getEye()));

        // Create a list for transparent polygons.
        mainManager.transparentDrawingDataList = new LinkedList<>();

        // Draw all opaque polygons, collect all transparent polygons into the list.
        glDisable(GL_DEPTH_TEST);  // Disable depth test.
        mainManager.bindGameTypeObjects();
        mainBackground.draw();

        glEnable(GL_DEPTH_TEST);  // Enable depth test.
        gameBoard.draw();

        // Draw all transparent polygons.
        if (!mainManager.transparentDrawingDataList.isEmpty()) {
            new TransparentDrawingData().drawTransparentTextures(mainManager, mainManager.transparentDrawingDataList);
        }

        // Draw interface.
        glDisable(GL_DEPTH_TEST);
        gameInterface.draw();
    }

    public MainBackground createMainBackground(MainManager mainManager, float cameraRatio, GameBoard.GameType gameType) {
        return mainBackground = new MainBackground(mainManager, cameraRatio, gameType);
    }

    public void pushAnimation(AnimationType animationType) {
        switch(animationType) {
            case CAMERA_BACK:
                camera.setAnimationZoomParams(Camera.AnimationZoomType.BACK);
                break;
            case CAMERA_FORWARD:
                camera.setAnimationZoomParams(Camera.AnimationZoomType.FORWARD);
                break;

            case GAME_CUBE_LEFT:
                gameBoard.putCubeMove(CubeController.Queue.MoveDirection.LEFT);
                break;
            case GAME_CUBE_RIGHT:
                gameBoard.putCubeMove(CubeController.Queue.MoveDirection.RIGHT);
                break;
            case GAME_CUBE_UP:
                gameBoard.putCubeMove(CubeController.Queue.MoveDirection.FORWARD);
                break;
            case GAME_CUBE_DOWN:
                gameBoard.putCubeMove(CubeController.Queue.MoveDirection.BACK);
                break;
        }
    }

    public void setTimer(Timer timer) {
        gameBoard.setTimer(timer);
    }
    public void setPointer(Pointer pointer) {
        gameBoard.setPointCounter(pointer);
    }
    public void setMainBGIntoCubeController() {
        gameBoard.setMainBGIntoCubeController(mainBackground);
    }
    public void setInterfaceMatrix(float[] projXViewMatrixInterface) {
        gameInterface.setInterfaceMatrix(projXViewMatrixInterface);
    }


    public GameBoard.GameType getGameType() {
        return gameBoard.getGameType();
    }



}
