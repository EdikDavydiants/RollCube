package com.example.rollcube;

import android.view.MotionEvent;

import java.util.LinkedList;


public class GestureController {

    public enum GestureType { CLICK, DOUBLE_CLICK, SWIPE, LONG_PRESS, ZOOM }
    public enum SwipeType { LEFT, RIGHT, UP, DOWN }
    public enum ZoomType { INCREASE, DECREASE }

    // All distances in pixels.
    public static final double CLICK_RADIUS = 30.0;
    public static final double DOUBLE_CLICK_RADIUS = 100.0;
    public static final double LONG_PRESS_RADIUS = 30.0;
    /**
     * How straight are your hands.
    */
    public static final double SWIPE_STRAIGHT_COEFFICIENT = 1.3;
    public static final double SWIPE_MIN_LENGTH = 100.0;
    public static final long SWIPE_TIME = 700;  // 0.7s
    public static final long LONG_PRESS_TIME = 1500;  // 1.5s
    public static final long LONG_PRESS_OK_BOARD_ACTIVATING = 10000;  // 10s
    /** Time between two clicks. */
    public static final long DOUBLE_CLICK_TIME = 220;
    /** Time between finger down and finger up. */
    public static final long CLICK_TIME = 100;
    public static final double ZOOM_SCALE_COEFFICIENT = 0.5;
    public static final float ZOOM_MAX_SIZE = 400;

    private final LinkedList<Pointer> pointers;
    private final LinkedList<Click> clicks;


    public GestureController() {
        pointers = new LinkedList<>();
        clicks = new LinkedList<>();
    }


    public GestureData eventProcess(MotionEvent event) {
        GestureData gestureData = null;

        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        MotionEvent.PointerCoords pCoords = new MotionEvent.PointerCoords();
        event.getPointerCoords(index, pCoords);
        float x = pCoords.x;
        float y = pCoords.y;
        long time = System.currentTimeMillis();

        int action = event.getActionMasked();

        if(event.getPointerCount() > 2) {
            pointers.clear();
            clicks.clear();
        }
        else {
            boolean emptyList = pointers.size() == 0;
            boolean firstTap = action == MotionEvent.ACTION_DOWN;
            if( !( (emptyList && !firstTap) || (!emptyList && firstTap) ) ){
                switch(action) {
                    case MotionEvent.ACTION_DOWN:
                        pointers.add(new Pointer(id, x, y, time));
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        if(pointers.size() == 1) {
                            pointers.add(new Pointer(id, x, y, time));
                        }
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        MotionEvent.PointerCoords[] psCoords = new MotionEvent.PointerCoords[2];
                        psCoords[0] = new MotionEvent.PointerCoords();
                        psCoords[1] = new MotionEvent.PointerCoords();
                        for (int i = 0; i < 2; i++) {
                            id = event.getPointerId(i);
                            event.getPointerCoords(i, psCoords[i]);
                            for (Pointer pointer: pointers) {
                                if (pointer.id == id) {
                                    pointer.saveUpData(psCoords[i].x, psCoords[i].y, time);
                                }
                            }
                        }
                        if(checkZoom()) {
                            gestureData = new ZoomData();
                        }
                        pointers.clear();
                        break;

                    case MotionEvent.ACTION_UP:
                        pointers.get(0).saveUpData(x, y, time);
                        if (!checkLongPress()) {
                            if (!checkSwipe()) {
                                if (checkClick()) {
                                    clicks.add(new Click(x, y, time));
                                    gestureData = new ClickData();
                                    clicks.clear();
//                                    if (clicks.size() == 0) {
//                                        clicks.add(new Click(x, y, time));
//                                    }
//                                    else if (clicks.size() == 1) {
//                                        clicks.add(new Click(x, y, time));
//                                        if(!checkDoubleClick()) {
//                                            Click click = clicks.remove(1);
//                                            clicks.clear();
//                                            clicks.add(click);
//                                        }
//                                        else {
//                                            gestureData = new DoubleClickData();
//                                            clicks.clear();
//                                        }
//                                    }
                                }
                            }
                            else {
                                gestureData = new SwipeData();
                            }
                        }
                        else {
                            gestureData = new LongPressData();
                        }
                        pointers.clear();
                        break;
                }
            }
        }
        return gestureData;
    }

    private boolean checkSwipe() {
        Pointer pointer = pointers.get(0);
        float dx = pointer.upTapData.x - pointer.downTapData.x;
        float dy = pointer.upTapData.y - pointer.downTapData.y;
        long dt = pointer.upTapData.time - pointer.downTapData.time;

        boolean fastSwipe = dt < SWIPE_TIME;
        boolean longSwipe = Math.sqrt(dx*dx + dy*dy) > SWIPE_MIN_LENGTH;
        boolean strengthSwipe = true;  //Math.abs(dx) > SWIPE_STRENGTH_COEFFICIENT * Math.abs(dy) || Math.abs(dy) > SWIPE_STRENGTH_COEFFICIENT * Math.abs(dx);

        return fastSwipe && longSwipe && strengthSwipe;
    }

    private boolean checkLongPress() {
        Pointer pointer = pointers.get(0);
        float dx = pointer.upTapData.x - pointer.downTapData.x;
        float dy = pointer.upTapData.y - pointer.downTapData.y;
        long dt = pointer.upTapData.time - pointer.downTapData.time;

        return (dt > LONG_PRESS_TIME) && (Math.sqrt(dx*dx + dy*dy) < LONG_PRESS_RADIUS);
    }

