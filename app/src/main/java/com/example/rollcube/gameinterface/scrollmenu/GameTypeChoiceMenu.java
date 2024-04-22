package com.example.rollcube.gameinterface.scrollmenu;

import android.graphics.RectF;

import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;


public class GameTypeChoiceMenu extends ScrollMenu {


    public GameTypeChoiceMenu(MainManager mainManager, RectF margins, float width, float picturesMargin, boolean isVertical) {
        super(mainManager, margins, width, picturesMargin, isVertical);
    }



    @Override
    protected void createScrollPictures(MainManager mainManager,
                                        GameInterface.HorizontalRelationType horizontalRelationType,
                                        GameInterface.VerticalRelativeType verticalRelativeType,
                                        float width, float picturesMargin
    ) {
        ScrollPicture iconFigures3 = new ScrollGameType(mainManager, width - 2 * picturesMargin, picturesMargin) {
            @Override
            public void setGameType() {
                this.gameType = GameBoard.GameType.FIGURES_3;
            }

            @Override
            public int getGrayTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_figures3_unf;
            }
            @Override
            public int getColorTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_figures3_foc;
            }
        };
        addRelative(iconFigures3, horizontalRelationType, this, verticalRelativeType, this);

        ScrollPicture iconFigures6 = new ScrollGameType(mainManager, width - 2 * picturesMargin, picturesMargin) {
            @Override
            public void setGameType() {
                this.gameType = GameBoard.GameType.FIGURES_6;
            }

            @Override
            public int getGrayTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_figures6_unf;
            }
            @Override
            public int getColorTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_figures6_foc;
            }
        };
        addRelative(iconFigures6, horizontalRelationType, this, verticalRelativeType, this);

        ScrollPicture iconDice = new ScrollGameType(mainManager, width - 2 * picturesMargin, picturesMargin) {
            @Override
            public void setGameType() {
                this.gameType = GameBoard.GameType.DICE;
            }

            @Override
            public int getGrayTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_dice_unf;
            }

            @Override
            public int getColorTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_dice_foc;
            }
        };
        addRelative(iconDice, horizontalRelationType, this, verticalRelativeType, this);

        ScrollPicture iconXYZ = new ScrollGameType(mainManager, width - 2 * picturesMargin, picturesMargin) {
            @Override
            public void setGameType() {
                this.gameType = GameBoard.GameType.XYZ;
            }

            @Override
            public int getGrayTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_XYZ_unf;
            }

            @Override
            public int getColorTextureId() {
                return this.mainManager.texturesId.game_type_scroll_menu_XYZ_foc;
            }
        };
        addRelative(iconXYZ, horizontalRelationType, this, verticalRelativeType, this);

        scrollPictureArr = new ScrollPicture[4];
        scrollPictureArr[0] = iconFigures3;
        scrollPictureArr[1] = iconFigures6;
        scrollPictureArr[2] = iconDice;
        scrollPictureArr[3] = iconXYZ;
    }


    public GameBoard.GameType getFocusGameType() {
        return ((ScrollGameType) scrollPictureArr[focusIdx]).getGameType();
    }


    public abstract class ScrollGameType extends ScrollPicture {
        protected GameBoard.GameType gameType;

        public ScrollGameType(MainManager mainManager, float side, float margin) {
            super(mainManager, side, margin);
            setGameType();
        }


        public abstract void setGameType();
        public GameBoard.GameType getGameType() {
            return gameType;
        }

    }




}
