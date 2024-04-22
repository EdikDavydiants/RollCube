package com.example.rollcube.gameinterface.mainmenu.newgame;

import android.graphics.RectF;

import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class NewGameRegimeSwitcher extends GameRegimeSwitcher {

    private final PlayButton playButton;


    public NewGameRegimeSwitcher(MainManager mainManager, PlayButton playButton, float coeff, RectF margins) {
        super(mainManager, coeff, margins);
        this.playButton = playButton;
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
                updatePlayButtonCondition();
            }
        };
        pointRaceSwitchButton = new RaceSwitchButton(mainManager, Regime.TIME_RACE,
                mainButtonsSize.prod(k_races), margins) {
            @Override
            protected void actSmth() {
                updatePlayButtonCondition();
            }
        };
        //------------------------------------------


        //---------------------- adding main buttons
        addSwitchableButton(timeRaceSwitchButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        addSwitchableButton(pointRaceSwitchButton,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, timeRaceSwitchButton,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
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
                updatePlayButtonCondition();
            }
        };
        timeRaceButton5min = timeRaceGroup.new TimeRaceButton(mainManager, 300000L, button5minSize, margins) {
            @Override
            protected void actSmth() {
                updatePlayButtonCondition();
            }
        };
        timeRaceButton10min = timeRaceGroup.new TimeRaceButton(mainManager, 600000L, button10minSize, margins) {
            @Override
            protected void actSmth() {
                updatePlayButtonCondition();
            }
        };

        Point2DFloat button50PointsSize = new Point2DFloat(0.400f, 0.256f).prod(k);
        Point2DFloat button150PointsSize = new Point2DFloat(0.539f, 0.256f).prod(k);
        Point2DFloat button500PointsSize = new Point2DFloat(0.584f, 0.256f).prod(k);
        pointRaceButton50points = pointRaceGroup.new PointRaceButton(mainManager, 50, button50PointsSize, margins) {
            @Override
            protected void actSmth() {
                updatePlayButtonCondition();
            }
        };
        pointRaceButton150points = pointRaceGroup.new PointRaceButton(mainManager, 150, button150PointsSize, margins) {
            @Override
            protected void actSmth() {
                updatePlayButtonCondition();
            }
        };
        pointRaceButton500points = pointRaceGroup.new PointRaceButton(mainManager, 500, button500PointsSize, margins) {
            @Override
            protected void actSmth() {
                updatePlayButtonCondition();
            }
        };
        //-------------------------------------


        //---------------------- adding buttons
        timeRaceGroup.addSwitchableButton(timeRaceButton2min,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, timeRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, timeRaceGroup);
        timeRaceGroup.addSwitchableButton(timeRaceButton5min,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, timeRaceButton2min,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, timeRaceGroup);
        timeRaceGroup.addSwitchableButton(timeRaceButton10min,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, timeRaceButton5min,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, timeRaceGroup);

        pointRaceGroup.addSwitchableButton(pointRaceButton50points,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, pointRaceGroup,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, pointRaceGroup);
        pointRaceGroup.addSwitchableButton(pointRaceButton150points,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, pointRaceButton50points,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, pointRaceGroup);
        pointRaceGroup.addSwitchableButton(pointRaceButton500points,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, pointRaceButton150points,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, pointRaceGroup);
        //------------------------------------


        //---------------------- adding groups
        addRelative(timeRaceGroup,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, timeRaceSwitchButton);
        addRelative(pointRaceGroup,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, pointRaceSwitchButton);
        //------------------------------------


        //--------------------------- centring
        updateHorizontalPosition(timeRaceGroup, GameInterface.HorizontalRelationType.IN_CENTER, this);
        updateHorizontalPosition(pointRaceGroup, GameInterface.HorizontalRelationType.IN_CENTER, this);

        updateHorizontalPosition(GameInterface.HorizontalRelationType.IN_CENTER,
                this, timeRaceSwitchButton, pointRaceSwitchButton);
        //------------------------------------
    }


    private void updatePlayButtonCondition() {
        if(isGameRegimeChosen()) {
            playButton.activate();
        } else {
            playButton.deactivate();
        }
    }

}
