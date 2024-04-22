package com.example.rollcube.gameinterface;

import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.ClickEventData;
import com.example.rollcube.Drawable;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;

import java.util.LinkedList;


public abstract class PullOutMenu extends PictureGroup implements Drawable, Interactive {

    public enum Status {CLOSED, OPENED, CLOSING, OPENING}
    public enum PullingOutType {LEFT, RIGHT, UP, DOWN}
    protected Status status = Status.CLOSED;

    private final boolean isAnimated;
    protected final PullingOutType pullingOutType;
    protected MainButton mainButton;
    protected final LinkedList<PullOutButton> pullOutList = new LinkedList<>();
    private final AnimationData animationData;


    public PullOutMenu(MainManager mainManager, PullingOutType pullingOutType, RectF margins,
                       Point2DFloat mainButtonInSize, RectF mainButtonMargins, boolean isAnimated) {
        super(mainButtonInSize, margins);
        this.isAnimated = isAnimated;
        this.pullingOutType = pullingOutType;
        animationData = new AnimationData();
        addMainButton(mainManager, mainButtonInSize, mainButtonMargins);
    }



    protected abstract void addMainButton(MainManager mainManager, Point2DFloat inSize, RectF margins);

    public Point2DFloat findPullOutButtonSizeParams(float weight) {
        Point2DFloat sizeParams = new Point2DFloat();
        switch (pullingOutType) {
            case LEFT:
            case RIGHT:
                sizeParams.x = this.inSize.x * weight;
                sizeParams.y = this.inSize.y;
                break;
            case UP:
            case DOWN:
                sizeParams.x = this.inSize.x;
                sizeParams.y = this.inSize.y * weight;
                break;
        }
        return sizeParams;
    }

    public Point2DFloat findPullOutButtonPosParams(Point2DFloat sizeParams, RectF margins) {
        Point2DFloat posParams = new Point2DFloat();
        switch (pullingOutType) {
            case LEFT:
                posParams.x = this.inPos.x + margins.left;
                posParams.y = this.inPos.y;
                break;
            case UP:
                posParams.x = this.inPos.x;
                posParams.y = this.inPos.y - margins.top;
                break;
            case RIGHT:
                posParams.x = this.inPos.x + this.inSize.x - sizeParams.x - margins.right;
                posParams.y = this.inPos.y;
                break;
            case DOWN:
                posParams.x = this.inPos.x;
                posParams.y = this.inPos.y - this.inSize.y + sizeParams.y + margins.bottom;
                break;
        }
        return posParams;
    }


