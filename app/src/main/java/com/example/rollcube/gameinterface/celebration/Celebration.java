package com.example.rollcube.gameinterface.celebration;

import android.graphics.RectF;

import com.example.rollcube.Drawable;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;

import java.util.Random;


public class Celebration implements Drawable {
    public static final float LIGHT_ANGLE = 30f;
    private boolean isCelebrating = false;
    private boolean isTimeCelebrating;
    private final GoldPiece[] goldPieces;
    private final Star[] stars;
    private RectF borders;
    private final RectF defaultBorders;


    public Celebration(RectF borders, MainManager mainManager) {
        this.borders = borders;
        this.defaultBorders = borders;
        Random rndm = new Random();
        goldPieces = new GoldPiece[4];
        for (int i = 0; i < goldPieces.length; i++) {
            float vx = 1.2f * ((rndm.nextFloat() - 0.5f) / 60);
            float vy = 1.2f * ((rndm.nextFloat() - 0.5f) / 60);
            float vw = 20 * ((rndm.nextFloat() - 0.5f));
            goldPieces[i] = new GoldPiece(mainManager, this, new Point2DFloat(0.268f, 0.238f).prod(0.15f), new Point2DFloat(vx, vy), vw);
        }
        stars = new Star[2];
        for (int i = 0; i < stars.length; i++) {
            float vx = 0.4f * ((rndm.nextFloat() - 0.5f) / 60);
            float vy = 0.4f * ((rndm.nextFloat() - 0.5f) / 60);
            float vw = 10 * ((rndm.nextFloat() - 0.5f));
            stars[i] = new Star(mainManager, this, new Point2DFloat(0.268f, 0.238f).prod(0.15f), new Point2DFloat(vx, vy), vw);
        }
    }



    @Override
    public void draw() {
        if(isCelebrating) {
            moveAnimation();
            for (GoldPiece goldPiece : goldPieces) {
                goldPiece.draw();
            }
            for (Star star : stars) {
                star.draw();
            }
        }
    }



    public void giveImpulse(){
        if(isCelebrating) {
            for (GoldPiece goldPiece : goldPieces) {
                goldPiece.giveImpulse();
            }
            for (Star star : stars) {
                star.giveImpulse();
            }
        }
    }

    public void moveAnimation() {
        if(isCelebrating) {
            for (GoldPiece goldPiece : goldPieces) {
                if(goldPiece.animate()) {
                    isCelebrating = false;
                }
            }
            for (Star star : stars) {
                if(star.animate()) {
                    isCelebrating = false;
                }
            }
        }
    }

    public void startCelebration() {
        if(!isCelebrating) {
            for (GoldPiece goldPiece : goldPieces) {
                goldPiece.onStart();
            }
            for (Star star : stars) {
                star.onStart();
            }
            isCelebrating = true;
        }
    }

    public void endCelebration() {
        if(isCelebrating) {
            for (GoldPiece goldPiece : goldPieces) {
                goldPiece.switchOff();
            }
            for (Star star : stars) {
                star.switchOff();
            }
        }
    }


    public RectF getBorders() {
        return borders;
    }

    public void setNewBorders(RectF borders) {
        this.borders = borders;
    }

    public boolean isCelebrating() {
        return isCelebrating;
    }

    public void free() {
        if(isCelebrating) {
            giveImpulse();
            borders = defaultBorders;
        }
    }

    public boolean isTimeCelebrating() {
        return isTimeCelebrating;
    }

    public void setCelebrationType(boolean isTimeCelebrating) {
        this.isTimeCelebrating = isTimeCelebrating;
    }

    public void changeStarsColor(int idx) {
        for (Star star: stars) {
            star.changeColor(idx);
        }
    }

}
