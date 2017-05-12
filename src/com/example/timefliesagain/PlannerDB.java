package com.example.timefliesagain;

import java.util.Date;

public class PlannerDB {
    // Table name
    public static final String TABLE1 = "planner";
    public static final String TABLE2 = "availability";
    public static final String TABLE3 = "toDo";
    public static final String TABLE4 = "notes";


    // Column names
    public static final String KEY_ID_1 = "id1";
    public static final String KEY_time_start = "time_start";
    public static final String KEY_time_end = "time_end";
    public static final String KEY_description = "description";

    public static final String KEY_ID_2 = "id2";
    public static final String KEY_date = "date";
    public static final String KEY_availability = "availability";

    public static final String KEY_ID_3 = "id3";
    public static final String KEY_taskName = "taskName";
    public static final String KEY_taskDuration = "taskDuration";
    
    public static final String KEY_ID_4 = "id4";
    public static final String KEY_note = "note";

    // Variable types
    public int planning_ID_1;
   // public String date;
    public Date time_start;
    public Date time_end;
    public String description;

    public int planning_ID_2;
    public String date;
    public String availability;

    public int planning_ID_3;
    public String taskName;
    public String taskDuration;
    
    public int planning_ID_4;
    public String note;
}

