package com.example.rollcube.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.rollcube.GameBoard;
import com.example.rollcube.gameinterface.mainmenu.GameRegimeSwitcher;
import com.example.rollcube.gameinterface.mainmenu.records.Records;

import java.util.Arrays;
import java.util.LinkedList;


public class DBHelper extends SQLiteOpenHelper {

    private final Records.RecordArrays recordArrays;
    private LinkedList<GameBoard.GameType> gameTypeList;
    private LinkedList<GameRegimeSwitcher.PointsRegime> pointsRegimeList;
    private LinkedList<GameRegimeSwitcher.TimeRegime> timeRegimeList;


    public DBHelper(@Nullable Context context, @Nullable String name, Records.RecordArrays recordArrays) {
        super(context, name, null, 1);
        this.recordArrays = recordArrays;
        initLists();
    }


    private void initLists() {
        gameTypeList = new LinkedList<>();
        pointsRegimeList = new LinkedList<>();
        timeRegimeList = new LinkedList<>();

        GameBoard.GameType[] gameTypes = GameBoard.GameType.values();
        gameTypeList.addAll(Arrays.asList(gameTypes));

        GameRegimeSwitcher.PointsRegime[] pointsRegimes = GameRegimeSwitcher.PointsRegime.values();
        pointsRegimeList.addAll(Arrays.asList(pointsRegimes));

        GameRegimeSwitcher.TimeRegime[] timeRegimes = GameRegimeSwitcher.TimeRegime.values();
        timeRegimeList.addAll(Arrays.asList(timeRegimes));
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        recordArrays.initStartingValues();
        createTables(sqLiteDatabase);
        saveRecordArraysToSQL(sqLiteDatabase, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




    public void uploadRecordArraysFromSQL() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from points_records_table", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int gameType = cursor.getInt(0);
            int time_regime = cursor.getInt(1);
            int idx = cursor.getInt(2);
            int value = cursor.getInt(3);
            recordArrays.pointRecordsArr[gameType][time_regime][idx] = value;
            cursor.moveToNext();
        }
        cursor.close();

        cursor = sqLiteDatabase.rawQuery("select * from time_records_table", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int gameType = cursor.getInt(0);
            int points_regime = cursor.getInt(1);
            int idx = cursor.getInt(2);
            int value = cursor.getInt(3);
            recordArrays.timeRecordsArr[gameType][points_regime][idx] = value;
            cursor.moveToNext();
        }
        cursor.close();

        sqLiteDatabase.close();
    }

    public void saveRecordArraysToSQL(SQLiteDatabase sqLiteDatabase, boolean isFirstSaving) {
        for (GameBoard.GameType gameType: gameTypeList) {
            if(gameType.ordinal() < gameTypeList.size() - 1) {
                for (GameRegimeSwitcher.TimeRegime timeRegime: timeRegimeList) {
                    for (int i = 0; i < Records.RecordArrays.LIST_LENGTH; i++) {
                        int value = recordArrays.pointRecordsArr[gameType.ordinal()][timeRegime.ordinal()][i];
                        insertPointsRecord(sqLiteDatabase, gameType.ordinal(), timeRegime.ordinal(), i, value);
                    }
                }
            }
        }
        for (GameBoard.GameType gameType: gameTypeList) {
            if(gameType.ordinal() < gameTypeList.size() - 1) {
                for (GameRegimeSwitcher.PointsRegime pointsRegime: pointsRegimeList) {
                    for (int i = 0; i < Records.RecordArrays.LIST_LENGTH; i++) {
                        long value = recordArrays.timeRecordsArr[gameType.ordinal()][pointsRegime.ordinal()][i];
                        insertTimeRecord(sqLiteDatabase, gameType.ordinal(), pointsRegime.ordinal(), i, value);
                    }
                }
            }
        }
        if(!isFirstSaving) {
            sqLiteDatabase.close();
        }
    }

    private void insertPointsRecord(SQLiteDatabase sqLiteDatabase, int gameType, int timeRegime, int idx, int value) {
        sqLiteDatabase.execSQL("insert into points_records_table (game_type, time_regime, idx, value) values(" + gameType + "," + timeRegime + "," + idx + "," + value + ");");
    }

    private void insertTimeRecord(SQLiteDatabase sqLiteDatabase, int gameType, int pointsRegime, int idx, long value) {
        sqLiteDatabase.execSQL("insert into time_records_table (game_type, points_regime, idx, value) values(" + gameType + "," + pointsRegime + "," + idx + "," + value + ");");
    }

    public void deleteAllRecords() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM time_records_table;");
        sqLiteDatabase.execSQL("DELETE FROM points_records_table;");
        sqLiteDatabase.close();
    }

    public void createTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists time_records_table (game_type int, points_regime int, idx int, value bigint);");
        sqLiteDatabase.execSQL("create table if not exists points_records_table (game_type int, time_regime int, idx int, value int);");
    }




}
