package com.example.rollcube.gameinterface.mainmenu.records;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.Animated;
import com.example.rollcube.gameinterface.FlippingList;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.celebration.Celebration;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.gameinterface.pointboard.PointBoard;
import com.example.rollcube.gameinterface.timeboard.TimeBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class RecordList extends FlippingList {

    private final Records records;
    private final Celebration celebration;
    private boolean isTimeBoard = false;
    private boolean isFirstFive = true;
    private boolean doesHaveCelebration = false;


    public RecordList(MainManager mainManager, Records records, Celebration celebration, RectF margins, RectF flipMargins,
                      int depth, int length, boolean isVertical, float flipHeight, float pictureMargin) {
        super(mainManager, margins, flipMargins, depth, length, isVertical, flipHeight, pictureMargin);
        this.records = records;
        this.celebration = celebration;
        createFlips(mainManager, flipMargins, flipHeight, pictureMargin);
        setAllDashes();
    }



    @Override
    protected void createFlips(MainManager mainManager, RectF margins, float height, float pictureMargin) {
        for (int i = 0; i < length; i++) {
            flippingPictureGroupArr[i] = new FlippingRecordGroup(mainManager, margins, height, pictureMargin, i);
            if (i == 0) {
                addRelative(flippingPictureGroupArr[0],
                        GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                        GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
            } else {
                if(isVertical) {
                    addRelative(flippingPictureGroupArr[i],
                            GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                            GameInterface.VerticalRelativeType.TOP_TO_EX_BOTTOM, flippingPictureGroupArr[i-1]);
                } else {
                    addRelative(flippingPictureGroupArr[i],
                            GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, flippingPictureGroupArr[i-1],
                            GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
                }
            }
        }
    }


    @Override
    protected void onAnimationEnd(boolean direction) {
        if (status == Status.CLOSING) {
            isFirstFive = direction;
            records.showRecords();
        }
        super.onAnimationEnd(direction);
    }


    public void showRecords(Records.RecordArrays recordArrays, int focusGameType,
                            GameRegimeSwitcher.Status status) {
        if (status.regime == null){
            setAllDashes();
        } else {
            this.isTimeBoard = status.regime == GameRegimeSwitcher.Regime.POINT_RACE;
            if (isTimeBoard) {
                if (status.pointsRegime != null) {
                    takeCelebration(focusGameType, status, flippingPictureGroupArr, celebration, recordArrays);
                    for (int i = 0; i < length; i++) {
                        long timeRecord;
                        if (isFirstFive) {
                            timeRecord = recordArrays.timeRecordsArr[focusGameType][status.pointsRegime.ordinal()][i];
                        } else {
                            timeRecord = recordArrays.timeRecordsArr[focusGameType][status.pointsRegime.ordinal()][5 + i];
                        }
                        FlippingRecordGroup flippingRecordGroup = (FlippingRecordGroup) flippingPictureGroupArr[i];
                        flippingRecordGroup.setRecord(timeRecord);
                    }
                } else {
                    setAllDashes();
                }
            }
            else {
                if (status.timeRegime != null) {
                    takeCelebration(focusGameType, status, flippingPictureGroupArr, celebration, recordArrays);
                    for (int i = 0; i < length; i++) {
                        int pointRecord;
                        if (isFirstFive) {
                            pointRecord = recordArrays.pointRecordsArr[focusGameType][status.timeRegime.ordinal()][i];
                        }
                        else  {
                            pointRecord = recordArrays.pointRecordsArr[focusGameType][status.timeRegime.ordinal()][5 + i];
                        }
                        FlippingRecordGroup flippingRecordGroup = (FlippingRecordGroup) flippingPictureGroupArr[i];
                        flippingRecordGroup.setRecord(pointRecord);
                    }
                } else {
                    setAllDashes();
                }
            }
        }
    }

    private void takeCelebration(int focusGameType, GameRegimeSwitcher.Status status,
                                 FlippingPictureGroup[] flippingPictureGroupArr, Celebration celebration,
                                 Records.RecordArrays recordArrays) {
        if(celebration.isCelebrating()) {
            int[] path = recordArrays.getLastRecordIdxPath();
            boolean isGameTypeMatch = focusGameType == path[0];
            boolean isRegimeMatch = status.regime.ordinal() == path[1];
            boolean isLevelMatch = false;
            switch (status.regime) {
                case POINT_RACE:
                    isLevelMatch = status.pointsRegime.ordinal() == path[2];
                    break;
                case TIME_RACE:
                    isLevelMatch = status.timeRegime.ordinal() == path[2];
                    break;
            }
            boolean isRecordMatch = (isFirstFive && path[3] < 5) || (!isFirstFive && path[3] >= 5);
            if(isGameTypeMatch && isRegimeMatch && isLevelMatch && isRecordMatch) {
                int i = path[3];
                if(!isFirstFive) {
                    i -= 5;
                }
                FlippingRecordGroup flippingRecordGroups = (FlippingRecordGroup) flippingPictureGroupArr[i];
                flippingRecordGroups.takeCelebration(celebration);
                doesHaveCelebration = true;
            } else {
                if (doesHaveCelebration) {
                    celebration.free();
                    doesHaveCelebration = false;
                }

            }
        }
    }

    public void setAllDashes() {
        for (int i = 0; i < length; i++) {
            ((FlippingRecordGroup)flippingPictureGroupArr[i]).setDashes();
        }
    }





    public class FlippingRecordGroup extends FlippingPictureGroup {
        private final FlippingRecordPicture flippingRecordPicture;
        private final PointBoard pointBoard;
        private final TimeBoard timeBoard;
        private final int numberInList;


        public FlippingRecordGroup(MainManager mainManager, RectF margins, float height, float pictureMargin, int numberInList) {
            super(margins);
            this.numberInList = numberInList;
            float boardsHeight = height * 0.9f;
            pointBoard = new PointBoard(mainManager, boardsHeight, 0);
            timeBoard = new TimeBoard(mainManager, margins, boardsHeight, 0L);
            flippingRecordPicture = createFlippingPicture(mainManager, height, pictureMargin, 0.002f);

            addRelative(flippingRecordPicture,
                    GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT, this,
                    GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);

            addRelative(pointBoard,
                    GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, flippingRecordPicture,
                    GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);
            addRelative(timeBoard,
                    GameInterface.HorizontalRelationType.LEFT_TO_EX_RIGHT, flippingRecordPicture,
                    GameInterface.VerticalRelativeType.TOP_TO_IN_TOP, this);

            updateVerticalPosition(pointBoard, GameInterface.VerticalRelativeType.IN_CENTER, this);
            updateVerticalPosition(timeBoard, GameInterface.VerticalRelativeType.IN_CENTER, this);
        }


        public FlippingRecordPicture createFlippingPicture(MainManager mainManager,
                                                           float height, float rightMargin, float verticalMargins) {
            return new FlippingRecordPicture(
                    mainManager, new Point2DFloat(height, height),
                    new RectF(0f, verticalMargins, rightMargin, verticalMargins));
        }


        @Override
        public void draw() {
            flippingRecordPicture.draw();
            if(isTimeBoard) {
                timeBoard.draw();
            } else {
                pointBoard.draw();
            }
        }

        @Override
        public void animateGroup(float[] animMatrix) {
            flippingRecordPicture.animate(animMatrix);
            if(isTimeBoard) {
                timeBoard.animate(animMatrix);
            } else {
                pointBoard.animate(animMatrix);
            }
        }

        public void setRecord(long timeRecord) {
            timeBoard.setTime(timeRecord);
        }

        public void setRecord(int pointRecord) {
            pointBoard.setNumber(pointRecord);
        }

        public void setDashes() {
            timeBoard.setDashes();
            pointBoard.setDashes();
        }

        public void takeCelebration(Celebration celebration) {
            celebration.setNewBorders(getInBorders());
        }



        public class FlippingRecordPicture extends Picture implements Animated {

            public FlippingRecordPicture(MainManager mainManager, Point2DFloat inSize,
                                         RectF margins) {
                super(mainManager, inSize, margins, Orientation.NORMAL);
            }

            @Override
            public void animate(float[] animMatrix) {
                Matrix.multiplyMM(mModelMatrix, 0, animMatrix, 0, mModelMatrix, 0);
                //Matrix.multiplyMM(mMatrix, 0, drawManager.PROJ_X_VIEW_matrix_interface, 0, animMatrix, 0);
            }


            @Override
            public void draw() {
                Matrix.multiplyMM(mMatrix, 0, mainManager.INTERFACE_matrix, 0, mModelMatrix, 0);
                glUniformMatrix4fv(mainManager.uMatrixLocation_tp, 1, false, mMatrix, 0);
                glBindTexture(GL_TEXTURE_2D, getTexture(mainManager, activeDepth, numberInList));
                mainManager.buffers.indexBuffer_t.position(startIndex);
                glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, mainManager.buffers.indexBuffer_t);
            }



        }



    }






}
