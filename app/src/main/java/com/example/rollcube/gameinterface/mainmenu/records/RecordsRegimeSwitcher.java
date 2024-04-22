package com.example.rollcube.gameinterface.mainmenu.records;

import android.graphics.RectF;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class RecordsRegimeSwitcher extends GameRegimeSwitcher {

    private final Records records;


    public RecordsRegimeSwitcher(MainManager mainManager, float coeff, Records records, RectF margins) {
        super(mainManager, coeff,  margins);
        this.records = records;
    }



    @Override
    protected void createContent(MainManager mainManager, float coeff, RectF margins) {

        //------------------- creating main buttons
        float k_races = 0.4f * coeff;
        Point2DFloat mainButtonsSize = new Point2DFloat(0.874f, 0.512f);
        timeRaceSwitchButton = new RaceSwitchButton(mainManager, Regime.POINT_RACE,
                mainButtonsSize.prod(k_races), margins) {
            @Override
            protected void actSmth() {
                status.regime = Regime.TIME_RACE;
                records.showRecords();
            }
        };
        pointRaceSwitchButton = new RaceSwitchButton(mainManager, Regime.TIME_RACE,
                mainButtonsSize.prod(k_races), margins) {
            @Override
            protected void actSmth() {
                status.regime = Regime.POINT_RACE;
                records.showRecords();
            }
        };
        //------------------------------------------


        //---------------------- adding main buttons
        addSwitchableButton(timeRaceSwitchButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addSwitchableButton(pointRaceSwitchButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, timeRaceSwitchButton);
        //------------------------------------------


        //--------------------- creating race groups
        RectF raceGroupMargins = new RectF(0f,0f,0f,0f);
        RaceSwitchButton.TimeRaceGroup timeRaceGroup = timeRaceSwitchButton.new TimeRaceGroup(raceGroupMargins);
        RaceSwitchButton.PointRaceGroup pointRaceGroup = pointRaceSwitchButton.new PointRaceGroup(raceGroupMargins);
        //-----------------------------------------


        //------------------------ creating buttons
        float k = 0.5f * coeff;
        Point2DFloat button2minSize = new Point2DFloat(0.650f, 0.256f).prod(k);
        Point2DFloat button5minSize = new Point2DFloat(0.650f, 0.256f).prod(k);
        Point2DFloat button10minSize = new Point2DFloat(0.736f, 0.256f).prod(k);
        timeRaceButton2min = timeRaceGroup.new TimeRaceButton(mainManager, 120000L, button2minSize, margins) {
            @Override
            protected void actSmth() {
                status.timeRegime = TimeRegime.TIME_2_MIN;
                records.showRecords();
            }
        };
        timeRaceButton5min = timeRaceGroup.new TimeRaceButton(mainManager, 300000L, button5minSize, margins) {
            @Override
            protected void actSmth() {
                status.timeRegime = TimeRegime.TIME_5_MIN;
                records.showRecords();
            }
        };
        timeRaceButton10min = timeRaceGroup.new TimeRaceButton(mainManager, 600000L, button10minSize, margins) {
            @Override
            protected void actSmth() {
                status.timeRegime = TimeRegime.TIME_10_MIN;
                records.showRecords();
            }
        };

        Point2DFloat button50PointsSize = new Point2DFloat(0.400f, 0.256f).prod(k);
        Point2DFloat button150PointsSize = new Point2DFloat(0.539f, 0.256f).prod(k);
        Point2DFloat button500PointsSize = new Point2DFloat(0.584f, 0.256f).prod(k);
        pointRaceButton50points = pointRaceGroup.new PointRaceButton(mainManager, 50, button50PointsSize, margins) {
            @Override
            protected void actSmth() {
                status.pointsRegime = PointsRegime.POINTS_50;
                records.showRecords();
            }
        };
        pointRaceButton150points = pointRaceGroup.new PointRaceButton(mainManager, 150, button150PointsSize, margins) {
            @Override
            protected void actSmth() {
                status.pointsRegime = PointsRegime.POINTS_150;
                records.showRecords();
            }
        };
        pointRaceButton500points = pointRaceGroup.new PointRaceButton(mainManager, 500, button500PointsSize, margins) {
            @Override
            protected void actSmth() {
                status.pointsRegime = PointsRegime.POINTS_500;
                records.showRecords();
            }
        };
        //-------------------------------------


        //---------------------- adding buttons
        timeRaceGroup.addSwitchableButton(timeRaceButton2min,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, timeRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, timeRaceGroup);
        timeRaceGroup.addSwitchableButton(timeRaceButton5min,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, timeRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, timeRaceButton2min);
        timeRaceGroup.addSwitchableButton(timeRaceButton10min,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, timeRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, timeRaceButton5min);

        pointRaceGroup.addSwitchableButton(pointRaceButton50points,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, pointRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, pointRaceGroup);
        pointRaceGroup.addSwitchableButton(pointRaceButton150points,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, pointRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, pointRaceButton50points);
        pointRaceGroup.addSwitchableButton(pointRaceButton500points,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, pointRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, pointRaceButton150points);
        //------------------------------------


        //---------------------- adding groups
        addRelative(timeRaceGroup,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, timeRaceSwitchButton,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addRelative(pointRaceGroup,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, timeRaceSwitchButton,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        //------------------------------------


        //--------------------------- centring
        updateVerticalPosition(timeRaceGroup, GameInterface.VerticalRelativeType.IN_CENTER, this);
        updateVerticalPosition(pointRaceGroup, GameInterface.VerticalRelativeType.IN_CENTER, this);

        updateVerticalPosition(GameInterface.VerticalRelativeType.IN_CENTER,
                this, timeRaceSwitchButton, pointRaceSwitchButton);
        //------------------------------------
    }





}
