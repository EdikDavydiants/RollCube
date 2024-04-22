package com.example.rollcube.gameinterface;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glEnable;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.Drawable;
import com.example.rollcube.GameView;
import com.example.rollcube.GestureController;
import com.example.rollcube.gameinterface.celebration.Celebration;
import com.example.rollcube.gameinterface.mainmenu.MainMenu;
import com.example.rollcube.gameinterface.okpuzzle.OkBoard;
import com.example.rollcube.gameinterface.mainmenu.records.Records;
import com.example.rollcube.gameinterface.sidemenu.SideMenu;
import com.example.rollcube.gameinterface.buttons.StartButton;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class GameInterface extends PictureGroup implements Drawable {
    public enum VerticalRelativeType {      BOTTOM_TO_IN_BOTTOM, BOTTOM_TO_IN_TOP, TOP_TO_IN_BOTTOM, TOP_TO_IN_TOP,
                                            BOTTOM_TO_EX_BOTTOM, BOTTOM_TO_EX_TOP, TOP_TO_EX_BOTTOM, TOP_TO_EX_TOP,
                                            IN_CENTER }
    public enum HorizontalRelationType {    LEFT_TO_IN_LEFT, LEFT_TO_IN_RIGHT, RIGHT_TO_IN_LEFT, RIGHT_TO_IN_RIGHT,
                                            LEFT_TO_EX_LEFT, LEFT_TO_EX_RIGHT, RIGHT_TO_EX_LEFT, RIGHT_TO_EX_RIGHT,
                                            IN_CENTER }

    private final MainManager mainManager;

    private final StartButton startButton;
    private final SideMenu sideMenu;
    private final Timer timer;
    private final Pointer pointer;
    private final MainMenu mainMenu;
    private final OkBoard okBoard;
    public Celebration celebration;


    public GameInterface(MainManager mainManager, float cameraRatio, Records.RecordArrays recordArrays, RectF margins) {
        super(new Point2DFloat(-1f + margins.left, cameraRatio - margins.top),
                new Point2DFloat(2f - margins.top - margins.bottom, 2 * cameraRatio - margins.left - margins.right),
                margins);
        this.mainManager = mainManager;
        parent = this;

        celebration = initCelebration();

        float coeffStartButton = mainManager.getSaveData().startButtonSize;
        float coeffSideMenu = mainManager.getSaveData().buttonToMainMenuSize;
        float coeffTimerAndPointer = mainManager.getSaveData().timerAndPointerSize;
        float coeffMainMenu = mainManager.getSaveData().mainMenuSize;

        startButton = createStartButton(mainManager, coeffStartButton);
        sideMenu = createSideMenu(mainManager, coeffSideMenu);
        timer = createTimer(mainManager, 0.14f * coeffTimerAndPointer, 0L);
        pointer = createPointer(mainManager, 0.17f * coeffTimerAndPointer, 0);
        mainMenu = createMainMenu(mainManager, coeffMainMenu, recordArrays);

        okBoard = createOkBoard(mainManager);

        setVertices();
        mainMenu.prepareAnimatedPictures();
    }



    public Celebration initCelebration() {
        return new Celebration(getExBorders(), mainManager);
    }

    private Timer createTimer(MainManager mainManager, float height, long time) {
        Timer timer = new Timer(mainManager, celebration, new RectF(0.01f, 0.01f, 0.01f, 0.01f), height, time);
        addRelative(timer, HorizontalRelationType.RIGHT_TO_IN_RIGHT, this, VerticalRelativeType.TOP_TO_EX_BOTTOM, startButton);
        return timer;
    }

    private Pointer createPointer(MainManager mainManager, float height, int startingNumber) {
        Pointer pointer = new Pointer(mainManager, celebration, height, startingNumber);
        addRelative(pointer, HorizontalRelationType.RIGHT_TO_IN_RIGHT, this,
                VerticalRelativeType.TOP_TO_EX_BOTTOM, timer);
        return pointer;
    }

    private StartButton createStartButton(MainManager mainManager, float coeff) {
        StartButton startButton = new StartButton(mainManager, new Point2DFloat(0.240f, 0.200f).prod(0.8f * coeff),
                new RectF(0.01f, 0.01f, 0.01f, 0.01f));
        addRelative(startButton, HorizontalRelationType.RIGHT_TO_IN_RIGHT, this,
                VerticalRelativeType.TOP_TO_IN_TOP, this);
        return startButton;
    }

    private SideMenu createSideMenu(MainManager mainManager, float coeff) {
        SideMenu sideMenu = new SideMenu(mainManager, PullOutMenu.PullingOutType.DOWN,
                new RectF(0.01f, 0.01f, 0.01f, 0.01f),
                new Point2DFloat(0.200f, 0.137f).prod(coeff),
                new RectF(0.0f, 0.0f, 0.0f, 0.01f), false);

//        RectF restartButtonMargins = new RectF(0f, 0f, 0f, 0.0f);
//        Point2DFloat restartButtonInSize = new Point2DFloat(0.2f, 0.15f);
//        SideMenu.PullOutButtonTimeRace restartButton = sideMenu.new PullOutButtonTimeRace(drawManager, restartButtonInSize, restartButtonMargins) {
//            @Override
//            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
//                if (sideMenu.getStatus() == PullOutMenu.Status.OPEN) {
//                    sideMenu.closeMenu();
//                    sideMenu.setStatus(PullOutMenu.Status.CLOSING);
//                    return new ClickEventData(ClickEventData.ClickEvent.RESTART);
//                } else {
//                    return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
//                }
//            }
//
//
//            @Override
//            public int getTextureId() {
//                return this.drawManager.texturesId.menu_button_restart;
//            }
//        };
//        sideMenu.addPullOutButton(restartButton, HorizontalRelationType.LEFT_TO_IN_LEFT, VerticalRelativeType.BOTTOM_TO_IN_BOTTOM);

        addRelative(sideMenu,
                HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                VerticalRelativeType.TOP_TO_IN_TOP, this);

        return sideMenu;
    }

    private MainMenu createMainMenu(MainManager mainManager, float coeff, Records.RecordArrays recordArrays) {
        MainMenu mainMenu = new MainMenu(mainManager, this, coeff, celebration, recordArrays);
        addRelative(mainMenu, HorizontalRelationType.LEFT_TO_EX_RIGHT, sideMenu, VerticalRelativeType.IN_CENTER, this);
        return mainMenu;
    }

    private OkBoard createOkBoard(MainManager mainManager) {
        OkBoard okBoard = new OkBoard(mainManager);
        addRelative(okBoard, HorizontalRelationType.IN_CENTER, this, VerticalRelativeType.IN_CENTER, this);
        return okBoard;
    }


    public void setInterfaceMatrix(float[] PROJ_X_VIEW_matrix_interface) {
        float[] VIEW_matrix_interface = new float[16];
        float[] PROJ_matrix_interface = new float[16];
        Matrix.setLookAtM(VIEW_matrix_interface, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.orthoM(PROJ_matrix_interface, 0, exPos.x, exPos.x + exSize.x, exPos.y - exSize.y, exPos.y, -1f, 2f);

        Matrix.multiplyMM(PROJ_X_VIEW_matrix_interface, 0, PROJ_matrix_interface, 0, VIEW_matrix_interface, 0);
    }


    @Override
    public void draw() {
        sideMenu.animate();

        glEnable(GL_BLEND);

        super.draw();
        mainManager.useColorProgram();
        celebration.draw();
        mainManager.useTextureProgram();

        glDisable(GL_BLEND);
    }



    @Override
    public RectF getParamsOnScreen(RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        return new RectF(0f, 0f, GameView.VIEW_SIZE.x, GameView.VIEW_SIZE.y);
    }

    @Override
    public ClickEventData checkClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        interfaceInPosOnScreen = getParamsOnScreen(null, null);
        interfaceInSize = new Point2DFloat(inSize.x, inSize.y);
        return super.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
    }

    @Override
    public boolean checkSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        interfaceInPosOnScreen = getParamsOnScreen(null, null);
        interfaceInSize = new Point2DFloat(inSize.x, inSize.y);
        return super.checkSwipe(swipeData, interfaceInPosOnScreen, interfaceInSize);
    }

    @Override
    public ClickEventData checkLongPress(GestureController.LongPressData longPressData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        interfaceInPosOnScreen = getParamsOnScreen(null, null);
        interfaceInSize = new Point2DFloat(inSize.x, inSize.y);
        return super.checkLongPress(longPressData, interfaceInPosOnScreen, interfaceInSize);
    }


    public Timer getTimer() {
        return timer;
    }
    public Pointer getPointCounter() {
        return pointer;
    }

    public void setStartButtonCondition(StartButton.Status condition) {
        startButton.setCondition(condition);
    }


    public void openMainMenu() {
        mainMenu.open();
        startButton.setClickable(false);
    }

    public void closeMainMenu() {
        mainMenu.close();
        startButton.setClickable(true);
    }

    public void closeSideMenu() {
        sideMenu.closeMenu();
    }

    public void hideTimerAndPointCounter() {
        timer.hide();
        pointer.hide();
    }
    public void showOnTimerAndPointCounter() {
        timer.show();
        pointer.show();
    }

    public void activateOkBoard() {
        okBoard.activate();
    }

    public SideMenu getSideMenu() {
        return sideMenu;
    }



}
