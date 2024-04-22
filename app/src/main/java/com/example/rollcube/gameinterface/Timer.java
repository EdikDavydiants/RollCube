package com.example.rollcube.gameinterface;

import android.graphics.RectF;

import com.example.rollcube.GameBoard;
import com.example.rollcube.gameinterface.celebration.Celebration;
import com.example.rollcube.gameinterface.celebration.Star;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.gameinterface.timeboard.BoardBackground;
import com.example.rollcube.gameinterface.timeboard.TimeBoard;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public class Timer extends TimeBoard implements Runnable {
    private boolean isPaused = true;
    private GameBoard gameBoard;

    private long startGlobalTime;
    private long lastPauseGlobalTime;
    private long startGameTime;
    private long currentGameTime;
    private long overallPauseTime;
    private final Celebration celebration;


    public Timer(MainManager mainManager, Celebration celebration, RectF margins, float height, long startTime) {
        super(mainManager, margins, height, startTime);
        this.celebration = celebration;

        onStart(startTime);
    }


    @Override
    protected BoardBackground createBackground(MainManager mainManager, float width, float height) {
        return new BoardBackground(mainManager, new Point2DFloat(width, height), Picture.Orientation.NORMAL) {
            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.timer_background;
            }
        };
    }



    @Override
    public void run() {
        while (true) {
            if(!isPaused) {
                try {
                    Thread.sleep(10);
                    switch(getRegime()) {
                        case POINT_RACE:
                            currentGameTime = startGameTime - (System.currentTimeMillis() - startGlobalTime) + overallPauseTime;
                            break;
                        case TIME_RACE:
                            currentGameTime = startGameTime + (System.currentTimeMillis() - startGlobalTime) - overallPauseTime;
                            break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                switch(getRegime()) {
                    case POINT_RACE:
                        if (currentGameTime < displayTime() - 100) {
                            moveTimer(false);
                            if(currentGameTime <= 0) {
                                gameBoard.finishGame();
                            }
                        }
                        break;
                    case TIME_RACE:
                        if (currentGameTime > displayTime() + 100) {
                            moveTimer(true);
                        }
                        break;
                }
            }
            else {
                while (isPaused) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }



    private void moveTimer(boolean isForward) {
        if (!isDashes) {
            if(isForward) {
                if (decisec < 9) {
                    decisec++;
                    decisecView.set(decisec);
                }
                else if (sec_ones < 9) {
                    sec_ones++;
                    decisec = 0;
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else if (sec_tens < 5) {
                    sec_tens++;
                    sec_ones = 0;
                    decisec = 0;
                    secTensView.set(sec_tens);
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else if (min_ones < 9){
                    min_ones++;
                    sec_tens = 0;
                    sec_ones = 0;
                    decisec = 0;
                    minOnesView.set(min_ones);
                    secTensView.set(sec_tens);
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else if (min_tens < 5){
                    min_tens++;
                    min_ones = 0;
                    sec_tens = 0;
                    sec_ones = 0;
                    decisec = 0;
                    minTensView.set(min_tens);
                    minOnesView.set(min_ones);
                    secTensView.set(sec_tens);
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else {
                    isDashes = true;
                    minTensView.setDash();
                    minOnesView.setDash();
                    secTensView.setDash();
                    secOnesView.setDash();
                    decisecView.setDash();
                }
            } else {
                if (decisec > 0) {
                    decisec--;
                    decisecView.set(decisec);
                }
                else if (sec_ones > 0) {
                    sec_ones--;
                    decisec = 9;
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else if (sec_tens > 0) {
                    sec_tens--;
                    sec_ones = 9;
                    decisec = 9;
                    secTensView.set(sec_tens);
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else if (min_ones > 0){
                    min_ones--;
                    sec_tens = 5;
                    sec_ones = 9;
                    decisec = 9;
                    minOnesView.set(min_ones);
                    secTensView.set(sec_tens);
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else if (min_tens > 0){
                    min_tens--;
                    min_ones = 9;
                    sec_tens = 5;
                    sec_ones = 9;
                    decisec = 9;
                    minTensView.set(min_tens);
                    minOnesView.set(min_ones);
                    secTensView.set(sec_tens);
                    secOnesView.set(sec_ones);
                    decisecView.set(decisec);
                }
                else {
                    isPaused = true;
                    minTensView.set(0);
                    minOnesView.set(0);
                    secTensView.set(0);
                    secOnesView.set(0);
                    decisecView.set(0);
                }
            }
        }
    }


    public boolean start() {
        if(isPaused) {
            celebration.endCelebration();
            isPaused = false;
            startGlobalTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    public boolean pause() {
        if(!isPaused) {
            isPaused = true;
            lastPauseGlobalTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    public void resume() {
        isPaused = false;
        overallPauseTime += System.currentTimeMillis() - lastPauseGlobalTime;
    }

    public void reset() {
        isPaused = true;

        minTensView.set(0);
        minOnesView.set(0);
        secTensView.set(0);
        secOnesView.set(0);
        decisecView.set(0);

        min_tens = 0;
        min_ones = 0;
        sec_tens = 0;
        sec_ones = 0;
        decisec = 0;

        startGameTime = 0;
        currentGameTime = 0;
        overallPauseTime = 0;
        isDashes = false;
    }

    public void onStart(long startTime) {
        startGameTime = startTime;
        currentGameTime = startTime;
        overallPauseTime = 0;
        setTime(startTime);
    }


    public long displayTime() {
        if(isDashes) {
            return ((10L * 9 + 9) * 60 + 10L * 9 + 9) * 1000 + 9 * 100L;
        } else {
            return ((10L * min_tens + min_ones) * 60 + 10L * sec_tens + sec_ones) * 1000 + decisec * 100L;
        }
    }
    
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    private GameRegimeSwitcher.Regime getRegime() {
        return gameBoard.getGameInfo().regime;
    }

    public void setStartTime() {
        switch(getRegime()) {
            case POINT_RACE:
                GameRegimeSwitcher.TimeRegime timeRegime = ((GameBoard.GameInfo.TimeRace)(gameBoard.getGameInfo().race)).timeRegime;
                switch(timeRegime) {
                    case TIME_2_MIN:
                        onStart(120000L);
                        break;
                    case TIME_5_MIN:
                        onStart(300000L);
                        break;
                    case TIME_10_MIN:
                        onStart(600000L);
                        break;
                }
                break;
            case TIME_RACE:
                onStart(0L);
                break;
        }
    }

    public boolean isPaused() {
        return isPaused;
    }


    public void takeCelebration() {
        celebration.giveImpulse();
        celebration.setCelebrationType(true);
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
        if(celebration.isCelebrating() && celebration.isTimeCelebrating()) {
            takeCelebration();
        }
    }

    @Override
    public void hide(){
        super.hide();
        if(celebration.isCelebrating() && celebration.isTimeCelebrating()) {
            celebration.free();
        }
    }


    @Override
    public boolean isInteractive() {
        return false;
    }
}
