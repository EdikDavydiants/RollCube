package com.example.rollcube.gameinterface.celebration;

import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;
import android.opengl.Matrix;

import com.example.rollcube.gameobjects.GameColorObject;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;

import java.util.Random;


public abstract class FeastObject extends GameColorObject {
    private enum Status { SWITCHED_OFF, CELEBRATING, SWITCHING_OFF }

    private Status status = Status.SWITCHED_OFF;
    protected final Celebration celebration;
    protected final Point2DFloat size;
    protected Point2DFloat pos;
    protected Point2DFloat V;
    protected float Vw;
    protected float angle = 0;
    private AnimationDataSwitchingOff animationDataSwitchingOff;
    protected EdgeGroup[] edgeGroups;
    private final int LEFT = 0;
    private final int RIGHT = 1;
    private final int TOP = 2;
    private final int BOTTOM = 3;


    public FeastObject(MainManager mainManager, Celebration celebration, Point2DFloat size) {
        super(mainManager);
        this.celebration = celebration;
        this.size = size;
        V = new Point2DFloat(0.2f / 60, 0.3f / 60);
        Vw = 1f;
        init();
    }

    public FeastObject(MainManager mainManager, Celebration celebration, Point2DFloat size, Point2DFloat V, float Vw) {
        super(mainManager);
        this.celebration = celebration;
        this.size = size;
        this.V = V;
        this.Vw = Vw;
        init();
    }

    private void init() {
        RectF borders = celebration.getBorders();
        pos = new Point2DFloat((borders.left + borders.right) / 2, (borders.top + borders.bottom) / 2).sum(new Point2DFloat(- size.x / 2, - size.y / 2));
        setVertices();
        animationDataSwitchingOff = new AnimationDataSwitchingOff();
        fastSwitch(-90f);
        initEdgeGroups();
    }

    public void giveImpulse() {
        Random rndm = new Random();
        double fi = 2 * Math.PI * rndm.nextDouble();
        float r = (float) (Math.sqrt(V.x * V.x + V.y * V.y) * 1.2f);
        V.x = (float) (r * Math.cos(fi));
        V.y = (float) (r * Math.sin(fi));
    }


    protected boolean animate() {
        RectF borders = celebration.getBorders();
        boolean isSwitchedOff = false;

        float newX = pos.x + V.x;
        float newY = pos.y + V.y;
        float k = 0.99f;

        if (newX < borders.left) {
            V.x = - k * V.x;
            newX = borders.left;
            processCollide(LEFT);
        } else if (newX + size.x > borders.right) {
            V.x = - k * V.x;
            newX = borders.right - size.x;
            processCollide(RIGHT);
        }
        if (newY + size.y > borders.top) {
            V.y = - k * V.y;
            newY = borders.top - size.y;
            processCollide(TOP);
        }
        else if (newY < borders.bottom) {
            V.y = - k * V.y;
            newY = borders.bottom;
            processCollide(BOTTOM);
        }

       if (animationDataSwitchingOff.isAnimating) {
            isSwitchedOff = !animationDataSwitchingOff.moveAnimation(newX, newY);
       }
       else {
           moveSpin(newX, newY);
       }

        pos.x = newX;
        pos.y = newY;

        return isSwitchedOff;
    }

    void processCollide(int side) {
        float pi = (float) Math.PI;
        float L = pi * pi * size.x / 180;
        float w = Vw * L;
        float k = 0.3f;

        if(side == LEFT) {
            float av = (V.y + w) / 2;
            float dVy = av - V.y;
            float dw = av - w;

            V.y += k * dVy;
            w += k * dw;
        } else if(side == RIGHT) {
            float w_ = -w;
            float av = (V.y + w_) / 2;
            float dVy = av - V.y;
            float dw_ = av - w_;

            V.y += k * dVy;
            w_ += k * dw_;
            w = -w_;
        } else if(side == TOP) {
            float av = (V.x + w) / 2;
            float dVx = av - V.x;
            float dw = av - w;

            V.x += k * dVx;
            w += k * dw;
        } else if(side == BOTTOM) {
            float w_ = -w;
            float av = (V.x + w_) / 2;
            float dVx = av - V.x;
            float dw_ = av - w_;

            V.x += k * dVx;
            w_ += k * dw_;
            w = -w_;
        }

        Vw = w / L;
    }


