package com.example.rollcube.managers;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.rollcube.R;



public class SoundManager {
    private final SoundPool soundPool;

    public int cubeFriction;
    public int cubeHit;
    public int wallUp;
    public int tileTwist;
    public int platformRotation;


    public SoundManager(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        cubeFriction = soundPool.load(context, R.raw.quick_friction, 1);
        cubeHit = soundPool.load(context, R.raw.wooden_hit, 1);
        wallUp = soundPool.load(context, R.raw.wall_up, 1);
        tileTwist = soundPool.load(context, R.raw.fan_2_350ms, 1);
        platformRotation = soundPool.load(context, R.raw.fan_2, 1);

    }

    public void playCubeHit() {
        soundPool.play(cubeHit, 1f, 1f, 1, 0, 1f);
    }
    public void playCubeFriction() {
        soundPool.play(cubeFriction, 1f, 1f, 1, 0, 1f);
    }
    public void playTileTwist() {
        soundPool.play(tileTwist, 1f, 1f, 1, 0, 1f);
    }
    public void playWallUp() {
        soundPool.play(wallUp, 1f, 1f, 1, 0, 1f);
    }
    public void playPlatformRotation() {
        soundPool.play(platformRotation, 1f, 1f, 1, 0, 1f);
    }

}
