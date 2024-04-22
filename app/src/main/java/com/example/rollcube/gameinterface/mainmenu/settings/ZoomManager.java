package com.example.rollcube.gameinterface.mainmenu.settings;


import android.graphics.RectF;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.gameinterface.StaticPicture;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class ZoomManager extends PictureGroup {

    private final ZoomNumber zoomNumber;


    public ZoomManager(MainManager mainManager, int startingNumber, RectF margins) {
        super(margins);

        zoomNumber = new ZoomNumber(mainManager, startingNumber, 0.15f,
                new RectF(0.01f, 0.03f, 0.01f, 0.01f));

        StaticPicture textPicture = new StaticPicture(mainManager, new Point2DFloat(0.294f, 0.146f),
                Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.settings_camera_zoom;
            }
        };

        addRelative(textPicture,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(zoomNumber,
                GameInterface.HorizontalRelationType.IN_CENTER, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, textPicture);
    }



    public int getValue() {
        return zoomNumber.getValue();
    }


}