    @Override
    public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        ClickEventData clickEventData = mainButton.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);;
        if (clickEventData.clickEvent == ClickEventData.ClickEvent.NO_CLICK && status == Status.OPENED) {
            for (PullOutButton pullOutButton: pullOutList) {
                clickEventData = pullOutButton.checkClick(clickCoords, interfaceInPosOnScreen, interfaceInSize);
                if (clickEventData.clickEvent != ClickEventData.ClickEvent.NO_CLICK) {
                    break;
                }
            }
        }
        return clickEventData;
    }

    @Override
    public RectF getParamsOnScreen(RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        RectF paramsOnScreen = mainButton.getParamsOnScreen(interfaceInPosOnScreen, interfaceInSize);
        if (status == Status.OPENED) {
            RectF pullOutButtonParamsOnScreen;
            for (PullOutButton pullOutButton: pullOutList) {
                pullOutButtonParamsOnScreen = pullOutButton.getParamsOnScreen(interfaceInPosOnScreen, interfaceInSize);
                switch (pullingOutType) {
                    case LEFT:
                        paramsOnScreen.left -= pullOutButtonParamsOnScreen.right - pullOutButtonParamsOnScreen.left;
                        break;
                    case RIGHT:
                        paramsOnScreen.right += pullOutButtonParamsOnScreen.right - pullOutButtonParamsOnScreen.left;
                        break;
                    case UP:
                        paramsOnScreen.top -= pullOutButtonParamsOnScreen.bottom - pullOutButtonParamsOnScreen.top;
                        break;
                    case DOWN:
                        paramsOnScreen.bottom += pullOutButtonParamsOnScreen.bottom - pullOutButtonParamsOnScreen.top;
                        break;
                }
            }
        }
        return paramsOnScreen;
    }


    public void addPullOutButton(PullOutButton pullOutButton,
                                 GameInterface.HorizontalRelationType horizontalRelationType,
                                 GameInterface.VerticalRelativeType verticalRelativeType) {
        pullOutList.add(pullOutButton);
        addRelative(pullOutButton, horizontalRelationType, this, verticalRelativeType, this);
    }


    public void draw() {
        if (status != Status.CLOSED) {
            for (PullOutButton button: pullOutList) {
                button.draw();
            }
        }
        mainButton.draw();
    }


    public void animate() {
        if(animationData.isAnimating) {
            animationData.moveAnimation();
        }
    }

    public void closeMenu() {
        if(status == Status.OPENED) {
            status = Status.CLOSING;
            animationData.setCloseAnimation();
        }
    }

    protected void openMenu() {
        status = Status.OPENING;
        animationData.setOpenAnimation();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
    public boolean isActive() {
        return status != Status.CLOSED;
    }


    protected abstract class MainButton extends Button {
        public MainButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
        }

        @Override
        public ClickEventData onClick(Point2DFloat clickCoords, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
            if (status == Status.CLOSED) {
                openMenu();
            }
            else if (status == Status.OPENED) {
                closeMenu();
            }
            return new ClickEventData(ClickEventData.ClickEvent.NO_EVENT);
        }
    }


    protected abstract class PullOutButton extends Button {

        public PullOutButton(MainManager mainManager, Point2DFloat inSize, RectF margins) {
            super(mainManager, inSize, margins);
        }

        public void moveModelMatrix(float dl) {
            switch (pullingOutType) {
                case LEFT:
                    Matrix.translateM(mModelMatrix, 0, -dl, 0, 0);
                    inPos.x -= dl;
                    exPos.x -= dl;
                    break;
                case RIGHT:
                    Matrix.translateM(mModelMatrix, 0, dl, 0, 0);
                    inPos.x += dl;
                    exPos.x += dl;
                    break;
                case UP:
                    Matrix.translateM(mModelMatrix, 0, 0, dl, 0);
                    inPos.y += dl;
                    exPos.y += dl;
                    break;
                case DOWN:
                    Matrix.translateM(mModelMatrix, 0, 0, -dl, 0);
                    inPos.y -= dl;
                    exPos.y -= dl;
                    break;
            }
        }

        public Status getMenuStatus() {
            return status;
        }

        public abstract int getTextureId();
    }



    public class AnimationData {
        private boolean isAnimating = false;
        private int frameCounter = 0;
        private final int frameNumber;
        private float[] dlArr;

        public AnimationData() {
            if (isAnimated) {
                frameNumber = 15;
            }
            else {
                frameNumber = 1;
            }
        }

        private void setAnimationParams(int sign) {
            if (!isAnimating) {
                frameCounter = 0;
                dlArr = new float[pullOutList.size()];
                float l = 0;

                switch (pullingOutType) {
                    case LEFT:
                    case RIGHT:
                        for (int i = 0; i < pullOutList.size(); i++) {
                            l += pullOutList.get(i).getExSize().x;
                            dlArr[i] = sign * l / frameNumber;
                        }
                        break;
                    case UP:
                    case DOWN:
                        for (int i = 0; i < pullOutList.size(); i++) {
                            l += pullOutList.get(i).getExSize().y;
                            dlArr[i] = sign * l / frameNumber;
                        }
                        break;
                }

                isAnimating = true;
            }
        }

        private void moveAnimation() {
            if (frameCounter < frameNumber) {
                for (int i = 0; i < pullOutList.size(); i++) {
                    pullOutList.get(i).moveModelMatrix(dlArr[i]);
                }
                frameCounter++;
            }
            else {
                if (status == Status.OPENING) {
                    status = Status.OPENED;
                }
                else if (status == Status.CLOSING) {
                    status = Status.CLOSED;
                }
                isAnimating = false;
            }
        }

        public void setCloseAnimation() {
            setAnimationParams(-1);
        }

        public void setOpenAnimation() {
            setAnimationParams(1);
        }
    }




}
