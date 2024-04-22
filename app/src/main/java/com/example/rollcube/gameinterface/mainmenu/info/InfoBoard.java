package com.example.rollcube.gameinterface.mainmenu.info;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.gameinterface.StaticPicture;
import com.example.rollcube.gameinterface.SwitchBlankList;
import com.example.rollcube.gameinterface.mainmenu.BackButton;
import com.example.rollcube.gameinterface.mainmenu.InteractiveNumber;
import com.example.rollcube.gameinterface.mainmenu.MenuBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class InfoBoard extends MenuBoard {

    private final BackButton backButton;

    private final SwitchBlankList switchBlankList;


    public InfoBoard(MainManager mainManager, float coeff) {
        super();
        float k = 0.75f;
        RectF margins = new RectF(0.006f, 0.006f, 0.006f, 0.006f);
        RectF zeroMargins = new RectF(0.0f, 0.0f, 0.0f, 0.0f);

        switchBlankList = new SwitchBlankList(mainManager, margins, 0.200f * k, margins);

        float heightPic = 0.6f;
        float widthPic = heightPic * (1329f / 719f);

        StaticPicture interactingWithNumberPic = new StaticPicture(mainManager, new Point2DFloat(widthPic, heightPic),
                zeroMargins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.info_interacting_with_number;
            }
        };
        StaticPicture resetSettingsPic = new StaticPicture(mainManager, new Point2DFloat(widthPic, heightPic),
                zeroMargins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.info_reset_settings;
            }
        };
        StaticPicture contactsPic = new StaticPicture(mainManager, new Point2DFloat(widthPic, heightPic),
                zeroMargins, Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.info_contacts;
            }
        };

        InteractiveNumber interactiveNumberExample = new InteractiveNumber(mainManager,
                3f, 1f, 5f, 0.005f, 0.1f,
                zeroMargins);

        PictureGroup interactingWithNumberBlank = new PictureGroup();
        PictureGroup resetSettingsBlank = new PictureGroup();
        PictureGroup contactsBlank = new PictureGroup();

        interactingWithNumberBlank.addRelative(interactingWithNumberPic,
                GameInterface.HorizontalRelationType.IN_CENTER, interactingWithNumberBlank,
                GameInterface.VerticalRelativeType.IN_CENTER, interactingWithNumberBlank);
        interactingWithNumberBlank.addRelative(interactiveNumberExample,
                GameInterface.HorizontalRelationType.IN_CENTER, interactingWithNumberBlank,
                GameInterface.VerticalRelativeType.IN_CENTER, interactingWithNumberBlank);
        resetSettingsBlank.addRelative(resetSettingsPic,
                GameInterface.HorizontalRelationType.IN_CENTER, resetSettingsBlank,
                GameInterface.VerticalRelativeType.IN_CENTER, resetSettingsBlank);
        contactsBlank.addRelative(contactsPic,
                GameInterface.HorizontalRelationType.IN_CENTER, contactsBlank,
                GameInterface.VerticalRelativeType.IN_CENTER, contactsBlank);

        interactingWithNumberBlank.updateVerticalPosition(interactiveNumberExample, interactingWithNumberBlank, 0.55f);

        switchBlankList.addBlank(resetSettingsBlank);
        switchBlankList.addBlank(interactingWithNumberBlank);
        switchBlankList.addBlank(contactsBlank);


        addRelative(switchBlankList,
                GameInterface.HorizontalRelationType.IN_CENTER, this,
                GameInterface.VerticalRelativeType.IN_CENTER, this);

        switchBlankList.addButtonNext(this,
                GameInterface.HorizontalRelationType.RIGHT_TO_IN_RIGHT, switchBlankList,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, switchBlankList);
        switchBlankList.addButtonPrev(this,
                GameInterface.HorizontalRelationType.RIGHT_TO_EX_LEFT, switchBlankList.getButtonNext(),
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, switchBlankList);


        backButton = createBackButton(mainManager, 0.256f * k, margins);
    }



    private BackButton createBackButton(MainManager mainManager, float buttonSize, RectF buttonMargins) {
        BackButton backButton = new BackButton(mainManager, buttonSize, buttonMargins) {

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                ((GameInterface) InfoBoard.this.parent).openMainMenu();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }

        };

        addRelative(backButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, switchBlankList);

        return backButton;
    }




}