    private boolean checkDoubleClick() {
        Click click1 = clicks.get(0);
        Click click2 = clicks.get(1);
        float dx = click1.x - click2.x;
        float dy = click1.y - click2.y;
        long dt = click2.time - click1.time;

        return (dt < DOUBLE_CLICK_TIME) && (Math.sqrt(dx*dx + dy*dy) < DOUBLE_CLICK_RADIUS);
    }

    private boolean checkZoom() {
        Pointer pointer1 = pointers.get(0);
        Pointer pointer2 = pointers.get(1);
        float dx_start = pointer1.downTapData.x - pointer2.downTapData.x;
        float dx_end = pointer1.upTapData.x - pointer2.upTapData.x;
        float dy_start = pointer1.downTapData.y - pointer2.downTapData.y;
        float dy_end = pointer1.upTapData.y - pointer2.upTapData.y;

        float dr_start = (float) Math.sqrt(dx_start * dx_start + dy_start * dy_start);
        float dr_end = (float) Math.sqrt(dx_end * dx_end + dy_end * dy_end);

        boolean isSquareBig = dr_end > ZOOM_MAX_SIZE || dr_start > ZOOM_MAX_SIZE;
        boolean isGestureBig = ZOOM_SCALE_COEFFICIENT * dr_end > dr_start || ZOOM_SCALE_COEFFICIENT * dr_start > dr_end;

        return isSquareBig && isGestureBig;
    }

    private boolean checkClick() {
        Pointer pointer = pointers.get(0);
        float dx = pointer.upTapData.x - pointer.downTapData.x;
        float dy = pointer.upTapData.y - pointer.downTapData.y;
        long dt = pointer.upTapData.time - pointer.downTapData.time;

        return (dt < CLICK_TIME) && (Math.sqrt(dx*dx + dy*dy) < CLICK_RADIUS);
    }




    private static class Pointer {
        private final int id;
        private final EventData downTapData;
        private EventData upTapData = null;


        private Pointer(int id, float x, float y, long time) {
            this.id = id;
            downTapData = new EventData(x, y, time);
        }

        private void saveUpData(float x, float y, long time) {
            upTapData = new EventData(x, y, time);
        }

        private static class EventData {
            private final long time;
            private final float x;
            private final float y;
            private EventData(float x, float y, long time) {
                this.x = x;
                this.y = y;
                this.time = time;
            }
        }
    }


    private static class Click {
        private final long time;
        private final float x;
        private final float y;
        private Click(float x, float y, long time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }

    public abstract static class GestureData {
        public final GestureType gestureType;
        protected GestureData(GestureType gestureType) {
            this.gestureType = gestureType;
        }

    }

    public class ClickData extends GestureData {
        public final float x;
        public final float y;
        protected ClickData() {
            super(GestureType.CLICK);
            this.x = clicks.get(0).x;
            this.y = clicks.get(0).y;
        }
    }

    public class DoubleClickData extends GestureData {
        public final float x;
        public final float y;
        protected DoubleClickData() {
            super(GestureType.DOUBLE_CLICK);
            this.x = (clicks.get(0).x + clicks.get(1).x) / 2;
            this.y = (clicks.get(0).y + clicks.get(1).y) / 2;
        }
    }

    public class SwipeData extends GestureData {
        public final SwipeType swipeType;
        public final float x1;
        public final float y1;
        public final float x2;
        public final float y2;
        public final float dx;
        public final float dy;
        protected SwipeData() {
            super(GestureType.SWIPE);
            x1 = pointers.get(0).downTapData.x;
            x2 = pointers.get(0).upTapData.x;
            y1 = pointers.get(0).downTapData.y;
            y2 = pointers.get(0).upTapData.y;
            dx = x2 - x1;
            dy = y2 - y1;
            boolean horizontal = Math.abs(dx) > Math.abs(dy);
            boolean vertical = Math.abs(dy) > Math.abs(dx);
            boolean left = dx <= 0 && horizontal;
            boolean right = dx > 0 && horizontal;
            boolean up = dy <= 0 && vertical;
            //boolean down = dy > 0 && vertical;
            if (left) {
                swipeType = SwipeType.LEFT;
            }
            else if (right) {
                swipeType = SwipeType.RIGHT;
            }
            else if (up) {
                swipeType = SwipeType.UP;
            }
            else {
                swipeType = SwipeType.DOWN;
            }
        }
    }

    public class ZoomData extends GestureData {
        private final ZoomType zoomType;
        protected ZoomData() {
            super(GestureType.ZOOM);
            float dx_down = pointers.get(0).downTapData.x - pointers.get(1).downTapData.x;
            float dy_down = pointers.get(0).downTapData.y - pointers.get(1).downTapData.y;
            float length_down = (float) Math.sqrt(dx_down * dx_down + dy_down * dy_down);
            float dx_up = pointers.get(0).upTapData.x - pointers.get(1).upTapData.x;
            float dy_up = pointers.get(0).upTapData.y - pointers.get(1).upTapData.y;
            float length_up = (float) Math.sqrt(dx_up * dx_up + dy_up * dy_up);
            if(length_up > length_down) {
                zoomType = ZoomType.INCREASE;
            }
            else {
                zoomType = ZoomType.DECREASE;
            }
        }
        public ZoomType getZoomType() {
            return zoomType;
        }
    }

    public class LongPressData extends GestureData {
        public final float x;
        public final float y;
        public final long dt;
        protected LongPressData() {
            super(GestureType.LONG_PRESS);
            x = (pointers.get(0).downTapData.x + pointers.get(0).upTapData.x) / 2;
            y = (pointers.get(0).downTapData.y + pointers.get(0).upTapData.y) / 2;
            dt = pointers.get(0).upTapData.time - pointers.get(0).downTapData.time;
        }
    }



}
