package com.example.rollcube.gameinterface.mainmenu.settings;

import android.graphics.RectF;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.gameinterface.StaticPicture;
import com.example.rollcube.gameinterface.mainmenu.InteractiveNumber;

public class SizeManager extends PictureGroup {

    private final InteractiveNumber interactiveNumber;

    public SizeManager(MainManager mainManager, StaticPicture textPicture, RectF margins,
                       float startingNumber, float bottomValue, float topValue) {
        super(margins);

        interactiveNumber = new InteractiveNumber(mainManager,
                startingNumber, bottomValue, topValue,
                0.005f, 0.1f, new RectF(0.01f, 0.01f, 0.01f, 0.01f));

        addRelative(textPicture,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);

        addRelative(interactiveNumber,
                GameInterface.HorizontalRelationType.IN_CENTER, textPicture,
                GameInterface.VerticalRelativeType.BOTTOM_TO_IN_BOTTOM, textPicture);
    }



    public float getValue() {
        return interactiveNumber.getNumber();
    }

    public void setNumber(float value) {
        interactiveNumber.setNumber(value);
    }




}