    public void switchOff() {
        animationDataSwitchingOff.setAnimationParams();
    }


    public void moveSpin(float newX, float newY) {
        Matrix.setIdentityM(I1, 0);
        Matrix.setIdentityM(I2, 0);

        float shiftX = pos.x + size.x / 2;
        float shiftY = pos.y + size.y / 2;

        Matrix.translateM(I1, 0, -shiftX, -shiftY, 0);
        Matrix.setRotateM(mMatrix, 0, Vw, 0, 0, 1);
        Matrix.translateM(I2, 0, shiftX + newX - pos.x, shiftY + newY - pos.y, 0);

        Matrix.multiplyMM(mModelMatrix, 0, I1, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mModelMatrix, 0, mMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mModelMatrix, 0, I2, 0, mModelMatrix, 0);

        angle += Vw;
        if (angle > 360f) {angle -= 360f;}
        if (angle < 0) {angle += 360f;}
        Vw *= 0.999f;
    }


    protected float light_coeff(float range, float angle) {
        if (angle >= -540 && angle < -360) {
            return 0.01111111f * range * angle + 5 * range + 1f;
        }
        else if (angle >= -360 && angle < -180) {
            return - 0.01111111f * range * angle - 3 * range + 1f;
        }
        else if (angle >= -180 && angle < 0) {
            return 0.01111111f * range * angle + range + 1f;
        }
        else if (angle >= 0 && angle < 180) {
            return - 0.01111111f * range * angle + range + 1f;
        }
        else if (angle >= 180 && angle < 360) {
            return 0.01111111f * range * angle - 3 * range + 1f;
        }
        else if (angle >= 360 && angle < 540) {
            return - 0.01111111f * range * angle + 5 * range + 1f;
        }
        else if (angle >= 540 && angle < 720) {
            return 0.01111111f * range * angle - 7 * range + 1f;
        }
        else {
            return 0;
        }
    }

    private void fastSwitch(float angle) {
        animationDataSwitchingOff.moveSwitchingOff(pos.x, pos.y, angle);
    }

    public void onStart() {
        fastSwitch(90f);
        status = Status.CELEBRATING;
    }



    public class AnimationDataSwitchingOff {
        private boolean isAnimating = false;
        private int frameCounter = 0;
        private final int totalFrameCount = 90;
        private final float angle = -1f;

        public AnimationDataSwitchingOff() {

        }


        private void setAnimationParams() {
            if (!isAnimating) {
                status = Status.SWITCHING_OFF;
                frameCounter = 0;
                isAnimating = true;
            }
        }


        private boolean moveAnimation(float newX, float newY) {
            if (frameCounter < totalFrameCount) {
                moveSwitchingOff(newX, newY, angle);
                frameCounter++;
            } else {
                status = Status.SWITCHED_OFF;
                isAnimating = false;
            }
            return isAnimating;
        }


        public void moveSwitchingOff(float newX, float newY, float angle) {
            Matrix.setIdentityM(I1, 0);
            Matrix.setIdentityM(I2, 0);

            float shiftX = pos.x + size.x / 2;
            float shiftY = pos.y + size.y / 2;

            Matrix.translateM(I1, 0, -shiftX, -shiftY, 0);
            Matrix.multiplyMM(mModelMatrix, 0, I1, 0, mModelMatrix, 0);

            Matrix.setRotateM(mMatrix, 0, angle, 0, 1, 0);
            Matrix.multiplyMM(mModelMatrix, 0, mMatrix, 0, mModelMatrix, 0);

            Matrix.translateM(I2, 0, shiftX + newX - pos.x, shiftY + newY - pos.y, 0);
            Matrix.multiplyMM(mModelMatrix, 0, I2, 0, mModelMatrix, 0);
        }

    }


    protected abstract void initEdgeGroups();

    public abstract class EdgeGroup {
        protected boolean isGlared = false;
        protected float color_r;
        protected float color_g;
        protected float color_b;
        protected float colorRange;
        protected float angle0;
        public EdgeGroup() {

        }
        public EdgeGroup(float colorRange, float angle0) {
            this.colorRange = colorRange;
            this.angle0 = angle0;
        }

        protected abstract void moveColor();
        protected abstract void draw();
    }






}
