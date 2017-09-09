package com.example.android.habittrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittrackerapp.data.HabitContract.HabitEntry;


/**
 * Created by Paulina on 10-7-2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper {


    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    public static final String DATABASE_TITLE = "habit.db";

    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_TITLE, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_EXERCISE_TYPE + " TEXT, "
                + HabitEntry.COLUMN_EXERCISE_DURATION + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
