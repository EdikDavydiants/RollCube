package com.example.rollcube.gameinterface;

import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.GestureController;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;


public abstract class FlippingList extends PictureGroup implements Interactive {
    public enum Status {OPENING, CLOSING, WAITING}

    protected Status status = Status.WAITING;
    protected final boolean isVertical;
    protected final int depth;
    protected final int length;
    protected final FlippingPictureGroup[] flippingPictureGroupArr;
    protected final int[][] texturesArr;
    protected int activeDepth = 0;
    protected final AnimationData animationData = new AnimationData();
    

    public FlippingList(MainManager mainManager, RectF margins, RectF flipMargins,
                        int depth, int length, boolean isVertical, float flipHeight, float pictureMargin) {
        super(margins);
        this.isVertical = isVertical;
        this.depth = depth;
        this.length = length;
        flippingPictureGroupArr = new FlippingPictureGroup[length];
        texturesArr = new int[depth][length];
        createFlips(mainManager, flipMargins, flipHeight, pictureMargin);
    }



    public abstract int getTexture(MainManager mainManager, int depth, int length);

    public void prepare() {
        setGroupsMatrices();
    }

    private void setGroupsMatrices() {
        for (FlippingPictureGroup flippingPictureGroup : flippingPictureGroupArr) {
            flippingPictureGroup.setMatrices();
        }
    }

    protected abstract void createFlips(MainManager mainManager, RectF margins, float height, float margin);

    @Override
    public void draw() {
        animationData.moveAnimation();
        for (FlippingPictureGroup flippingPictureGroup: flippingPictureGroupArr) {
            flippingPictureGroup.draw();
        }
    }



