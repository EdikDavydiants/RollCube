package com.example.rollcube.gameinterface.mainmenu.settings;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.managers.DataManager;
import com.example.rollcube.gameinterface.Background;
import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.gameinterface.StaticPicture;
import com.example.rollcube.gameinterface.SwitchBlankList;
import com.example.rollcube.gameinterface.mainmenu.BackButton;
import com.example.rollcube.gameinterface.mainmenu.MenuBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class Settings extends MenuBoard {

    private final BackButton backButton;
    private final Button saveButton;

    private SizeManager mainMenuSizeManager;
    private SizeManager buttonToMainMenuSizeManager;
    private SizeManager timerAndPointerSizeManager;
    private SizeManager startButtonSizeManager;
    private ZoomManager zoomManager;
    private final SwitchBlankList switchBlankList;


    public Settings(MainManager mainManager, float coeff) {
        super();
        float k = 0.75f;
        RectF margins = new RectF(0.006f, 0.006f, 0.006f, 0.006f);
        RectF zeroMargins = new RectF(0.0f, 0.0f, 0.0f, 0.0f);

        PictureGroup sizeSettingsBlank = createSizeSettingsBlank(mainManager, margins);
        zoomManager = createZoomSettingBlank(mainManager, margins);

        switchBlankList = new SwitchBlankList(mainManager, zeroMargins, 0.200f * k, margins);
        switchBlankList.addBlank(sizeSettingsBlank);
        switchBlankList.addBlank(zoomManager);

        addRelative(switchBlankList,
                GameInterface.HorizontalRelationType.IN_CENTER, this,
                GameInterface.VerticalRelativeType.IN_CENTER, this);

        backButton = createBackButton(mainManager, 0.256f * k, margins);
        saveButton = createSaveButton(mainManager, new Point2DFloat(0.250f * k, 0.250f * k), margins);

        switchBlankList.addButtonPrev(this,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, saveButton,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, switchBlankList);
        switchBlankList.addButtonNext(this,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, switchBlankList.getButtonPrev(),
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, switchBlankList);


        setBackground(new Background(mainManager, this, Picture.Orientation.NORMAL) {
            @Override
            public void animate(float[] animMatrix) {

            }

            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.menu_background;
            }
        });
    }




    private BackButton createBackButton(MainManager mainManager, float buttonSize, RectF buttonMargins) {
        BackButton backButton = new BackButton(mainManager, buttonSize, buttonMargins) {

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                ((GameInterface) Settings.this.parent).openMainMenu();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }
        };

        addRelative(backButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, switchBlankList);

        return backButton;
    }

    private Button createSaveButton(MainManager mainManager, Point2DFloat inSize, RectF buttonMargins) {
        Button saveButton = new Button(mainManager, inSize, buttonMargins) {

            @Override
            public void draw() {
                glUniformMatrix4fv(this.mainManager.uMatrixLocation_tp, 1, false, this.mainManager.INTERFACE_matrix, 0);
                glBindTexture(GL_TEXTURE_2D, this.mainManager.texturesId.main_menu_button_save);
                this.mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, this.mainManager.buffers.indexBuffer_t);
            }

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                DataManager.SaveData saveData = this.mainManager.getSaveData();

                saveData.isFirstLaunch = false;

                saveData.mainMenuSize = mainMenuSizeManager.getValue();
                saveData.buttonToMainMenuSize = buttonToMainMenuSizeManager.getValue();
                saveData.timerAndPointerSize = timerAndPointerSizeManager.getValue();
                saveData.startButtonSize = startButtonSizeManager.getValue();

                saveData.cameraZoom = zoomManager.getValue();

                new DataManager().savePref(this.mainManager.getContext(), saveData);

                close();
                ((GameInterface) Settings.this.parent).openMainMenu();

                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }
        };

        addRelative(saveButton,
                GameInterface.HorizontalRelationType.IN_CENTER, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, switchBlankList);

        return saveButton;
    }


    private PictureGroup createSizeSettingsBlank(MainManager mainManager, RectF margins) {
        RectF zeroMargins = new RectF(0.0f, 0.0f, 0.0f, 0.0f);

        PictureGroup sizeManagersGroup = new PictureGroup(zeroMargins);

        float width1 = 0.423f;
        float width2 = 0.354f;
        float width3 = 0.478f;
        float height1 = width1 * (208f/354);
        float height2 = width1 * (148f/354);
        float height3 = width2 * (146f/423);

        StaticPicture interfaceSizesPic = new StaticPicture(mainManager, new Point2DFloat(width2, height3),
                margins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.settings_interface_sizes;
            }
        };
        StaticPicture mainMenuPic = new StaticPicture(mainManager, new Point2DFloat(width1, height2),
                zeroMargins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.settings_main_menu;
            }
        };
        StaticPicture buttonToMainMenuPic = new StaticPicture(mainManager, new Point2DFloat(width1, height1),
                zeroMargins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.settings_button_to_main_menu;
            }
        };
        StaticPicture timerAndPointerPic = new StaticPicture(mainManager, new Point2DFloat(width1, height1),
                zeroMargins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.settings_timer_and_pointer;
            }
        };
        StaticPicture startButtonPic = new StaticPicture(mainManager, new Point2DFloat(width3, height2),
                zeroMargins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.settings_start_button;
            }
        };

        mainMenuSizeManager = new SizeManager(mainManager, mainMenuPic, margins, 1f, 0.2f, 5f);
        buttonToMainMenuSizeManager = new SizeManager(mainManager, buttonToMainMenuPic, margins, 1f, 0.2f, 5f);
        timerAndPointerSizeManager = new SizeManager(mainManager, timerAndPointerPic, margins, 1f, 0.2f, 5f);
        startButtonSizeManager = new SizeManager(mainManager, startButtonPic, margins, 1f, 0.2f, 5f);

        sizeManagersGroup.addRelative(interfaceSizesPic,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, sizeManagersGroup,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, sizeManagersGroup);

        sizeManagersGroup.addRelative(mainMenuSizeManager,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, sizeManagersGroup,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, interfaceSizesPic);
        sizeManagersGroup.addRelative(startButtonSizeManager,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, mainMenuSizeManager,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, interfaceSizesPic);

        sizeManagersGroup.addRelative(buttonToMainMenuSizeManager,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, sizeManagersGroup,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, mainMenuSizeManager);
        sizeManagersGroup.addRelative(timerAndPointerSizeManager,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, buttonToMainMenuSizeManager,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, mainMenuSizeManager);

        sizeManagersGroup.updateHorizontalPosition(interfaceSizesPic, GameInterface.HorizontalRelationType.IN_CENTER, sizeManagersGroup);
        sizeManagersGroup.updateHorizontalPosition(GameInterface.HorizontalRelationType.IN_CENTER, sizeManagersGroup,
                buttonToMainMenuSizeManager, timerAndPointerSizeManager);

        DataManager.SaveData saveData = mainManager.getSaveData();
        float mainMenuSize = saveData.mainMenuSize;
        float startButtonSize = saveData.startButtonSize;
        float buttonToMainMenuSize = saveData.buttonToMainMenuSize;
        float timerAndPointerSize = saveData.timerAndPointerSize;

        mainMenuSizeManager.setNumber(mainMenuSize);
        startButtonSizeManager.setNumber(startButtonSize);
        buttonToMainMenuSizeManager.setNumber(buttonToMainMenuSize);
        timerAndPointerSizeManager.setNumber(timerAndPointerSize);

        return sizeManagersGroup;
    }


    private ZoomManager createZoomSettingBlank(MainManager mainManager, RectF margins) {
        return new ZoomManager(mainManager, mainManager.getSaveData().cameraZoom, margins);
    }




}
