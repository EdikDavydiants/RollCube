package com.example.rollcube.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rollcube.ClassMediator;
import com.example.rollcube.managers.DataManager;
import com.example.rollcube.GameView;


public class GameActivity extends AppCompatActivity {

    private ClassMediator classMediator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        if (!supportES2()) {
            Toast.makeText(this, "OpenGl ES 2.0 is not supported", Toast.LENGTH_LONG).show();
            finish();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        classMediator = new ClassMediator();
        classMediator.screenParams = new ScreenParams(this);
        classMediator.gameActivity = this;

        DataManager dataManager = new DataManager();
        if(dataManager.checkIsFirstLaunch(this)) {
            dataManager.setDefaultParams(this);
        }

        createGameView();
    }


    @Override
    protected void onPause() {
        classMediator.openGLRend.pauseRend();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(classMediator.openGLRend != null) {
            classMediator.openGLRend.resumeRend();
        }
    }

    @Override
    protected void onDestroy() {
//        DataManager dataManager = new DataManager();
//        if (gameStatus == GameStatus.RUNNING || gameStatus == GameStatus.PAUSED) {
//            SaveData saveData = gameView.openGLRend.scene.gameBoard.saveDataClass();
//            dataManager.saveGame(this, saveData);
//        }
//        else {
//            dataManager.putSaveDataStatusDisable(this);
//        }
        super.onDestroy();
    }


    private boolean supportES2() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return (configurationInfo.reqGlEsVersion >= 0x20000);
    }

    private void createGameView() {
        GameView gameView = new GameView(classMediator);
        gameView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(gameView);
    }





    /**
     * Class provides real width, real height and density of the screen.
     */
    public static class ScreenParams {
        public float dp_SCREEN_HEIGHT;  // Real height in dp.
        public float dp_SCREEN_WIDTH;  // Real height in dp.
        public int pxl_SCREEN_HEIGHT;  // Real height in pixels.
        public int pxl_SCREEN_WIDTH;  // Real width in pixels.
        public float DENSITY;  // Pixel amount in one dp.


        public ScreenParams(AppCompatActivity appCompatActivity) {
            findSizeParams2(appCompatActivity);
        }

        public int transit_DP_to_PX(float dp_value) {
            return (int) (dp_value * DENSITY);
        }

        public float transit_PX_to_DP(int px_value) {
            return px_value / DENSITY;
        }


        public void findSizeParams(AppCompatActivity appCompatActivity) {
            DisplayMetrics displayMetrics = appCompatActivity.getResources().getDisplayMetrics();
            DENSITY = displayMetrics.density;
            Point screenSize = new Point();
            appCompatActivity.getWindowManager().getDefaultDisplay().getRealSize(screenSize);

            pxl_SCREEN_WIDTH = screenSize.x;
            pxl_SCREEN_HEIGHT = screenSize.y;

            dp_SCREEN_HEIGHT = pxl_SCREEN_HEIGHT / DENSITY;
            dp_SCREEN_WIDTH = pxl_SCREEN_WIDTH / DENSITY;
        }

        public void findSizeParams2(AppCompatActivity appCompatActivity) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            appCompatActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            DENSITY = displayMetrics.density;

            dp_SCREEN_HEIGHT = height / DENSITY;
            dp_SCREEN_WIDTH = width / DENSITY;

            dp_SCREEN_HEIGHT += 0;
            dp_SCREEN_WIDTH += 48f;

            pxl_SCREEN_HEIGHT = (int) (dp_SCREEN_HEIGHT * DENSITY);
            pxl_SCREEN_WIDTH = (int) (dp_SCREEN_WIDTH * DENSITY);
        }

    }


}