    @Override
    public void onSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if(isVertical) {
            switch(swipeData.swipeType) {
                case UP:
                    flip(false);
                    break;
                case DOWN:
                    flip(true);
                    break;
            }
        } else {
            switch(swipeData.swipeType) {
                case LEFT:
                    flip(false);
                    break;
                case RIGHT:
                    flip(true);
                    break;
            }
        }
    }


    public void flip(boolean direction) {
        boolean isNotAnimating = !animationData.isAnimating;
        boolean isWaiting = status == Status.WAITING;
        boolean isFirstDepth = activeDepth == 0;
        boolean isLastDepth = activeDepth == depth - 1;
        if(isNotAnimating && isWaiting && !(direction && isFirstDepth) && !(!direction && isLastDepth)) {
            animationData.setAnimationParams(direction);
            status = Status.CLOSING;
        }
    }

    protected void moveAnimation_x_dfi() {
        for (FlippingPictureGroup flippingPictureGroup: flippingPictureGroupArr) {
            flippingPictureGroup.animateGroup(flippingPictureGroup.rotateMatrix_x_dfi);
        }
    }
    protected void moveAnimation_x2_dfi() {
        for (FlippingPictureGroup flippingPictureGroup: flippingPictureGroupArr) {
            flippingPictureGroup.animateGroup(flippingPictureGroup.rotateMatrix_x2_dfi);
        }
    }
    protected void moveAnimation_x_90() {
        for (FlippingPictureGroup flippingPictureGroup: flippingPictureGroupArr) {
            flippingPictureGroup.animateGroup(flippingPictureGroup.rotateMatrix_x_fi90);
        }
    }
    protected void moveAnimation_x2_90() {
        for (FlippingPictureGroup flippingPictureGroup: flippingPictureGroupArr) {
            flippingPictureGroup.animateGroup(flippingPictureGroup.rotateMatrix_x2_fi90);
        }
    }
    protected void moveAnimation_x_180() {
        for (FlippingPictureGroup flippingPictureGroup: flippingPictureGroupArr) {
            flippingPictureGroup.animateGroup(flippingPictureGroup.rotateMatrix_x_fi180);
        }
    }
    protected void moveAnimation_x2_180() {
        for (FlippingPictureGroup flippingPictureGroup: flippingPictureGroupArr) {
            flippingPictureGroup.animateGroup(flippingPictureGroup.rotateMatrix_x2_fi180);
        }
    }

    public void animateLeafPictures(boolean direction) {
        if(direction) {
            moveAnimation_x_dfi();
        } else {
            moveAnimation_x2_dfi();
        }
    }

    protected void onAnimationEnd(boolean direction) {
        if(direction) {
            if(activeDepth > 0 || status == Status.OPENING){
                switch (status){
                    case OPENING:
                        status = Status.WAITING;
                        break;
                    case CLOSING:
                        moveAnimation_x2_180();
                        activeDepth--;
                        animationData.setAnimationParams(true);
                        status = Status.OPENING;
                        break;
                }
            }
        } else {
            if(activeDepth < depth - 1 || status == Status.OPENING) {
                switch(status) {
                    case OPENING:
                        status = Status.WAITING;
                        break;
                    case CLOSING:
                        activeDepth++;
                        moveAnimation_x_180();
                        animationData.setAnimationParams(false);
                        status = Status.OPENING;
                        break;
                }
            }
        }
    }




    public abstract class FlippingPictureGroup extends PictureGroup {
        protected float centerX;
        protected float centerY;
        private float[] rotateMatrix_x_fi180;
        private float[] rotateMatrix_x2_fi180;
        private float[] rotateMatrix_x_fi90;
        private float[] rotateMatrix_x2_fi90;
        private float[] rotateMatrix_x_dfi;
        private float[] rotateMatrix_x2_dfi;


        public FlippingPictureGroup(RectF margins) {
            super(margins);
        }



        public abstract void animateGroup(float[] animMatrix);

        private void setMatrices() {
            updateCenter();
            rotateMatrix_x_fi180 = rotateMatrix(180);
            rotateMatrix_x2_fi180 = rotateMatrix(-180);
            rotateMatrix_x_fi90 = rotateMatrix(90);
            rotateMatrix_x2_fi90 = rotateMatrix(-90);
            rotateMatrix_x_dfi = rotateMatrix(animationData.dfi);
            rotateMatrix_x2_dfi = rotateMatrix(-animationData.dfi);
        }
        private float[] rotateMatrix(float dfi) {
            float[] I1 = new float[16];
            float[] I2 = new float[16];
            float[] M = new float[16];
            Matrix.setIdentityM(I1, 0);
            Matrix.setIdentityM(I2, 0);

            Matrix.translateM(I1, 0, -centerX, -centerY, 0f);
            if(isVertical) {
                if(animationData.direction) {
                    Matrix.setRotateM(M, 0, dfi, 1, 0, 0);
                } else {
                    Matrix.setRotateM(M, 0, -dfi, 1, 0, 0);
                }
            } else {
                if(animationData.direction) {
                    Matrix.setRotateM(M, 0, dfi, 0, 1, 0);
                } else {
                    Matrix.setRotateM(M, 0, -dfi, 0, 1, 0);
                }
            }
            Matrix.translateM(I2, 0, centerX, centerY, 0f); // I2 M I1;

            Matrix.multiplyMM(M, 0, M, 0, I1, 0);
            Matrix.multiplyMM(M, 0, I2, 0, M, 0);

            return M;
        }

        @Override
        public void updateCoords(float dx, float dy, boolean isRoot) {
            super.updateCoords(dx, dy, isRoot);
            updateCenter();
        }
        private void updateCenter() {
            centerX = (getInLeft() + getInRight()) / 2;
            centerY = (getInTop() + getInBottom()) / 2;
        }



    }




    public class AnimationData {
        private boolean isAnimating = false;
        private final int frameNumber = 9;
        private final float dfi = 90f / frameNumber;
        private int frameCounter;
        private boolean direction;   // true: ;   false:


        public void setAnimationParams(boolean direction) {
            frameCounter = 0;
            this.direction = direction;
            isAnimating = true;
        }


        public void moveAnimation() {
            if(isAnimating) {
                animateLeafPictures(direction);
                frameCounter++;
                if(frameCounter >= frameNumber) {
                    frameCounter = 0;
                    isAnimating = false;
                    onAnimationEnd(direction);
                }
            }
        }


    }



}
