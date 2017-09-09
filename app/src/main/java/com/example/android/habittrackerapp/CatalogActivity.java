package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittrackerapp.data.HabitDbHelper;
import com.example.android.habittrackerapp.data.HabitContract.HabitEntry;


public class CatalogActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_habit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
        readDatabase();
        insertHabit();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        TextView displayView = (TextView) findViewById(R.id.text_view_entry);
        Cursor cursor = db.rawQuery("SELECT * FROM " + HabitEntry.TABLE_NAME, null);

        try {
            displayView.setText("The habit table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry.COLUMN_NAME + " - " +
                    HabitEntry.COLUMN_EXERCISE_TYPE + " - " +
                    HabitEntry.COLUMN_EXERCISE_DURATION + "\n");

            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int exerciseTypeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_EXERCISE_TYPE);
            int exerciseDurationColumnIndex = cursor.getColumnIndex(HabitEntry
                    .COLUMN_EXERCISE_DURATION);

            while (cursor.moveToNext()) {
                String currentName = cursor.getString(nameColumnIndex);
                String currentExerciseType = cursor.getString(exerciseTypeColumnIndex);
                int currentExerciseDuration = cursor.getInt(exerciseDurationColumnIndex);
                displayView.append(("\n" + currentName + " - " +
                        currentExerciseType + " - " +
                        currentExerciseDuration));
            }
        } finally {
            cursor.close();
        }
    }

    private Cursor readDatabase() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_EXERCISE_TYPE,
                HabitEntry.COLUMN_EXERCISE_DURATION};

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private void insertHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, "Gerrit");
        values.put(HabitEntry.COLUMN_EXERCISE_TYPE, "cycling");
        values.put(HabitEntry.COLUMN_EXERCISE_DURATION, "2");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
