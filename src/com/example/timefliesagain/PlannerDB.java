package com.example.timefliesagain;

public class PlannerDB {
    // Table name
    public static final String TABLE1 = "planner";
    public static final String TABLE2 = "availability";
    public static final String TABLE3 = "toDo";


    // Column names
    public static final String KEY_ID_1 = "id1";
    public static final String KEY_date = "date";
    public static final String KEY_time_start = "time_start";
    public static final String KEY_time_end = "time_end";
    public static final String KEY_description = "description";

    public static final String KEY_ID_2 = "id2";
    public static final String KEY_availability = "availability";

    public static final String KEY_ID_3 = "id3";
    public static final String KEY_taskName = "taskName";
    public static final String KEY_taskDuration = "taskDuration";

    // Variable types
    public int planning_ID_1;
    public String date;
    public String time_start;
    public String time_end;
    public String description;

    public int planning_ID_2;
    public String availability;

    public int planning_ID_3;
    public String taskName;
    public String taskDuration;
}

