package com.example.android.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by Paulina on 10-7-2017.
 */

public class HabitContract {

    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habits";

        public final static String COLUMN_NAME = "name";

        public final static String COLUMN_EXERCISE_TYPE = "exercise_type";

        public final static String COLUMN_EXERCISE_DURATION = "exercise_duration";

        public static final int DURATION_1 = 0;
        public static final int DURATION_2 = 1;
        public static final int DURATION_3 = 2;
        public static final int DURATION_4 = 3;
        public static final int DURATION_5 = 4;
    }

}
