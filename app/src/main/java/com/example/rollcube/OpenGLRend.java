package com.example.rollcube;

import android.opengl.GLSurfaceView.Renderer;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

import com.example.rollcube.managers.MainManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class OpenGLRend implements Renderer {
    /**
     * Position coords number.
     */
    public static final int POSITION_COUNT = 3;
    /**
     * Texture coords number.
     */
    public static final int TEXTURE_COUNT = 2;
    /**
     * Vertex width in bytes for texture program.
     */
    public static final int STRIDE_T = (POSITION_COUNT + TEXTURE_COUNT) * 4;
    /**
     * Vertex width in bytes for color program.
     */
    private static final int STRIDE_C = POSITION_COUNT * 4;
    private final ClassMediator classMediator;
    private final MainManager mainManager;

    private boolean isDrawing = true;
    public Scene scene;



    public OpenGLRend(ClassMediator classMediator, float gameViewRatio) {
        this.classMediator = classMediator;
        this.classMediator.openGLRend = this;

        mainManager = new MainManager(classMediator.getContext());
        scene = new Scene(classMediator, mainManager, gameViewRatio);
    }



    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        // Default clearing colour each frame before drawing
        glClearColor(0.75f, 0.75f, 0.75f, 1f);

        // Blend settings transparent textures.
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        scene.setInterfaceMatrix(mainManager.INTERFACE_matrix);

        mainManager.initBuffersColorObjects();
        mainManager.initBuffersTexturesObjects();
        mainManager.initBuffersGameTypeObjects();

        mainManager.textureProgramId = createProgram_t();
        getLocations_t(mainManager);
        bindData_t(mainManager);

        mainManager.colorProgramId = createProgram_c();
        getLocations_c(mainManager);
        bindData_c(mainManager);

        mainManager.useTextureProgram();

        classMediator.scene.setMainBGIntoCubeController();
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        if(isDrawing) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            scene.draw();
        } else {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if(scene.gameBoard.isNewGameLauncherWaitingFor()) {
            pauseRend();
            scene.gameBoard.allowLaunchNewGame();
        }
    }


    private void bindData_t(MainManager mainManager) {
        mainManager.uploadTextures(classMediator.getContext());

        mainManager.buffers.coordsBuffer_t.position(0);
        glVertexAttribPointer(mainManager.aPositionLocation_t, POSITION_COUNT, GL_FLOAT, false, STRIDE_T, mainManager.buffers.coordsBuffer_t);
        glEnableVertexAttribArray(mainManager.aPositionLocation_t);

        mainManager.buffers.coordsBuffer_t.position(POSITION_COUNT);
        glVertexAttribPointer(mainManager.aTextureLocation, TEXTURE_COUNT, GL_FLOAT, false, STRIDE_T, mainManager.buffers.coordsBuffer_t);
        glEnableVertexAttribArray(mainManager.aTextureLocation);

        glActiveTexture(GL_TEXTURE0);
        glUniform1i(mainManager.uTextureUnitLocation, 0);
    }

    private void bindData_c(MainManager mainManager) {
        mainManager.buffers.coordsBuffer_c.position(0);
        glVertexAttribPointer(mainManager.aPositionLocation_cp, POSITION_COUNT, GL_FLOAT, false, STRIDE_C, mainManager.buffers.coordsBuffer_c);
        glEnableVertexAttribArray(mainManager.aPositionLocation_cp);
    }

    public void getLocations_c(MainManager mainManager) {
        mainManager.aPositionLocation_cp = glGetAttribLocation(mainManager.colorProgramId, "a_Position_c");
        mainManager.uMatrixLocation_cp = glGetUniformLocation(mainManager.colorProgramId, "u_Matrix_c");
        mainManager.uColorLocation = glGetUniformLocation(mainManager.colorProgramId, "u_Color_c");
    }

    public void getLocations_t(MainManager mainManager) {
        mainManager.aPositionLocation_t = glGetAttribLocation(mainManager.textureProgramId, "a_Position_t");
        mainManager.uMatrixLocation_tp = glGetUniformLocation(mainManager.textureProgramId, "u_Matrix_t");
        mainManager.uTextureUnitLocation = glGetUniformLocation(mainManager.textureProgramId, "u_TextureUnit_t");
        mainManager.aTextureLocation = glGetAttribLocation(mainManager.textureProgramId, "a_Texture_t");
    }

    /**
     * Method creates texture shader.
     * @return Returns program id.
     */
    public int createProgram_t() {
        int vertexTextureShaderId = ShaderUtils.createShader(classMediator.getContext(), GL_VERTEX_SHADER, R.raw.vertex_shader_t);
        int fragmentTextureShaderId = ShaderUtils.createShader(classMediator.getContext(), GL_FRAGMENT_SHADER, R.raw.fragment_shader_t);
        return ShaderUtils.createProgram(vertexTextureShaderId, fragmentTextureShaderId);
    }

    /**
     * Method creates color shader.
     * @return Returns program id.
     */
    public int createProgram_c() {
        int vertexColorShaderId = ShaderUtils.createShader(classMediator.getContext(), GL_VERTEX_SHADER, R.raw.vertex_shader_c);
        int fragmentColorShaderId = ShaderUtils.createShader(classMediator.getContext(), GL_FRAGMENT_SHADER, R.raw.fragment_shader_c);
        return ShaderUtils.createProgram(vertexColorShaderId, fragmentColorShaderId);
    }


    public void resumeRend() {
        isDrawing = true;
    }
    public void pauseRend() {
        isDrawing = false;
    }



}