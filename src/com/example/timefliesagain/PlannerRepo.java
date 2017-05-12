package com.example.timefliesagain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PlannerRepo {

    private DBHelper dbHelper;

    public PlannerRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Add new database entry appointment
    /*
    public int insertAppointment(PlannerDB planner) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlannerDB.KEY_date, planner.date);
        values.put(PlannerDB.KEY_time_start,planner.time_start);
        values.put(PlannerDB.KEY_time_end,planner.time_end);
        values.put(PlannerDB.KEY_description, planner.description);

        // Inserting Row
        long planner_Id = db.insert(PlannerDB.TABLE1, null, values);
        db.close(); // Closing database connection
        return (int) planner_Id;
    }
	*/
    public void insertAppointment_NEW(String start, String end, String desc) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlannerDB.KEY_time_start,start);
        values.put(PlannerDB.KEY_time_end,end);
        values.put(PlannerDB.KEY_description, desc);

        // Inserting Row
        db.insert(PlannerDB.TABLE1, null, values);
        db.close(); // Closing database connection
        
    }
    
    // Add new database entry availability
    public void insertAvailability_NEW(String date, String avail) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlannerDB.KEY_date, date);
        values.put(PlannerDB.KEY_availability, avail);

        db.insert(PlannerDB.TABLE2, null, values);
        db.close(); // Closing database connection
    }

    // Add new database entry to-do task
    public int insertToDo(PlannerDB toDo) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlannerDB.KEY_taskName, toDo.taskName);
        values.put(PlannerDB.KEY_taskDuration, toDo.taskDuration);

        long planner_Id = db.insert(PlannerDB.TABLE3, null, values);
        db.close(); // Closing database connection
        return (int) planner_Id;
    }
    
 // Add new database entry to-do task
    public void insertToDo_NEW(String taskN, String taskD) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlannerDB.KEY_taskName, taskN);
        values.put(PlannerDB.KEY_taskDuration, taskD);

        db.insert(PlannerDB.TABLE3, null, values);
        db.close(); // Closing database connection
    }
    
    //Add new database entry notes
    public void insertNote(String note) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlannerDB.KEY_note, note);

        db.insert(PlannerDB.TABLE4, null, values);
        db.close(); // Closing database connection
    }

    // Delete database entry
    public void deleteAppointment(int iD) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(PlannerDB.TABLE1, PlannerDB.KEY_ID_1 + "= ?", new String[] { String.valueOf(iD) });
        db.close(); // Closing database connection
    }

    public void deleteAvailability(int iD) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(PlannerDB.TABLE2, PlannerDB.KEY_ID_2 + "= ?", new String[] { String.valueOf(iD) });
        db.close(); // Closing database connection
    }

    public void deleteToDo(String iD) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(PlannerDB.TABLE3, PlannerDB.KEY_ID_3 + "= ?", new String[] { String.valueOf(iD) });
        db.close(); // Closing database connection
    }

    public void deleteToDoALL() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(PlannerDB.TABLE3, null, null);
        db.close(); // Closing database connection
    }
    
    // Update database entry appointment
    /*
    public void updateAppointment(PlannerDB planner) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PlannerDB.KEY_date, planner.date);
        values.put(PlannerDB.KEY_time_start,planner.time_start);
        values.put(PlannerDB.KEY_time_end,planner.time_end);
        values.put(PlannerDB.KEY_description, planner.description);

        db.update(PlannerDB.TABLE1, values, PlannerDB.KEY_ID_1 + "= ?", new String[] { String.valueOf(planner.planning_ID_1) });
        db.close(); // Closing database connection
    }
	*/
    
    // Update database entry availability
    public void updateAvailability(PlannerDB availability) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PlannerDB.KEY_availability, availability.availability);

        db.update(PlannerDB.TABLE2, values, PlannerDB.KEY_ID_2 + "= ?", new String[] { String.valueOf(availability.planning_ID_2) });
        db.close(); // Closing database connection
    }

    // Update database entry to-do
    public void updateToDo(PlannerDB toDo) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PlannerDB.KEY_taskName, toDo.taskName);
        values.put(PlannerDB.KEY_taskDuration, toDo.taskDuration);

        db.update(PlannerDB.TABLE3, values, PlannerDB.KEY_ID_3 + "= ?", new String[] { String.valueOf(toDo.planning_ID_3) });
        db.close(); // Closing database connection

    }
    
    public void updateToDo_NEW(int id, String task, String dur) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PlannerDB.KEY_taskName, task);
        values.put(PlannerDB.KEY_taskDuration, dur);

        db.update(PlannerDB.TABLE3, values, null, new String[] { String.valueOf(id) });
        //db.close(); // Closing database connection

    }

    // Get all database entries appointment
    public ArrayList<HashMap<String, String>> getAppointmentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_1 + "," +
               // PlannerDB.KEY_date + "," +
                PlannerDB.KEY_time_start + "," +
                PlannerDB.KEY_time_end + "," +
                PlannerDB.KEY_description +
                " FROM " + PlannerDB.TABLE1;

        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> planning = new HashMap<String, String>();
                planning.put("id1", cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_ID_1)));
                planning.put("description", cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_description)));
                appointmentList.add(planning);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return appointmentList;

    }
    
    ////////////
    public List<EventObjects> getAllFutureEvents(Date mDate){
        Calendar calDate = Calendar.getInstance();
        Calendar dDate = Calendar.getInstance();
        calDate.setTime(mDate);

        int calDay = calDate.get(Calendar.DAY_OF_MONTH);
        int calMonth = calDate.get(Calendar.MONTH) + 1;
        int calYear = calDate.get(Calendar.YEAR);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_1 + "," +
               // PlannerDB.KEY_date + "," +
                PlannerDB.KEY_time_start + "," +
                PlannerDB.KEY_time_end + "," +
                PlannerDB.KEY_description +
                " FROM " + PlannerDB.TABLE1;
        
        List<EventObjects> events = new ArrayList();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_description));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow(PlannerDB.KEY_time_start));
                String endDate = cursor.getString(cursor.getColumnIndexOrThrow(PlannerDB.KEY_time_end));
                Log.i("msg", message);
                Log.i("msg", startDate);
                Log.i("msg", endDate);
                //convert start date to date object
                Date reminderDate = convertStringToDate(startDate);
                Date end = convertStringToDate(endDate);
                
                if(reminderDate != null)
                {
                dDate.setTime(reminderDate);
                int dDay = dDate.get(Calendar.DAY_OF_MONTH);
                int dMonth = dDate.get(Calendar.MONTH) + 1;
                int dYear = dDate.get(Calendar.YEAR);

                if(calDay == dDay && calMonth == dMonth && calYear == dYear){
                    events.add(new EventObjects(id, message, reminderDate, end));
                }
                }else
                	Log.i("fuck", "it's null");
            }while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    private Date convertStringToDate(String dateInString){
        SimpleDateFormat format = new SimpleDateFormat("d-MM-yyyy HH:mm", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    ///////////

    // Get all database entries availability
    public ArrayList<HashMap<String, String>> getAvailabilityList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_2 + "," +
                PlannerDB.KEY_date + "," +
                PlannerDB.KEY_availability +
                " FROM " + PlannerDB.TABLE2;

        ArrayList<HashMap<String, String>> availabilityList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> availability = new HashMap<String, String>();
                availability.put("date", cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_date)));
                availability.put("availability", cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_availability)));
                availabilityList.add(availability);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return availabilityList;

    }

    // Get all database entries to-do
    public ArrayList<HashMap<String, String>> getToDoList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_3 + "," +
                PlannerDB.KEY_taskName + "," +
                PlannerDB.KEY_taskDuration  +
                " FROM " + PlannerDB.TABLE3;

        ArrayList<HashMap<String, String>> toDoList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> toDo = new HashMap<String, String>();
                toDo.put("id3", cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_ID_3)));
                toDo.put("taskName", cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_taskName)));
                toDo.put("taskDuration", cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_taskDuration)));
                toDoList.add(toDo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toDoList;

    }
    
 // Get all database entries notes
    public ArrayList<String> getNotesList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_4 + "," +
                PlannerDB.KEY_note  +
                " FROM " + PlannerDB.TABLE4;

        ArrayList<String> notesList = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
               
                notesList.add(cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_note)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notesList;

    }

    // Get appointment by ID
    /*
    public PlannerDB getAppointmentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_1 + "," +
                PlannerDB.KEY_date + "," +
                PlannerDB.KEY_time_start + "," +
                PlannerDB.KEY_time_end + "," +
                PlannerDB.KEY_description +
                " FROM " + PlannerDB.TABLE1
                + " WHERE " +
                PlannerDB.KEY_ID_1 + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        PlannerDB planner = new PlannerDB();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                planner.planning_ID_1 =cursor.getInt(cursor.getColumnIndex(PlannerDB.KEY_ID_1));
                planner.date =cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_date));
                planner.time_start  =cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_time_start));
                planner.time_end  =cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_time_end));
                planner.description =cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_description));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return planner;
    }
	*/
    
    // Get availability by ID
    public PlannerDB getAvailabilityById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_2 + "," +
                PlannerDB.KEY_availability + "," +
                " FROM " + PlannerDB.TABLE2
                + " WHERE " +
                PlannerDB.KEY_ID_2 + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        PlannerDB availability = new PlannerDB();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                availability.planning_ID_2 =cursor.getInt(cursor.getColumnIndex(PlannerDB.KEY_ID_2));
                availability.availability =cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_availability));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return availability;
    }

    // Get to-do task by ID
    public PlannerDB getToDoById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PlannerDB.KEY_ID_3 + "," +
                PlannerDB.KEY_taskName + "," +
                PlannerDB.KEY_taskDuration + "," +
                " FROM " + PlannerDB.TABLE3
                + " WHERE " +
                PlannerDB.KEY_ID_3 + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        PlannerDB toDo = new PlannerDB();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                toDo.planning_ID_3 =cursor.getInt(cursor.getColumnIndex(PlannerDB.KEY_ID_3));
                toDo.taskName =cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_taskName));
                toDo.taskDuration =cursor.getString(cursor.getColumnIndex(PlannerDB.KEY_taskDuration));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toDo;
    }
}
