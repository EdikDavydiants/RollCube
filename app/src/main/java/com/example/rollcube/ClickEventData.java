package com.example.rollcube;


/**
 * Class provides interface click info.
 * Info is processed in method onTouchEvent() in class GameView.
 * Often it's unnecessary to process click in onTouchEvent() and info is processed in button method directly.
 * In that case should return NO_EVENT.
 * Also should return NO_EVENT if button has registered click, but no action should be come.
 * NO_CLICK allows to continue searching click in the layout tree.
 */
public class ClickEventData {
    public enum ClickEvent {
        NO_CLICK,
        NO_EVENT,
        FIRST_RUN,
        NEW_GAME,
        PAUSE, RESUME,
        RESTART,
        OPEN_MENU,
        CLOSE_MENU,
        EXIT_GAME,
    }

    public ClickEvent clickEvent;
    public GameBoard.GameInfo gameInfo;


    public ClickEventData(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

}
