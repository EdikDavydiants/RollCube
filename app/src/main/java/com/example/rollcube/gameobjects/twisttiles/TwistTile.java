package com.example.rollcube.gameobjects.twisttiles;

import com.example.rollcube.Animation;
import com.example.rollcube.gameobjects.cells.CellObject;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;
import com.example.rollcube.GameBoard;
import com.example.rollcube.gameobjects.sides.Side;

import java.util.Random;


public abstract class TwistTile extends CellObject {
    public enum Status {DEACTIVATED, ACTIVATING, ACTIVATED, DEACTIVATING}

    public static final float THICKNESS = 0.1f * GameBoard.TILE_SIDE;
    private final AnimationData animationData;
    protected Side side;
    private Status status = Status.DEACTIVATED;


    public TwistTile(MainManager mainManager, int n, int m, GameBoard.GameType gameType) {
        super(mainManager, n, m, gameType);
        animationData = new AnimationData();
    }



    public abstract void setRandomSide(Random rndm);

    @Override
    public void setVertices(float dx, float dz) {
        verticesList.add(new Vector3DFloat(0,0,0).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,1).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(1,0,0).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(1,0,1).prod(GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));

        verticesList.add(new Vector3DFloat(0,-THICKNESS,0).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,0).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));


        verticesList.add(new Vector3DFloat(0,0,0).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,0).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,0,GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,GameBoard.TILE_SIDE).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,0,0).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(GameBoard.TILE_SIDE,-THICKNESS,0).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,0,0).sum(new Vector3DFloat(dx, 0, dz)));
        verticesList.add(new Vector3DFloat(0,-THICKNESS,0).sum(new Vector3DFloat(dx, 0, dz)));



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
        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
        texturePointsList.add(new Point2DFloat(1, 0));
        texturePointsList.add(new Point2DFloat(1, 1));
        texturePointsList.add(new Point2DFloat(0, 0));
        texturePointsList.add(new Point2DFloat(0, 1));
    }

    @Override
    public void setIndexes() {
        indexArr = new short[0];
    }

    public boolean isActivated() {
        return status == Status.ACTIVATED;
    }

    public void activate() {
        if (status == Status.DEACTIVATED) {
            status = Status.ACTIVATING;
            animationData.setAnimationParams();
        }
    }

    public void deactivateQuickly() {
        status = Status.DEACTIVATED;
        animationData.switchFast();
        mainManager.soundManager.playTileTwist();
    }

    public void deactivate() {
        if (status == Status.ACTIVATED) {
            status = Status.DEACTIVATING;
            animationData.setAnimationParams();
        }
    }


    public void moveAnimation() {
        animationData.moveAnimation();
    }

    public Side getSide() {
        return side;
    }

    public boolean isDeactivated() {
        return status == Status.DEACTIVATED;
    }

    public void uploadData(int tileData) {
        if(tileData != 0) {
            side = side.uploadData(tileData);
        }
    }

    public int saveData() {
        if(isActivated() && side != null) {
            return side.saveData();
        }
        else {
            return 0;
        }
    }




    public class AnimationData extends Animation {

        public AnimationData() {

        }

        private void setAnimationParams() {
            if (!isAnimating) {
                frameCounter = 0;
                isAnimating = true;
            }
        }

        @Override
        public void moveAnimation() {
            if (isAnimating) {
                if (frameCounter == 0) {
//                    animationData.sound_rotating.setVolume(1f, 1f);
//                    sound_rotating.start();
                    mainManager.soundManager.playTileTwist();
                }

                if (frameCounter < 18) {
                    if (frameCounter < 3) {
                        moveDown();
                    }
                    else if (frameCounter < 15) {
                        moveSpin(15f);
                    }
                    else {
                        moveUp();
                    }
                    frameCounter++;
                }
                else {
                    switch (status) {
                        case ACTIVATING:
                            status = Status.ACTIVATED;
                            break;
                        case DEACTIVATING:
                            status = Status.DEACTIVATED;
                            break;
                    }
                    isAnimating = false;
                }
            }
        }

        public void switchFast() {
            float shiftX = cellCoords.n * GameBoard.TILE_SIDE + GameBoard.TILE_SIDE / 2 + (cellCoords.n + 1) * GameBoard.GAP_WIDTH;
            float shiftY = -0.5f * THICKNESS;
            animationData.rotate(shiftX, shiftY, 0f,
                    0f, 0f, 1f,
                    180f,
                    mModelMatrix,
                    I1, mMatrix, I2);
        }

        public void moveSpin(float angle) {
            float shiftX = cellCoords.n * GameBoard.TILE_SIDE + GameBoard.TILE_SIDE / 2 + (cellCoords.n + 1) * GameBoard.GAP_WIDTH;
            float shiftY = -1.5f * THICKNESS;
            rotate(shiftX, shiftY, 0f,
                    0f, 0f, 1f,
                    angle,
                    mModelMatrix,
                    I1, mMatrix, I2);

        }

        public void moveDown() {
            float shift = THICKNESS / 3;
            shift(0f, -shift, 0f, mModelMatrix , I1);
        }

        public void moveUp() {
            float shift = THICKNESS / 3;
            shift(0f, shift, 0f, mModelMatrix , I1);
        }
    }





}
