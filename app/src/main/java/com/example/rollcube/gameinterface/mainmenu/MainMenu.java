package com.example.rollcube.gameinterface.mainmenu;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Button;
import com.example.rollcube.gameinterface.celebration.Celebration;
import com.example.rollcube.gameinterface.mainmenu.info.InfoBoard;
import com.example.rollcube.gameinterface.mainmenu.newgame.NewGame;
import com.example.rollcube.gameinterface.mainmenu.records.Records;
import com.example.rollcube.gameinterface.mainmenu.settings.Settings;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class MainMenu extends MenuBoard {

    private final Button newGameButton;
    private final Button recordsButton;
    private final Button exitButton;
    private final Button settingsButton;
    private final Button infoBoardButton;

    private final NewGame newGame;
    private final Records records;
    private final Settings settings;
    private final InfoBoard infoBoard;


    public MainMenu(MainManager mainManager, GameInterface gameInterface, float coeff, Celebration celebration, Records.RecordArrays recordArrays) {
        super();

        float k_ = coeff * (0.3f / 480f);

        float newGameWidth = 1000f * k_;
        float newGameHeight = 566f * k_;
        float squareButtonSide = 480f * k_;
        float recordHeight = 566f * k_;
        float margin = 20f * k_;

        RectF buttonsMargins = new RectF(margin, margin, margin, margin);
        Point2DFloat newGameButtonSize = new Point2DFloat(newGameWidth, newGameHeight);
        Point2DFloat squareButtonsSize = new Point2DFloat(squareButtonSide, squareButtonSide);
        Point2DFloat recordButtonSize = new Point2DFloat(squareButtonSide, recordHeight);

        newGameButton = createButtonNewGame(mainManager, newGameButtonSize, buttonsMargins);
        exitButton = createButtonExit(mainManager, squareButtonsSize, buttonsMargins);
        settingsButton = createButtonSettings(mainManager, squareButtonsSize, buttonsMargins);
        infoBoardButton = createButtonInfo(mainManager, squareButtonsSize, buttonsMargins);
        recordsButton = createButtonRecords(mainManager, recordButtonSize, buttonsMargins);

        newGame = createNewGameBoard(mainManager, coeff, gameInterface);
        records = createRecordsBoard(mainManager, coeff, gameInterface, celebration, recordArrays);
        settings = createSettingsBoard(mainManager, coeff, gameInterface);
        infoBoard = createInfoBoard(mainManager, coeff, gameInterface);
    }



    @Override
    public void close() {
        super.close();
        newGame.close();
        records.close();
        settings.close();
        infoBoard.close();
    }



    private Button createButtonNewGame(MainManager mainManager, Point2DFloat buttonsSize, RectF buttonsMargins) {
        Button buttonNewGame = new Button(mainManager, buttonsSize, buttonsMargins) {
            @Override
            public void draw() {
                glUniformMatrix4fv(this.mainManager.uMatrixLocation_tp, 1, false, this.mainManager.INTERFACE_matrix, 0);
                glBindTexture(GL_TEXTURE_2D, this.mainManager.texturesId.main_menu_button_new_game);
                this.mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, this.mainManager.buffers.indexBuffer_t);
            }

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                newGame.open();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }
        };

        addRelative(buttonNewGame,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);

        return buttonNewGame;
    }


    private Button createButtonRecords(MainManager mainManager, Point2DFloat buttonsSize, RectF buttonsMargins) {
        Button buttonRecords = new Button(mainManager, buttonsSize, buttonsMargins) {
            @Override
            public void draw() {
                glUniformMatrix4fv(this.mainManager.uMatrixLocation_tp, 1, false, this.mainManager.INTERFACE_matrix, 0);
                glBindTexture(GL_TEXTURE_2D, this.mainManager.texturesId.main_menu_button_records);
                this.mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, this.mainManager.buffers.indexBuffer_t);
            }

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                records.open();
                ((GameInterface) MainMenu.this.parent).hideTimerAndPointCounter();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }
        };

        addRelative(buttonRecords,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, newGameButton,
                GameInterface.VerticalRelativeType.BOTTOM_TO_EX_TOP, settingsButton);

        return buttonRecords;
    }

    private Button createButtonExit(MainManager mainManager, Point2DFloat buttonsSize, RectF buttonsMargins) {
        Button buttonExit = new Button(mainManager, buttonsSize, buttonsMargins) {
            @Override
            public void draw() {
                glUniformMatrix4fv(this.mainManager.uMatrixLocation_tp, 1, false, this.mainManager.INTERFACE_matrix, 0);
                glBindTexture(GL_TEXTURE_2D, this.mainManager.texturesId.main_menu_button_exit);
                this.mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, this.mainManager.buffers.indexBuffer_t);
            }

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                return new ClickEventData(ClickEventData.ClickEvent.EXIT_GAME);
            }
        };

        addRelative(buttonExit,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, newGameButton);

        return buttonExit;
    }

    private Button createButtonInfo(MainManager mainManager, Point2DFloat buttonsSize, RectF buttonsMargins) {
        Button buttonInfo = new Button(mainManager, buttonsSize, buttonsMargins) {
            @Override
            public void draw() {
                glUniformMatrix4fv(this.mainManager.uMatrixLocation_tp, 1, false, this.mainManager.INTERFACE_matrix, 0);
                glBindTexture(GL_TEXTURE_2D, this.mainManager.texturesId.main_menu_button_info);
                this.mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, this.mainManager.buffers.indexBuffer_t);
            }

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                infoBoard.open();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }
        };

        addRelative(buttonInfo,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, settingsButton,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, newGameButton);

        return buttonInfo;
    }

    private Button createButtonSettings(MainManager mainManager, Point2DFloat buttonsSize, RectF buttonsMargins) {
        Button buttonExit = new Button(mainManager, buttonsSize, buttonsMargins) {
            @Override
            public void draw() {
                glUniformMatrix4fv(this.mainManager.uMatrixLocation_tp, 1, false, this.mainManager.INTERFACE_matrix, 0);
                glBindTexture(GL_TEXTURE_2D, this.mainManager.texturesId.main_menu_button_settings);
                this.mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, this.mainManager.buffers.indexBuffer_t);
            }

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                settings.open();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }


        };

        addRelative(buttonExit,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, exitButton,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, newGameButton);

        return buttonExit;
    }



    private NewGame createNewGameBoard(MainManager mainManager, float coeff, GameInterface gameInterface) {
        NewGame newGame = new NewGame(mainManager, coeff);
        gameInterface.addRelative(newGame,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, gameInterface.getSideMenu(),
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, gameInterface);
        return newGame;
    }

    private Settings createSettingsBoard(MainManager mainManager, float coeff, GameInterface gameInterface) {
        Settings settings = new Settings(mainManager, coeff);
        gameInterface.addRelative(settings,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, gameInterface.getSideMenu(),
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, gameInterface);
        return settings;
    }

    private Records createRecordsBoard(MainManager mainManager, float coeff, GameInterface gameInterface, Celebration celebration, Records.RecordArrays recordArrays) {
        Records records = new Records(mainManager, coeff, celebration, recordArrays);
        gameInterface.addRelative(records,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, gameInterface.getSideMenu(),
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, gameInterface);
        return records;
    }

    private InfoBoard createInfoBoard(MainManager mainManager, float coeff, GameInterface gameInterface) {
        InfoBoard infoBoard = new InfoBoard(mainManager, coeff);
        gameInterface.addRelative(infoBoard,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, gameInterface.getSideMenu(),
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, gameInterface);
        return infoBoard;
    }



    public void prepareAnimatedPictures() {
        newGame.setScrollMenu();
        records.prepareAnimatedPictures();
    }




}
