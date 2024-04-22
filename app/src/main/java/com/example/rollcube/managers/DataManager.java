package com.example.rollcube.managers;

import android.content.Context;
import android.content.SharedPreferences;


public class DataManager {
    public static final String PREF_DATA = "pref_data";

    public static final String IS_FIRST_LAUNCH_KEY = "is_first_launch";
    public static final String MAIN_MENU_SIZE_KEY = "mm_size";
    public static final String BUTTON_TO_MAIN_MENU_SIZE_KEY = "btn_to_mm_size";
    public static final String TIMER_AND_POINTER_SIZE_KEY = "timer_pointer_size";
    public static final String START_BUTTON_SIZE_KEY = "start_btn_size";

    public static final String PLAYER_REGIME_KEY = "player_regime";
    public static final String SOUND_POOL = "sound_pool";
    public static final String MEDIA_PLAYER = "media_player";

    private static final String CAMERA_ZOOM_KEY = "camera_zoom";


    public void savePref(Context context, SaveData saveData) {
        SharedPreferences sPref = context.getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putBoolean(IS_FIRST_LAUNCH_KEY, saveData.isFirstLaunch);

        ed.putFloat(MAIN_MENU_SIZE_KEY, saveData.mainMenuSize);
        ed.putFloat(BUTTON_TO_MAIN_MENU_SIZE_KEY, saveData.buttonToMainMenuSize);
        ed.putFloat(TIMER_AND_POINTER_SIZE_KEY, saveData.timerAndPointerSize);
        ed.putFloat(START_BUTTON_SIZE_KEY, saveData.startButtonSize);

        ed.putString(PLAYER_REGIME_KEY, SOUND_POOL);

        ed.putInt(CAMERA_ZOOM_KEY, saveData.cameraZoom);

        ed.apply();
    }

    public void extractPref(Context context, SaveData saveData) {
        SharedPreferences sPref = context.getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE);

        saveData.mainMenuSize = sPref.getFloat(MAIN_MENU_SIZE_KEY, 1f);
        saveData.buttonToMainMenuSize = sPref.getFloat(BUTTON_TO_MAIN_MENU_SIZE_KEY, 1f);
        saveData.timerAndPointerSize = sPref.getFloat(TIMER_AND_POINTER_SIZE_KEY, 1f);
        saveData.startButtonSize = sPref.getFloat(START_BUTTON_SIZE_KEY, 1f);

        saveData.playerRegime = sPref.getString(PLAYER_REGIME_KEY, SOUND_POOL);

        saveData.cameraZoom = sPref.getInt(CAMERA_ZOOM_KEY, 0);
    }


    public boolean checkIsFirstLaunch(Context context) {
        SharedPreferences sPref = context.getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE);
        return sPref.getBoolean(IS_FIRST_LAUNCH_KEY, true);
    }

    public void setDefaultInterfaceSizeValues(Context context) {
        SharedPreferences sPref = context.getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putBoolean(IS_FIRST_LAUNCH_KEY, false);

        ed.putFloat(MAIN_MENU_SIZE_KEY, 1.0f);
        ed.putFloat(BUTTON_TO_MAIN_MENU_SIZE_KEY, 1.0f);
        ed.putFloat(TIMER_AND_POINTER_SIZE_KEY, 1.0f);
        ed.putFloat(START_BUTTON_SIZE_KEY, 1.0f);

        ed.apply();
    }

    public void setDefaultParams(Context context) {
        SharedPreferences sPref = context.getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putBoolean(IS_FIRST_LAUNCH_KEY, false);

        ed.putFloat(MAIN_MENU_SIZE_KEY, 1.0f);
        ed.putFloat(BUTTON_TO_MAIN_MENU_SIZE_KEY, 1.0f);
        ed.putFloat(TIMER_AND_POINTER_SIZE_KEY, 1.0f);
        ed.putFloat(START_BUTTON_SIZE_KEY, 1.0f);

        ed.putString(PLAYER_REGIME_KEY, SOUND_POOL);

        ed.putInt(CAMERA_ZOOM_KEY, 0);

        ed.apply();
    }

    public void setPlayerRegime(Context context, String playerRegime) {
        SharedPreferences sPref = context.getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PLAYER_REGIME_KEY, playerRegime);
        ed.apply();
    }

    public void setCameraZoom(Context context, int zoomValue) {
        SharedPreferences sPref = context.getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(PLAYER_REGIME_KEY, zoomValue);
        ed.apply();
    }

    public static class SaveData {
        public boolean isFirstLaunch = false;

        public float mainMenuSize;
        public float buttonToMainMenuSize;
        public float timerAndPointerSize;
        public float startButtonSize;

        public String playerRegime;

        public int cameraZoom;


        public SaveData(Context context) {
            new DataManager().extractPref(context, this);
        }



    }



}
