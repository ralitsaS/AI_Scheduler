package com.example.timefliesagain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "planning.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //All necessary tables you'd like to create will be created here
        String CREATE_TABLE_APPOINTMENT = "CREATE TABLE " + PlannerDB.TABLE1  + "("
                + PlannerDB.KEY_ID_1  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlannerDB.KEY_date + " TEXT, "
                + PlannerDB.KEY_time_start + " TEXT, "
                + PlannerDB.KEY_time_end + " TEXT, "
                + PlannerDB.KEY_description + " TEXT); ";

        db.execSQL(CREATE_TABLE_APPOINTMENT);

        String CREATE_TABLE_AVAILABILITY = "CREATE TABLE " + PlannerDB.TABLE2  + "("
                + PlannerDB.KEY_ID_2  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlannerDB.KEY_availability + " TEXT); ";

        db.execSQL(CREATE_TABLE_AVAILABILITY);

        String CREATE_TABLE_TODO = "CREATE TABLE " + PlannerDB.TABLE3  + "("
                + PlannerDB.KEY_ID_3  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlannerDB.KEY_taskName + " TEXT, "
                + PlannerDB.KEY_taskDuration + " TEXT); ";

        db.execSQL(CREATE_TABLE_TODO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + PlannerDB.TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + PlannerDB.TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + PlannerDB.TABLE3);

        // Create tables again
        onCreate(db);

    }
}
