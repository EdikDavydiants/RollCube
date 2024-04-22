package com.example.rollcube.managers;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

import android.content.Context;

import com.example.rollcube.OpenGLRend;
import com.example.rollcube.gameobjects.GameColorObject;
import com.example.rollcube.gameobjects.GameTextureObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.LinkedList;


public class MainManager {
    private final Context context;
    private final DataManager.SaveData saveData;

    private final LinkedList<GameColorObject> gameColorObjectList;
    private final LinkedList<GameTextureObject> gameTextureObjectList;

    private final LinkedList<GameTextureObject> gameTypeObjectList;

    public int textureProgramId;

    public Buffers buffers;

    public int aPositionLocation_t;
    public int uMatrixLocation_tp;
    public int uTextureUnitLocation;
    public int aTextureLocation;

    public int colorProgramId;
    public int aPositionLocation_cp;
    public int uMatrixLocation_cp;
    public int uColorLocation;



    public final float[] INTERFACE_matrix = new float[16];
    public float[] PROJ_matrix = new float[16];
    public float[] VIEW_matrix = new float[16];

    public TexturesIdData texturesId;
    public SoundManager soundManager;

    public LinkedList<TransparentDrawingData> transparentDrawingDataList;



    public MainManager(Context context) {
        this.context = context;

        saveData = new DataManager.SaveData(context);

        buffers = new Buffers();

        gameColorObjectList = new LinkedList<>();
        gameTextureObjectList = new LinkedList<>();

        gameTypeObjectList = new LinkedList<>();

        uploadSounds(context);
    }



    public void clearGameTypeList() {
        gameTypeObjectList.clear();
    }

    public void uploadTextures(Context context) {
        texturesId = new TexturesIdData(context);
    }

    public void uploadSounds(Context context) {
        soundManager = new SoundManager(context);
    }


    public void addColorObject(GameColorObject gameColorObject) {
        gameColorObjectList.add(gameColorObject);
    }
    public void addTextureObject(GameTextureObject gameTextureObject) {
        gameTextureObjectList.add(gameTextureObject);
    }
    public void addGameTypeObject(GameTextureObject gameTextureObject) {
        gameTypeObjectList.add(gameTextureObject);
    }


