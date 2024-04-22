package com.example.rollcube.gameinterface;

import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import com.example.rollcube.GameBoard;
import com.example.rollcube.gameinterface.celebration.Celebration;
import com.example.rollcube.gameinterface.celebration.Star;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.gameinterface.pointboard.PointBoard;
import com.example.rollcube.gameinterface.timeboard.BoardBackground;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class Pointer extends PointBoard {

    private GameBoard gameBoard;
    private final Celebration celebration;


    public Pointer(MainManager mainManager, Celebration celebration, float height, int startingNumber) {
        super(mainManager, height, startingNumber);
        this.celebration = celebration;

        setNumber(startingNumber);
    }



    @Override
    protected BoardBackground createBackground(MainManager mainManager, float width, float height, float k) {
        return new BoardBackground(mainManager, new Point2DFloat(width, height).prod(k), Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.point_counter_background;
            }
        };
    }



    public boolean isFinished() {
        if(gameBoard.getGameInfo().regime == GameRegimeSwitcher.Regime.POINT_RACE)  {
            return false;
        } else {
            return currentNumber <= 0;
        }
    }

    public int getValue() {
        return currentNumber;
    }

    public void addOrTakePoints(int points, boolean isForward) {
        takeCelebration();

        int current_hundreds = currentNumber / 100;
        int current_tens = (currentNumber / 10) % 10;
        int current_ones = currentNumber % 10;

        if(isForward) {
            currentNumber += points;
        } else {
            currentNumber -= points;
        }

        int new_hundreds = currentNumber / 100;
        int new_tens = (currentNumber / 10) % 10;
        int new_ones = currentNumber % 10;

        if (currentNumber <= 0) {
            new_hundreds = 0;
            new_tens = 0;
            new_ones = 0;
        }
        if (currentNumber > 999) {
            currentNumber = 999;
            new_hundreds = 9;
            new_tens = 9;
            new_ones = 9;
        }

        if (current_hundreds != new_hundreds) {
            hundredsDigit.set(new_hundreds);
        }
        if (current_tens != new_tens) {
            tensDigit.set(new_tens);
        }
        if (current_ones != new_ones) {
            onesDigit.set(new_ones);
        }
    }


    public void setStartPoints() {
        switch(gameBoard.getGameInfo().regime) {
            case POINT_RACE:
                setNumber(0);
                break;
            case TIME_RACE:
                GameRegimeSwitcher.PointsRegime pointsRegime = ((GameBoard.GameInfo.PointRace)(gameBoard.getGameInfo().race)).pointsRegime;
                switch (pointsRegime) {
                    case POINTS_50:
                        setNumber(50);
                        break;
                    case POINTS_150:
                        setNumber(150);
                        break;
                    case POINTS_500:
                        setNumber(500);
                        break;
                }
                break;
        }
    }


    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }


    public void takeCelebration() {
        celebration.giveImpulse();
        celebration.setCelebrationType(false);
        celebration.setNewBorders(getInBorders());
    }

    public void startCelebration(int recordIdx) {
        if(recordIdx < 3) {
            celebration.changeStarsColor(recordIdx);
        } else {
            celebration.changeStarsColor(Star.IRON);
        }
        takeCelebration();
        celebration.startCelebration();
    }


    @Override
    public void show(){
        super.show();
        if(celebration.isCelebrating() && !celebration.isTimeCelebrating()) {
            takeCelebration();
        }
    }

    @Override
    public void hide(){
        super.hide();
        if(celebration.isCelebrating() && !celebration.isTimeCelebrating()) {
            celebration.free();
        }
    }


    @Override
    public boolean isInteractive() {
        return false;
    }

}
