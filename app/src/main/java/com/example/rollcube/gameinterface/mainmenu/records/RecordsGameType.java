package com.example.rollcube.gameinterface.mainmenu.records;

import android.graphics.RectF;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.scrollmenu.GameTypeChoiceMenu;


public class RecordsGameType extends GameTypeChoiceMenu {

    private final Records records;

    public RecordsGameType(MainManager mainManager, Records records, RectF margins, float width,
                           float picturesMargin, boolean isVertical) {
        super(mainManager, margins, width, picturesMargin, isVertical);
        this.records = records;
    }



    @Override
    protected void onAnimationEnd(boolean direction) {
        super.onAnimationEnd(direction);
        records.showRecords();
    }



}
