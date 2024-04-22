package com.example.rollcube.gameinterface.scrollmenu;

import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.RectF;

import com.example.rollcube.GestureController;
import com.example.rollcube.gameinterface.Background;
import com.example.rollcube.gameinterface.GameInterface;
import com.example.rollcube.gameinterface.Picture;
import com.example.rollcube.gameinterface.PictureGroup;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public abstract class ScrollMenu extends PictureGroup {

    boolean isVertical;
    private final Vector3DFloat centerO;
    protected ScrollPicture[] scrollPictureArr;
    protected int focusIdx = 0;
    private final AnimationData animationData = new AnimationData();


    public ScrollMenu(MainManager mainManager, RectF margins, float width, float picturesMargin, boolean isVertical) {
        super(margins);
        findSize(width, margins, isVertical);
        this.isVertical = isVertical;


        if(width < picturesMargin) {
            picturesMargin = width;
        }

        GameInterface.HorizontalRelationType horizontalRelationType;
        GameInterface.VerticalRelativeType verticalRelativeType;
        if (isVertical) {
            verticalRelativeType = GameInterface.VerticalRelativeType.IN_CENTER;
            horizontalRelationType = GameInterface.HorizontalRelationType.LEFT_TO_IN_LEFT;
        } else {
            verticalRelativeType = GameInterface.VerticalRelativeType.TOP_TO_IN_TOP;
            horizontalRelationType = GameInterface.HorizontalRelationType.IN_CENTER;
        }


        createScrollPictures(mainManager, horizontalRelationType, verticalRelativeType, width, picturesMargin);


        groupBackground = new Background(mainManager, this, Picture.Orientation.NORMAL) {
            @Override
            public void animate(float[] animMatrix) {

            }

            @Override
            protected int getTexture() {
                return this.mainManager.texturesId.game_type_scroll_menu_background;
            }
        };
        float k = 2f / (float) Math.sqrt((3 + 2 * Math.sqrt(2)) * (2 - Math.sqrt(2)));
        if(isVertical) {
            groupBackground.getExSize().y *= k;
            groupBackground.getInSize().y *= k;
        } else {
            groupBackground.getExSize().x *= k;
            groupBackground.getInSize().x *= k;
        }

        addRelative(groupBackground,
                GameInterface.HorizontalRelationType.IN_CENTER, this,
                GameInterface.VerticalRelativeType.IN_CENTER, this);

        centerO = new Vector3DFloat(0f, 0f, - (float) (Math.sqrt(3 + 2 * Math.sqrt(2)) / 2) * width);
    }


    protected abstract void createScrollPictures(MainManager mainManager,
                                                 GameInterface.HorizontalRelationType horizontalRelationType,
                                                 GameInterface.VerticalRelativeType verticalRelativeType,
                                                 float width, float picturesMargin
                                        );


    @Override
    public void onSwipe(GestureController.SwipeData swipeData, RectF interfaceInPosOnScreen, Point2DFloat interfaceInSize) {
        if (!animationData.isAnimating) {
            if(isVertical) {
                switch (swipeData.swipeType) {
                    case UP:
                        break;
                    case DOWN:
                        break;
                }
            } else {
                switch (swipeData.swipeType) {
                    case LEFT:
                        scroll(true);
                        break;
                    case RIGHT:
                        scroll(false);
                        break;
                }
            }
        }
    }

    private void findSize(float width, RectF margins, boolean isVertical) {
        if(isVertical) {
            inSize.x = width;
            inSize.y = (float) (1. + Math.sqrt(2)) * width;
        } else {
            inSize.x = (float) (1. + Math.sqrt(2)) * width;
            inSize.y = width;
        }
        exSize.x = inSize.x + margins.left + margins.right;
        exSize.y = inSize.y + margins.bottom + margins.top;
    }

    public void setCenter() {
        centerO.x = inPos.x + inSize.x / 2;
        centerO.y = inPos.y - inSize.y / 2;
    }

    public void setPicturesPositions() {
        if (0 <= focusIdx && focusIdx < scrollPictureArr.length) {
            for (int i = 0; i < scrollPictureArr.length; i++) {
                if(i < focusIdx - 1) {
                    scrollPictureArr[i].fastMove(centerO, isVertical, true, 2);
                    scrollPictureArr[i].hide();
                }
                else if(i == focusIdx - 1) {
                    scrollPictureArr[i].fastMove(centerO, isVertical, true, 1);
                    scrollPictureArr[i].defocus();
                }
                else if(i == focusIdx) {
                    scrollPictureArr[i].focus();
                }
                else if(i == focusIdx + 1) {
                    scrollPictureArr[i].fastMove(centerO, isVertical, false, 1);
                    scrollPictureArr[i].defocus();
                }
                else if(i > focusIdx + 1) {
                    scrollPictureArr[i].fastMove(centerO, isVertical, false, 2);
                    scrollPictureArr[i].hide();
                }
            }
        }
    }

    @Override
    public void draw() {
        animationData.moveAnimation();

        groupBackground.draw();
        for (ScrollPicture scrollPicture: scrollPictureArr) {
            scrollPicture.draw();
        }
    }

    public void scroll(boolean direction) {
        if ((!direction && focusIdx > 0) || (direction && focusIdx < scrollPictureArr.length - 1)) {
            if(direction) {
                if (focusIdx + 2 < scrollPictureArr.length) {
                    scrollPictureArr[focusIdx + 2].defocus();
                }
            } else {
                if (focusIdx - 2 >= 0) {
                    scrollPictureArr[focusIdx - 2].defocus();
                }
            }
            animationData.setAnimationParams(direction);
        }
    }

    protected void onAnimationEnd(boolean direction) {
        if(focusIdx + 1 < scrollPictureArr.length) {
            scrollPictureArr[focusIdx + 1].hide();
        }
        scrollPictureArr[focusIdx].hide();
        if(focusIdx - 1 >= 0) {
            scrollPictureArr[focusIdx - 1].hide();
        }
        if (direction) {
            focusIdx++;
        } else {
            focusIdx--;
        }
        if(focusIdx + 1 < scrollPictureArr.length) {
            scrollPictureArr[focusIdx + 1].defocus();
        }
        scrollPictureArr[focusIdx].focus();
        if(focusIdx - 1 >= 0) {
            scrollPictureArr[focusIdx - 1].defocus();
        }
    }

    public int getFocusIdx() {
        return focusIdx;
    }




    public class AnimationData {
        private boolean isAnimating = false;
        private final int frameNumber = 12;
        private final float dfi = 45f / frameNumber;
        private int frameCounter;
        private boolean direction;   // true: left;   false: right


        public void setAnimationParams(boolean direction) {
            frameCounter = 0;
            this.direction = direction;
            isAnimating = true;
        }


        public void moveAnimation() {
            if(isAnimating) {
                if(direction) {
                    if (focusIdx + 2 < scrollPictureArr.length) {
                        scrollPictureArr[focusIdx + 2].animate(centerO, isVertical, true, dfi);
                    }
                } else {
                    if (focusIdx - 2 >= 0) {
                        scrollPictureArr[focusIdx - 2].animate(centerO, isVertical, false, dfi);
                    }
                }
                if(focusIdx + 1 < scrollPictureArr.length) {
                    scrollPictureArr[focusIdx + 1].animate(centerO, isVertical, direction, dfi);
                }
                scrollPictureArr[focusIdx].animate(centerO, isVertical, direction, dfi);
                if(focusIdx - 1 >= 0) {
                    scrollPictureArr[focusIdx - 1].animate(centerO, isVertical, direction, dfi);
                }
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
