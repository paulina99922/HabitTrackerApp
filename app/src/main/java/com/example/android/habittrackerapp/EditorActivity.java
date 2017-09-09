package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittrackerapp.data.HabitDbHelper;
import com.example.android.habittrackerapp.data.HabitContract.HabitEntry;

import static com.example.android.habittrackerapp.data.HabitContract.HabitEntry.DURATION_5;

public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mExerciseTypeEditText;
    private Spinner mExerciseDurationSpinner;

    /**
     * {@link HabitEntry#DURATION_1), {@link HabitEntry#DURATION_2}, {@link HabitEntry#DURATION_3},
     * {@link HabitEntry#DURATION_4}or{@link HabitEntry#DURATION_5}.
     */

    private int mExerciseDuration = HabitEntry.DURATION_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mExerciseTypeEditText = (EditText) findViewById(R.id.edit_exercise);
        mExerciseDurationSpinner = (Spinner) findViewById(R.id.spinner_duration);
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter durationSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_duration_options, android.R.layout.simple_spinner_item);

        durationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mExerciseDurationSpinner.setAdapter(durationSpinnerAdapter);

        mExerciseDurationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener
                () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.duration_1))) {
                        mExerciseDuration = HabitEntry.DURATION_1;
                    } else if (selection.equals(getString(R.string.duration_2))) {
                        mExerciseDuration = HabitEntry.DURATION_2;
                    } else if (selection.equals(getString(R.string.duration_3))) {
                        mExerciseDuration = HabitEntry.DURATION_3;
                    } else if (selection.equals(getString(R.string.duration_4))) {
                        mExerciseDuration = HabitEntry.DURATION_4;
                    } else if (selection.equals(getString(R.string.duration_5))) {
                        mExerciseDuration = DURATION_5;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mExerciseDuration = HabitEntry.DURATION_5;
            }
        });
    }


    private void insertHabit() {
        String nameString = mNameEditText.getText().toString().trim();
        String exerciseTypeString = mExerciseTypeEditText.getText().toString().trim();

        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, nameString);
        values.put(HabitEntry.COLUMN_EXERCISE_TYPE, exerciseTypeString);
        values.put(HabitEntry.COLUMN_EXERCISE_DURATION, mExerciseDuration);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving the entry", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Entry saved. Its ID is: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                insertHabit();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}