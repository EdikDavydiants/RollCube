package com.example.rollcube.gameobjects.walls;

import android.opengl.Matrix;

import com.example.rollcube.Animation;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.gameobjects.gaps.GapObject;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.sides.Side;

import java.util.Random;


public abstract class Wall extends GapObject {
    public enum WallSide {LEFT, RIGHT, FORWARD, BACK}
    public enum Status {DEACTIVATED, ACTIVATING, ACTIVATED, DEACTIVATING}
    public enum MoveDirection {UP, DOWN}

    protected Vector3DFloat n_vect = null;
    private final AnimationData animationData;
    protected Side side;
    protected Status status = Status.DEACTIVATED;
    protected final Vector3DFloat xVect = new Vector3DFloat(1f, 0f, 0f);
    protected final Vector3DFloat zVect = new Vector3DFloat(0f, 0f, 1f);


    public Wall(MainManager mainManager, Type type, GameBoard.GameType gameType) {
        super(mainManager, type, 0, 0, gameType);

        animationData = new AnimationData();

        if(type == Type.M) {
            Matrix.setRotateM(mMatrix, 0, -90f, 0, 1, 0);
            Matrix.translateM(I1, 0, GameBoard.GAP_WIDTH, 0, 0);

            Matrix.multiplyMM(mModelMatrix, 0, mMatrix, 0, mModelMatrix, 0);
            Matrix.multiplyMM(mModelMatrix, 0, I1, 0, mModelMatrix, 0);
        }

        setVertices(GameBoard.GAP_WIDTH, 0, type);
    }



    public abstract void setRandomSide(Random rndm);

    public boolean isActivated() {
        return status == Status.ACTIVATED;
    }

    public void activate() {
        if (status == Status.DEACTIVATED) {
            animationData.setAnimationParams(MoveDirection.UP);
            status = Status.ACTIVATING;
        }
    }

    public void deactivate() {
        if (status == Status.ACTIVATED) {
            animationData.setAnimationParams(MoveDirection.DOWN);
            status = Status.DEACTIVATING;
        }
    }

    public void setN_vect(Vector3DFloat n_vect) {
        this.n_vect = n_vect;
    }

    public void setTransparency(Vector3DFloat N) {
        isTransparent = N.scalarProd(n_vect) < 0f;
    }

    public Side getSide() {
        return side;
    }

    public boolean isDeactivated() {
        return status == Status.DEACTIVATED;
    }

    public int saveData() {
        if(isActivated() && side != null) {
            return side.saveData();
        }
        else {
            return 0;
        }
    }

    public void uploadData(int tileData) {
        if(tileData != 0) {
            side = side.uploadData(tileData);
        }
    }




    public void moveAnimation() {
        animationData.moveAnimation();
    }


    public class AnimationData extends Animation {
        private final int frameNumber = 12;
        private MoveDirection moveDirection;

        public AnimationData() {

        }

        private void setAnimationParams(MoveDirection moveDirection) {
            if (!isAnimating) {
                frameCounter = 0;
                this.moveDirection = moveDirection;
                isAnimating = true;
            }
        }

        @Override
        public void moveAnimation() {
            if (isAnimating) {
                if (frameCounter < frameNumber) {
                    move(moveDirection);
                    frameCounter++;
                }
                else {
                    switch (moveDirection) {
                        case UP:
                            status = Status.ACTIVATED;
                            break;
                        case DOWN:
                            status = Status.DEACTIVATED;
                            break;
                    }
                    isAnimating = false;
                }

                if (frameCounter == 1) {
//                    sound_moving_up.start();
                    mainManager.soundManager.playWallUp();
                }
            }
        }

        public void move(MoveDirection moveDirection) {
            float shift = GameBoard.TILE_SIDE / frameNumber;
            switch(moveDirection) {
                case UP:
                    shift(0f, shift, 0f, mModelMatrix, I1);
                    break;
                case DOWN:
                    shift(0f, -shift, 0f, mModelMatrix, I1);
                    break;
            }
        }

    }


    public void setVertices(float dX, float dZ, Type type) {
        float ay = GameBoard.TILE_SIDE;
        float ax = GameBoard.TILE_SIDE;
        float az = GameBoard.GAP_WIDTH;

        //// big side
        verticesList.add(new Vector3DFloat(0, 0, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax, 0, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(0,  -ay, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax,  -ay, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        //// big side
        verticesList.add(new Vector3DFloat(0, 0, az).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax, 0, az).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(0,  -ay, az).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax,  -ay, az).sum(new Vector3DFloat(dX, 0, dZ)));
        ////////////////
        //// side
        verticesList.add(new Vector3DFloat(0,  -ay, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(0, 0, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(0,  -ay,   az).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(0, 0,   az).sum(new Vector3DFloat(dX, 0, dZ)));
        //// cap
        verticesList.add(new Vector3DFloat(0, 0, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(ax, 0,   0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(0, 0,   az).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax, 0,   az).sum(new Vector3DFloat(dX, 0, dZ)));
        //// side
        verticesList.add(new Vector3DFloat(ax, 0,   0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax,  -ay, 0).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax, 0,   az).sum(new Vector3DFloat(dX, 0, dZ)));
        verticesList.add(new Vector3DFloat(  ax,  -ay,   az).sum(new Vector3DFloat(dX, 0, dZ)));



        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));

        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
        ////////////////
        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));

        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));

        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
    }

    @Override
    public void setIndexes() {
        indexArr = new short[0];
    }


    protected int getCapTexture(boolean isTransparent) {
        int textureId = 0;
        switch(type) {
            case N:
                if(isTransparent) {
                    textureId = mainManager.texturesId.tr_wall_cap;
                } else {
                    textureId = mainManager.texturesId.gap_cap;
                }
                break;
            case M:
                if(isTransparent) {
                    textureId = mainManager.texturesId.tr_wall_cap;
                } else {
                    textureId = mainManager.texturesId.gap_cap;
                }
                break;
        }
        return textureId;
    }

}
