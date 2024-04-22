package com.example.rollcube.gameinterface.mainmenu.records;

import android.graphics.RectF;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.celebration.Celebration;
import com.example.rollcube.gameinterface.mainmenu.BackButton;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.gameinterface.mainmenu.MenuBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class Records extends MenuBoard {
    private final RecordArrays recordArrays;

    private final RecordsGameType recordsGameType;
    private final RecordList recordList;
    private final RecordsRegimeSwitcher recordsRegimeSwitcher;
    private final BackButton backButton;


    public Records(MainManager mainManager, float coeff, Celebration celebration, RecordArrays recordArrays) {
        super();
        RectF margins = new RectF(0.006f, 0.006f, 0.006f, 0.006f);
        this.recordArrays = recordArrays;


        recordsGameType = createRecordsGameType(mainManager, new RectF(0.00f, 0.00f, 0.00f, 0.00f),
                0.18f * coeff, 0.0f);
        recordsRegimeSwitcher = createRecordsRegimeSwitcher(mainManager, coeff, margins);
        backButton = createBackButton(mainManager, 0.256f * 0.75f * coeff, margins);
        recordList = createRecordList(mainManager, coeff, celebration, margins);

        updateHorizontalPosition(recordsGameType, GameInterface.HorizontalRelationType.IN_CENTER, recordsRegimeSwitcher);
    }



    @Override
    public void close() {
        super.close();
        ((GameInterface) Records.this.parent).showOnTimerAndPointCounter();
    }


    private RecordsRegimeSwitcher createRecordsRegimeSwitcher(MainManager mainManager, float coeff, RectF margins) {
        RecordsRegimeSwitcher recordsRegimeSwitcher = new RecordsRegimeSwitcher(mainManager, coeff, this, margins);
        addRelative(recordsRegimeSwitcher,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, recordsGameType);
        return recordsRegimeSwitcher;
    }

    private RecordList createRecordList(MainManager mainManager, float coeff, Celebration celebration, RectF margins) {
        RecordList recordList = new RecordList(mainManager, this, celebration, margins,
                new RectF(0.0f, 0.0f, 0.0f, 0.0f), 2, 5, true,
                0.17f * coeff, 0.01f) {
            @Override
            public int getTexture(MainManager drawManager, int depth, int length) {
                if(depth == 0) {
                    if(length == 0) {
                        return drawManager.texturesId.record_picture_gold_place;
                    }
                    else if(length == 1) {
                        return drawManager.texturesId.record_picture_silver_place;
                    }
                    else if(length == 2) {
                        return drawManager.texturesId.record_picture_bronze_place;
                    }
                    else {
                        return drawManager.texturesId.record_picture_iron_place;
                    }
                } else {
                    return drawManager.texturesId.record_picture_iron_place;
                }
            }
        };

        addRelative(recordList,
                GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, recordsRegimeSwitcher,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);

        return recordList;
    }

    private BackButton createBackButton(MainManager mainManager, float buttonSize, RectF buttonMargins) {
        BackButton backButton = new BackButton(mainManager, buttonSize, buttonMargins) {

            @Override
            public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
                close();
                ((GameInterface) Records.this.parent).openMainMenu();
                return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
            }

        };

        addRelative(backButton,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, recordsRegimeSwitcher);

        return backButton;
    }


    private RecordsGameType createRecordsGameType(MainManager mainManager, RectF buttonsMargins,
                                                  float width, float picturesMargin) {
        RecordsGameType recordsGameType = new RecordsGameType(mainManager, this, buttonsMargins,
                width, picturesMargin, false);
        addRelative(recordsGameType,
                GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
        return recordsGameType;
    }



    public void showRecords() {
        recordList.showRecords(recordArrays, recordsGameType.getFocusIdx(), recordsRegimeSwitcher.getStatus());
    }

    public void prepareAnimatedPictures() {
        recordsGameType.setCenter();
        recordsGameType.setPicturesPositions();
        recordList.prepare();
    }




    public static class RecordArrays {
        public final static int LIST_LENGTH = 10;
        public int[][][] pointRecordsArr;
        public long[][][] timeRecordsArr;
        public boolean wasChanged = false;
        private final int[] lastRecordIdxPath = new int[4];


        public RecordArrays() {
            pointRecordsArr = new int[4][3][LIST_LENGTH];
            timeRecordsArr = new long[4][3][LIST_LENGTH];
        }

        public void initStartingValues() {
            //region FIGURES_3
            //// 2 min
            pointRecordsArr[0][0][0] = 100;
            pointRecordsArr[0][0][1] = 85;
            pointRecordsArr[0][0][2] = 70;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[0][0][k] = 40;
            }

            //// 5 min
            pointRecordsArr[0][1][0] = 250;
            pointRecordsArr[0][1][1] = 210;
            pointRecordsArr[0][1][2] = 170;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[0][1][k] = 150;
            }

            //// 10 min
            pointRecordsArr[0][2][0] = 100 * 5;
            pointRecordsArr[0][2][1] = 85 * 5;
            pointRecordsArr[0][2][2] = 70 * 5;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[0][2][k] = 40 * 5;
            }

            //---------------------------------------

            //// 50 points
            timeRecordsArr[0][0][0] =     40 * 1000L;
            timeRecordsArr[0][0][1] =     60 * 1000L;
            timeRecordsArr[0][0][2] = 2 * 60 * 1000L;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[0][0][k] = 4 * 60 * 1000L;
            }

            //// 150 points
            timeRecordsArr[0][1][0] = 180 * 1000L;
            timeRecordsArr[0][1][1] = 210 * 1000L;
            timeRecordsArr[0][1][2] = 240 * 1000L;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[0][1][k] = 5 * 60 * 1000L;
            }

            //// 150 points
            timeRecordsArr[0][2][0] = (180 * 3 + 60) * 1000L;
            timeRecordsArr[0][2][1] = (210 * 3 + 60) * 1000L;
            timeRecordsArr[0][2][2] = (240 * 3 + 60) * 1000L;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[0][2][k] = (5 * 60 * 3 + 60) * 1000L;
            }
            // endregion

            //region FIGURES_6
            //// 2 min
            pointRecordsArr[1][0][0] = 70;
            pointRecordsArr[1][0][1] = 60;
            pointRecordsArr[1][0][2] = 50;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[1][0][k] = 40;
            }

            //// 5 min
            pointRecordsArr[1][1][0] = 35 * 5;
            pointRecordsArr[1][1][1] = 30 * 5;
            pointRecordsArr[1][1][2] = 25 * 5;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[1][1][k] = 20 * 5;
            }

            //// 10 min
            pointRecordsArr[1][2][0] = 70 * 5;
            pointRecordsArr[1][2][1] = 60 * 5;
            pointRecordsArr[1][2][2] = 50 * 5;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[1][2][k] = 40 * 5;
            }

            //---------------------------------------

            //// 50 points
            timeRecordsArr[1][0][0] =     80 * 1000L;
            timeRecordsArr[1][0][1] =     100 * 1000L;
            timeRecordsArr[1][0][2] =     120 * 1000L;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[1][0][k] = 180 * 1000L;
            }

            //// 150 points
            timeRecordsArr[1][1][0] =     80 * 1000L * 3;
            timeRecordsArr[1][1][1] =     100 * 1000L * 3;
            timeRecordsArr[1][1][2] =     120 * 1000L * 3;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[1][1][k] = 180 * 1000L * 3;
            }

            //// 500 points
            timeRecordsArr[1][2][0] =     80 * 1000L * 10;
            timeRecordsArr[1][2][1] =     100 * 1000L * 10;
            timeRecordsArr[1][2][2] =     120 * 1000L * 10;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[1][2][k] = 180 * 1000L * 10;
            }
            // endregion

            //region DICE
            //// 2 min
            pointRecordsArr[2][0][0] = 50;
            pointRecordsArr[2][0][1] = 45;
            pointRecordsArr[2][0][2] = 35;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[2][0][k] = 25;
            }

            //// 5 min
            pointRecordsArr[2][1][0] = 125;
            pointRecordsArr[2][1][1] = 110;
            pointRecordsArr[2][1][2] = 95;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[2][1][k] = 60;
            }

            //// 10 min
            pointRecordsArr[2][2][0] = 50 * 5;
            pointRecordsArr[2][2][1] = 45 * 5;
            pointRecordsArr[2][2][2] = 35 * 5;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[2][2][k] = 25 * 5;
            }

            //---------------------------------------

            //// 50 points
            timeRecordsArr[2][0][0] =     120 * 1000L;
            timeRecordsArr[2][0][1] =     150 * 1000L;
            timeRecordsArr[2][0][2] =     180 * 1000L;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[2][0][k] = 4 * 60 * 1000L;
            }

            //// 150 points
            timeRecordsArr[2][1][0] =     120 * 1000L * 3;
            timeRecordsArr[2][1][1] =     150 * 1000L * 3;
            timeRecordsArr[2][1][2] =     180 * 1000L * 3;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[2][1][k] = 4 * 60 * 1000L * 3;
            }

            //// 500 points
            timeRecordsArr[2][2][0] =     120 * 1000L * 10;
            timeRecordsArr[2][2][1] =     150 * 1000L * 10;
            timeRecordsArr[2][2][2] =     180 * 1000L * 10;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[2][2][k] = 4 * 60 * 1000L * 10;
            }
            // endregion

            //region XYZ
            //// 2 min
            pointRecordsArr[3][0][0] = 40;
            pointRecordsArr[3][0][1] = 35;
            pointRecordsArr[3][0][2] = 30;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[3][0][k] = 20;
            }

            //// 5 min
            pointRecordsArr[3][1][0] = 20 * 5;
            pointRecordsArr[3][1][1] = 90;
            pointRecordsArr[3][1][2] = 15 * 5;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[3][1][k] = 10 * 5;
            }

            //// 10 min
            pointRecordsArr[3][2][0] = 40 * 5;
            pointRecordsArr[3][2][1] = 35 * 5;
            pointRecordsArr[3][2][2] = 30 * 5;
            for (int k = 3; k < 10; k++) {
                pointRecordsArr[3][2][k] = 20 * 5;
            }

            //---------------------------------------

            //// 50 points
            timeRecordsArr[3][0][0] =     150 * 1000L;
            timeRecordsArr[3][0][1] =     180 * 1000L;
            timeRecordsArr[3][0][2] =     210 * 1000L;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[3][0][k] = 5 * 60 * 1000L;
            }

            //// 150 points
            timeRecordsArr[3][1][0] =     150 * 1000L * 3;
            timeRecordsArr[3][1][1] =     180 * 1000L * 3;
            timeRecordsArr[3][1][2] =     210 * 1000L * 3;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[3][1][k] = 5 * 60 * 1000L * 3;
            }

            //// 500 points
            timeRecordsArr[3][2][0] =     150 * 1000L * 10;
            timeRecordsArr[3][2][1] =     180 * 1000L * 10;
            timeRecordsArr[3][2][2] =     210 * 1000L * 10;
            for (int k = 3; k < 10; k++) {
                timeRecordsArr[3][2][k] = 5 * 60 * 1000L * 10;
            }
            // endregion
        }

        public int checkCandidateOnRecord(GameBoard.GameInfo gameInfo, int candidate) {
            int i = gameInfo.gameType.ordinal();
            int j = ((GameBoard.GameInfo.TimeRace)(gameInfo.race)).timeRegime.ordinal();

            boolean isNewRecord = false;

            int k;
            for (k = 0; k < LIST_LENGTH; k++) {
                if (candidate > pointRecordsArr[i][j][k]) {
                    isNewRecord = true;
                    wasChanged = true;
                    break;
                }
            }

            if(isNewRecord) {
                for (int n = LIST_LENGTH - 1; n > k; n--) {
                    pointRecordsArr[i][j][n] = pointRecordsArr[i][j][n-1];
                }
                pointRecordsArr[i][j][k] = candidate;
            }

            if(isNewRecord) {
                lastRecordIdxPath[0] = i;
                lastRecordIdxPath[1] = GameRegimeSwitcher.Regime.TIME_RACE.ordinal();
                lastRecordIdxPath[2] = j;
                lastRecordIdxPath[3] = k;
                return k;
            } else {
                return -1;
            }
        }

        public int checkCandidateOnRecord(GameBoard.GameInfo gameInfo, long candidate) {
            int i = gameInfo.gameType.ordinal();
            int j = ((GameBoard.GameInfo.PointRace)(gameInfo.race)).pointsRegime.ordinal();

            boolean isNewRecord = false;

            int k;
            for (k = 0; k < LIST_LENGTH; k++) {
                if (candidate < timeRecordsArr[i][j][k]) {
                    isNewRecord = true;
                    wasChanged = true;
                    break;
                }
            }

            if(isNewRecord) {
                for (int n = LIST_LENGTH - 1; n > k; n--) {
                    timeRecordsArr[i][j][n] = timeRecordsArr[i][j][n-1];
                }
                timeRecordsArr[i][j][k] = candidate;
            }

            if(isNewRecord) {
                lastRecordIdxPath[0] = i;
                lastRecordIdxPath[1] = GameRegimeSwitcher.Regime.POINT_RACE.ordinal();
                lastRecordIdxPath[2] = j;
                lastRecordIdxPath[3] = k;
                return k;
            } else {
                return -1;
            }
        }

        public int[] getLastRecordIdxPath() {
            return lastRecordIdxPath;
        }
    }



}
