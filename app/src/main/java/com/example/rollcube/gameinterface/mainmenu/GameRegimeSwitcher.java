package com.example.rollcube.gameinterface.mainmenu;


import android.graphics.RectF;

import com.example.rollcube.gameinterface.PictureGroupSwitcher;
import com.example.rollcube.GameBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class GameRegimeSwitcher extends PictureGroupSwitcher {
    public enum Regime {
        POINT_RACE("TIME_REGIME"),
        TIME_RACE("POINTS_REGIME");

        public final String string;
        Regime(String string) {
            this.string = string;
        }
    }
    public enum PointsRegime {
        POINTS_50("POINTS_50"),
        POINTS_150("POINTS_150"),
        POINTS_500("POINTS_500");

        public final String string;
        PointsRegime(String string) {
            this.string = string;
        }
    }
    public enum TimeRegime {
        TIME_2_MIN("TIME_2_MIN"),
        TIME_5_MIN("TIME_5_MIN"),
        TIME_10_MIN("TIME_10_MIN");

        public final String string;
        TimeRegime(String string) {
            this.string = string;
        }
    }

    protected Status status;
    protected RaceSwitchButton timeRaceSwitchButton;
    protected RaceSwitchButton pointRaceSwitchButton;
    protected RaceSwitchButton.TimeRaceGroup.TimeRaceButton timeRaceButton2min;
    protected RaceSwitchButton.TimeRaceGroup.TimeRaceButton timeRaceButton5min;
    protected RaceSwitchButton.TimeRaceGroup.TimeRaceButton timeRaceButton10min;
    protected RaceSwitchButton.PointRaceGroup.PointRaceButton pointRaceButton50points;
    protected RaceSwitchButton.PointRaceGroup.PointRaceButton pointRaceButton150points;
    protected RaceSwitchButton.PointRaceGroup.PointRaceButton pointRaceButton500points;


    public GameRegimeSwitcher(MainManager mainManager, float coeff, RectF margins) {
        super(margins);
        createContent(mainManager, coeff, margins);
        status = new Status();
    }



    protected abstract void createContent(MainManager mainManager, float coeff, RectF margins);

    public boolean isGameRegimeChosen() {
        if (!timeRaceSwitchButton.isChosen() && !pointRaceSwitchButton.isChosen()) {
            return false;
        } else {
            if (timeRaceSwitchButton.isChosen()) {
                return timeRaceButton2min.isChosen() ||
                        timeRaceButton5min.isChosen() ||
                        timeRaceButton10min.isChosen();
            } else {
                return pointRaceButton50points.isChosen() ||
                        pointRaceButton150points.isChosen() ||
                        pointRaceButton500points.isChosen();
            }
        }
    }

    public void collectGameRegime(GameBoard.GameInfo gameInfo) {
        if (timeRaceSwitchButton.isChosen()) {
            gameInfo.regime = Regime.POINT_RACE;
            if (timeRaceButton2min.isChosen()) {
                gameInfo.race = gameInfo.new TimeRace(TimeRegime.TIME_2_MIN);
            }
            else if (timeRaceButton5min.isChosen()) {
                gameInfo.race = gameInfo.new TimeRace(TimeRegime.TIME_5_MIN);
            }
            else {
                gameInfo.race = gameInfo.new TimeRace(TimeRegime.TIME_10_MIN);
            }
        } else {
            gameInfo.regime = Regime.TIME_RACE;
            if (pointRaceButton50points.isChosen()) {
                gameInfo.race = gameInfo.new PointRace(PointsRegime.POINTS_50);
            }
            else if (pointRaceButton150points.isChosen()) {
                gameInfo.race = gameInfo.new PointRace(PointsRegime.POINTS_150);
            }
            else {
                gameInfo.race = gameInfo.new PointRace(PointsRegime.POINTS_500);
            }
        }
    }





    public abstract class RaceSwitchButton extends SwitchButton {
        private final Regime regime;

        public RaceSwitchButton(MainManager mainManager, Regime regime,
                                Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
            this.regime = regime;
        }


        @Override
        public int getChosenTexture() {
            if (regime == Regime.POINT_RACE) {
                return mainManager.texturesId.menu_button_time_race_foc;
            } else {
                return mainManager.texturesId.menu_button_point_race_foc;
            }
        }

        @Override
        public int getUnchosenTexture() {
            if (regime == Regime.POINT_RACE) {
                return mainManager.texturesId.menu_button_time_race_unf;
            } else {
                return mainManager.texturesId.menu_button_point_race_unf;
            }
        }




        public class TimeRaceGroup extends SwitchableSwitchGroup {

            public TimeRaceGroup(RectF margins) {
                super(margins);
            }



            public  abstract class TimeRaceButton extends SwitchableButton {
                private final long startingTime;

                public TimeRaceButton(MainManager mainManager, long startingTime, Point2DFloat inSize, RectF margins) {
                    super(mainManager, inSize, margins);
                    this.startingTime = startingTime;
                }


                @Override
                public int getChosenTexture() {
                    if (startingTime == 120000L) {
                        return mainManager.texturesId.button_time_race_2min_foc;
                    }
                    else if (startingTime == 300000L) {
                        return mainManager.texturesId.button_time_race_5min_foc;
                    }
                    else { // 600000L
                        return mainManager.texturesId.button_time_race_10min_foc;
                    }
                }

                @Override
                public int getUnchosenTexture() {
                    if (startingTime == 120000L) {
                        return mainManager.texturesId.button_time_race_2min_unf;
                    }
                    else if (startingTime == 300000L) {
                        return mainManager.texturesId.button_time_race_5min_unf;
                    }
                    else { // 600000L
                        return mainManager.texturesId.button_time_race_10min_unf;
                    }
                }
            }


        }




        public class PointRaceGroup extends SwitchableSwitchGroup {

            public PointRaceGroup(RectF margins) {
                super(margins);
            }


            public abstract class PointRaceButton extends SwitchableButton {
                private final int startingNumber;

                public PointRaceButton(MainManager mainManager, int startingNumber, Point2DFloat inSize, RectF margins) {
                    super(mainManager, inSize, margins);
                    this.startingNumber = startingNumber;
                }


                @Override
                public int getChosenTexture() {
                    if (startingNumber == 50) {
                        return mainManager.texturesId.button_point_race_50points_foc;
                    }
                    else if (startingNumber == 150) {
                        return mainManager.texturesId.button_point_race_150points_foc;
                    }
                    else { // 500
                        return mainManager.texturesId.button_point_race_500points_foc;
                    }
                }

                @Override
                public int getUnchosenTexture() {
                    if (startingNumber == 50) {
                        return mainManager.texturesId.button_point_race_50points_unf;
                    }
                    else if (startingNumber == 150) {
                        return mainManager.texturesId.button_point_race_150points_unf;
                    }
                    else { // 500
                        return mainManager.texturesId.button_point_race_500points_unf;
                    }
                }
            }


        }

    }





    public static class Status {
        public Regime regime = null;
        public PointsRegime pointsRegime = null;
        public TimeRegime timeRegime = null;
    }

    public Status getStatus() {
        return status;
    }


}