    public void initBuffersGameTypeObjects() {
        ArrayInit arrayInit = new ArrayInit();
        int vertNumb_t = countVertices_t(gameTypeObjectList);
        arrayInit.coordArr_t = new float[vertNumb_t * 5];
        initCoordsArr_t(arrayInit.coordArr_t, gameTypeObjectList);

        buffers.coordsBuffer_gameType = ByteBuffer.allocateDirect(arrayInit.coordArr_t.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffers.coordsBuffer_gameType.put(arrayInit.coordArr_t);

        setIndexes_t(gameTypeObjectList);
        int indexNumb_t = countIndexes_t(gameTypeObjectList);
        arrayInit.indexArr_t = new short[indexNumb_t];
        initIndexArr_t(arrayInit.indexArr_t, gameTypeObjectList);

        buffers.indexBuffer_gameType = ByteBuffer.allocateDirect(arrayInit.indexArr_t.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffers.indexBuffer_gameType.put(arrayInit.indexArr_t);
    }
    public void initBuffersColorObjects() {
        ArrayInit arrayInit = new ArrayInit();
        int vertNumb_c = countVertices_c(gameColorObjectList);
        arrayInit.coordArr_c = new float[vertNumb_c * 3];
        initCoordsArr_c(arrayInit.coordArr_c, gameColorObjectList);

        buffers.coordsBuffer_c = ByteBuffer.allocateDirect(arrayInit.coordArr_c.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffers.coordsBuffer_c.put(arrayInit.coordArr_c);

        setIndexes_c(gameColorObjectList);
        int indexNumb_c = countIndexes_c(gameColorObjectList);
        arrayInit.indexArr_c = new short[indexNumb_c];
        initIndexArr_c(arrayInit.indexArr_c, gameColorObjectList);

        buffers.indexBuffer_c = ByteBuffer.allocateDirect(arrayInit.indexArr_c.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffers.indexBuffer_c.put(arrayInit.indexArr_c);
    }
    public void initBuffersTexturesObjects() {
        ArrayInit arrayInit = new ArrayInit();
        int vertNumb_t = countVertices_t(gameTextureObjectList);
        arrayInit.coordArr_t = new float[vertNumb_t * 5];
        initCoordsArr_t(arrayInit.coordArr_t, gameTextureObjectList);

        buffers.coordsBuffer_t = ByteBuffer.allocateDirect(arrayInit.coordArr_t.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffers.coordsBuffer_t.put(arrayInit.coordArr_t);

        setIndexes_t(gameTextureObjectList);
        int indexNumb_t = countIndexes_t(gameTextureObjectList);
        arrayInit.indexArr_t = new short[indexNumb_t];
        initIndexArr_t(arrayInit.indexArr_t, gameTextureObjectList);

        buffers.indexBuffer_t = ByteBuffer.allocateDirect(arrayInit.indexArr_t.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffers.indexBuffer_t.put(arrayInit.indexArr_t);
    }

    public int countVertices_c(LinkedList<GameColorObject> gameColorObjectList) {
        int pointsNumber = 0;
        for (GameColorObject gameColorObject: gameColorObjectList) {
            pointsNumber += gameColorObject.countVertices();
        }
        return pointsNumber;
    }
    public int countVertices_t(LinkedList<GameTextureObject> gameTextureObjectList) {
        int pointsNumber = 0;
        for (GameTextureObject gameTextureObject: gameTextureObjectList) {
            pointsNumber += gameTextureObject.countVertices();
        }
        return pointsNumber;
    }

    public int countIndexes_c(LinkedList<GameColorObject> gameColorObjectList) {
        int indexNumber = 0;
        for (GameColorObject gameColorObject: gameColorObjectList) {
            indexNumber += gameColorObject.countIndexes();
        }
        return indexNumber;
    }
    public int countIndexes_t(LinkedList<GameTextureObject> gameTextureObjectList) {
        int indexNumber = 0;
        for (GameTextureObject gameTextureObject: gameTextureObjectList) {
            indexNumber += gameTextureObject.countIndexes();
        }
        return indexNumber;
    }

    private void initCoordsArr_c(float[] coordsArr, LinkedList<GameColorObject> gameColorObjectList) {
        int startCoord = 0;
        for (GameColorObject gameColorObject: gameColorObjectList) {
            startCoord = gameColorObject.initCoordsArr(startCoord, coordsArr);
        }
    }
    private void initCoordsArr_t(float[] coordsArr, LinkedList<GameTextureObject> gameTextureObjectList) {
        int startCoord = 0;
        for (GameTextureObject gameTextureObject: gameTextureObjectList) {
            startCoord = gameTextureObject.initCoordsArr(startCoord, coordsArr);
        }
    }

    private void initIndexArr_c(short[] indexArr, LinkedList<GameColorObject> gameColorObjectList) {
        int startIndex = 0;
        for (GameColorObject gameColorObject: gameColorObjectList) {
            startIndex = gameColorObject.initIndexArr(startIndex, indexArr);
        }
    }
    private void initIndexArr_t(short[] indexArr, LinkedList<GameTextureObject> gameTextureObjectList) {
        int startIndex = 0;
        for (GameTextureObject gameTextureObject: gameTextureObjectList) {
            startIndex = gameTextureObject.initIndexArr(startIndex, indexArr);
        }
    }

    private void setIndexes_c(LinkedList<GameColorObject> gameColorObjectList) {
        for (GameColorObject gameColorObject: gameColorObjectList) {
            gameColorObject.setIndexes();
        }
    }
    private void setIndexes_t(LinkedList<GameTextureObject> gameTextureObjectList) {
        for (GameTextureObject gameTextureObject: gameTextureObjectList) {
            gameTextureObject.setIndexes();
        }
    }




    public static class ArrayInit {
        public float[] coordArr_t;
        public short[] indexArr_t;
        public float[] coordArr_c;
        public short[] indexArr_c;
    }


    public void useColorProgram() {
        glUseProgram(colorProgramId);
        glVertexAttribPointer(aPositionLocation_cp, 3, GL_FLOAT, false, 3*4, buffers.coordsBuffer_c);
    }

    public void useTextureProgram() {
        glUseProgram(textureProgramId);
        buffers.coordsBuffer_t.position(0);
        glVertexAttribPointer(aPositionLocation_t, 3, GL_FLOAT, false, (3+2)*4, buffers.coordsBuffer_t);
    }


    public void bindMainObjects() {
        buffers.coordsBuffer_t.position(0);
        glVertexAttribPointer(aPositionLocation_t, OpenGLRend.POSITION_COUNT, GL_FLOAT, false, OpenGLRend.STRIDE_T, buffers.coordsBuffer_t);
    }

    public void bindGameTypeObjects() {
        buffers.coordsBuffer_gameType.position(0);
        glVertexAttribPointer(aPositionLocation_t,  OpenGLRend.POSITION_COUNT, GL_FLOAT, false,  OpenGLRend.STRIDE_T, buffers.coordsBuffer_gameType);
    }


    public static class Buffers {
        public FloatBuffer coordsBuffer_t;
        public ShortBuffer indexBuffer_t;
        public FloatBuffer coordsBuffer_c;
        public ShortBuffer indexBuffer_c;
        public FloatBuffer coordsBuffer_gameType;
        public ShortBuffer indexBuffer_gameType;
        public FloatBuffer coordsBuffer_menu;
        public ShortBuffer indexBuffer_menu;
    }



    public Context getContext() {
        return context;
    }
    public DataManager.SaveData getSaveData() {
        return saveData;
    }

}
