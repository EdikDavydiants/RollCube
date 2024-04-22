package com.example.rollcube.gameinterface.mainmenu.newgame;

import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.gameinterface.Background;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.mainmenu.BackButton;
import com.example.rollcube.gameinterface.mainmenu.MenuBoard;
import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.scrollmenu.GameTypeChoiceMenu;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class NewGame extends MenuBoard {

    private final GameTypeChoiceMenu gameTypeChoiceMenu;
    private final NewGameRegimeSwitcher gameRegimeSwitcher;
    private final BackButton backButton;
    private final PlayButton playButton;


    public NewGame(MainManager mainManager, float coeff) {
        super();
        RectF margins = new RectF(0.04f, 0.04f, 0.04f, 0.04f);

        playButton = new PlayButton(mainManager, this, new Point2DFloat(0.650f, 0.256f).prod(0.75f * coeff), margins);
        gameTypeChoiceMenu = createGameTypeChoiceMenu(mainManager, coeff);
        gameRegimeSwitcher = createGameRegimeSwitcher(mainManager, playButton, coeff);
        backButton = createBackButton(mainManager, 0.256f * 0.75f * coeff, margins);

        addRelative(playButton,
                GameInterface.HorizontalRelationType.RIGHT_TO_IN_RIGHT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, gameRegimeSwitcher);

        updateHorizontalPosition(gameTypeChoiceMenu,
                GameInterface.HorizontalRelationType.IN_CENTER, this);

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



    private GameTypeChoiceMenu createGameTypeChoiceMenu(MainManager mainManager, float coeff) {
        float width = 0.18f * coeff;
        RectF margins = new RectF(0.00f, 0.03f, 0.00f, 0.00f);

        GameTypeChoiceMenu gameTypeChoiceMenu = new GameTypeChoiceMenu(mainManager, margins, width, 0.0f, false);
        addRelative(gameTypeChoiceMenu,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        return gameTypeChoiceMenu;
    }

    private NewGameRegimeSwitcher createGameRegimeSwitcher(MainManager mainManager, PlayButton playButton, float coeff) {
        NewGameRegimeSwitcher newGameRegimeSwitcher = new NewGameRegimeSwitcher(mainManager, playButton, coeff,
                new RectF(0.01f, 0.01f, 0.01f, 0.01f));

        addRelative(newGameRegimeSwitcher,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, gameTypeChoiceMenu);

        return newGameRegimeSwitcher;
    }

    private BackButton createBackButton(MainManager mainManager, float buttonSize, RectF buttonMargins) {
        BackButton backButton = new BackButton(mainManager, buttonSize, buttonMargins) {

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                ((GameInterface) NewGame.this.parent).openMainMenu();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }

        };

        addRelative(backButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, gameRegimeSwitcher);

        return backButton;
    }


    public void setScrollMenu() {
        gameTypeChoiceMenu.setCenter();
        gameTypeChoiceMenu.setPicturesPositions();
    }


    public GameBoard.GameType getGameType() {
        return gameTypeChoiceMenu.getFocusGameType();
    }


    public GameBoard.GameInfo collectGameRegime() {
        GameBoard.GameInfo gameInfo = new GameBoard.GameInfo(getGameType());

        if (gameRegimeSwitcher.isGameRegimeChosen()) {
            gameRegimeSwitcher.collectGameRegime(gameInfo);
            return gameInfo;
        } else {
            return null;
        }

    }








}
