package com.example.timefliesagain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import android.support.v4.app.Fragment;


public class MainActivity extends ActionBarActivity
implements NavigationDrawerFragment.NavigationDrawerCallbacks{
	
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button previousDay;
    private Button nextDay;
    private Button openToDo;
    public static TextView currentDate;
    private Calendar cal = Calendar.getInstance();
    private RelativeLayout mLayout;
    public static int eventIndex;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private PlannerRepo repo_inst;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        this.setContentView(R.layout.activity_main); 
        repo_inst = new PlannerRepo(this);
        
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ///////////////////////
        
        getActionBar().setCustomView(R.layout.custom_bar);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        
        openToDo = (Button)findViewById(R.id.custom_todo);
        openToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            	Intent myIntent = new Intent(MainActivity.this, ToDoList.class);
	               // myIntent.putExtra("key", value); //Optional parameters
	                MainActivity.this.startActivity(myIntent);
            }
        });
        
        ///////////////////////
        mLayout = (RelativeLayout)findViewById(R.id.left_event_column);
        eventIndex = mLayout.getChildCount();
        Log.i("eventIndex1", Integer.toString(eventIndex));
        currentDate = (TextView)findViewById(R.id.display_current_date);
        currentDate.setText(displayDateInString(cal.getTime()));
        try {
			deleteOldPlanned(currentDate.getText().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        displayDailyEvents();
        setAvailView(currentDate);
        setTaskView(currentDate);
        previousDay = (Button)findViewById(R.id.previous_day);
        nextDay = (Button)findViewById(R.id.next_day);
        
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCalendarDate();
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCalendarDate();
            }
        });
        
       
        
    }
    
    
    
    private void previousCalendarDate(){
    	if(mLayout.getChildAt(eventIndex-1)!=null) 
    		{
    		int new_index = mLayout.getChildCount();
    		//Log.i("eventIndex2", Integer.toString(mLayout.getChildCount()));
    		for(int i=1; i <= new_index-eventIndex; i++ ) mLayout.removeViewAt(eventIndex - 1);
    		//mLayout.removeView(mTaskView);
    		//Log.i("eventIndex3", Integer.toString(mLayout.getChildCount()));
    		} 
    	
        cal.add(Calendar.DAY_OF_MONTH, -1);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
        setAvailView(currentDate);
        setTaskView(currentDate);
    }
    
    
    private void nextCalendarDate(){
    	if(mLayout.getChildAt(eventIndex-1)!=null) 
    		{int new_index = mLayout.getChildCount();
    		//Log.i("eventIndex2", Integer.toString(mLayout.getChildCount()));
    		for(int i=1; i <= new_index-eventIndex; i++ ) mLayout.removeViewAt(eventIndex - 1);
    		//mLayout.removeView(mTaskView);
    		//Log.i("eventIndex5", Integer.toString(mLayout.getChildCount()));
    		} 
    	
        cal.add(Calendar.DAY_OF_MONTH, 1);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
        setAvailView(currentDate);
        setTaskView(currentDate);
    }
    
    
    private String displayDateInString(Date mDate){
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
        return formatter.format(mDate);
    }
    
    private void deleteOldPlanned(String now) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
        ArrayList<HashMap<String, String>> planned_list = repo_inst.getPlannedList();
        ArrayList<String> old_dates= new ArrayList<String>();
        for(int i =0; i<planned_list.size();i++)
        	if(sdf.parse(planned_list.get(i).get("day")).before(sdf.parse(now))) old_dates.add(planned_list.get(i).get("day"));
        
        for(int j =0; j<old_dates.size();j++) repo_inst.deletePlanned(old_dates.get(j));
    }
    
    private void displayDailyEvents(){
        Date calendarDate = cal.getTime();
        List<EventObjects> dailyEvent = repo_inst.getAllFutureEvents(calendarDate);
        
        for(EventObjects eObject : dailyEvent){
            Date eventDate = eObject.getDate();
            Date endDate = eObject.getEnd();
            String eventMessage = eObject.getMessage();
            int eventBlockHeight = getEventTimeFrame(eventDate, endDate);
            Log.d(TAG, "Height " + eventBlockHeight);
            displayEventSection(eventDate, eventBlockHeight, eventMessage);
        }
        
    }
    private int getEventTimeFrame(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(timeDifference);
        int hours = mCal.get(Calendar.HOUR);
        int minutes = mCal.get(Calendar.MINUTE);
        return (hours * 60) + ((minutes * 60) / 100);
    }
    private void displayEventSection(Date eventDate, int height, String message){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(eventDate);
        String[]hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(TAG, "Hour value " + hours);
        Log.d(TAG, "Minutes value " + minutes);
        int topViewMargin = (hours * 60) + ((minutes * 60) / 100);
        Log.d(TAG, "Margin top " + topViewMargin);
        createEventView(eventDate, topViewMargin, height, message);
    }
    private void createEventView(Date start_date, int topMargin, int height, String message){
        TextView mEventView = new TextView(MainActivity.this);
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = (int) (topMargin * 1.5);
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight((int) (height * 1.5));
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(message);
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
        
        SimpleDateFormat format = new SimpleDateFormat("d-MM-yyyy HH:mm", Locale.ENGLISH);
        final String start = format.format(start_date);
        
        mEventView.setClickable(true);
        mEventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            	LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
                final View mView = layoutInflaterAndroid.inflate(R.layout.del_ev_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilderUserInput.setView(mView);
                
                alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    	
                    	mLayout.removeView(v);
                    	repo_inst.deleteAppointment(start);
                    	
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
            }
        });
        mLayout.addView(mEventView, eventIndex - 1);
        Log.i("eventIndex6", Integer.toString(mLayout.getChildCount()));
        
    }
    
    
    
    public void setAvailView(TextView cur_date){
    	ArrayList<HashMap<String, String>> avail_list = repo_inst.getAvailabilityList();
        String avail = null;
        String[] avail_hours = null;
        
        for(int i=0; i < avail_list.size(); i++)
        {
        	if(avail_list.get(i).get("date").equals(cur_date.getText().toString())) 
    		{
    			avail=avail_list.get(i).get("availability");
    			avail_hours = avail.split(" ");
    		}
        }
        
        
        for(int x=1; x <= 24; x++)
        			{
        				//Log.i("hour", avail_hours[j]);
        				//String hourID = "hour_"+avail_hours[j];
    					String hourID = "hour_"+x;
        				int h_id = getResources().getIdentifier(hourID, "id", getPackageName());
        				//Log.i("hour id", Integer.toString(h_id));
        				View hour =(View)findViewById(h_id);
        				hour.setBackgroundColor(0xFFDEDEDE);
        				
        				if(avail_hours!=null){
        					for(int j=1; j < avail_hours.length; j++)
                			{
                				if(avail_hours[j].equals(Integer.toString(x))) hour.setBackgroundColor(0xFFFFFFFF);
                			
                			}
        				}
        				
        			}
        	
    }
    
    
    public void setTaskView(TextView cur_date){
    	ArrayList<HashMap<String, String>> planned_tasks = repo_inst.getPlannedList();
        
        for(int i=0; i < planned_tasks.size(); i++)
        {
        	if(planned_tasks.get(i).get("day").equals(cur_date.getText().toString())) 
        		{
        			
        		double topMargin = 60*(Integer.parseInt(planned_tasks.get(i).get("hour_start"))-1)+1;
        		double height = 59*Integer.parseInt(planned_tasks.get(i).get("length"));
        		String message = planned_tasks.get(i).get("message");
        		//mLayout = (RelativeLayout)this.getActivity().findViewById(R.id.left_event_column);
        		
        		TextView mTaskView = new TextView(this);
                RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                
				lParam.topMargin = (int) (topMargin * 1.5);
                lParam.leftMargin = 40;
                mTaskView.setLayoutParams(lParam);
                mTaskView.setPadding(24, 0, 24, 0);
                mTaskView.setHeight((int) (height * 1.5));
                mTaskView.setGravity(0x11);
                mTaskView.setTextColor(Color.parseColor("#ffffff"));
                mTaskView.setText(message);
                mTaskView.setBackgroundColor(Color.parseColor("#3fb57d"));
                if(mLayout!=null) mLayout.addView(mTaskView, eventIndex - 1);
                Log.i("index", Integer.toString(mLayout.indexOfChild(mTaskView)));
                Log.i("eventIndex2", Integer.toString(mLayout.getChildCount()));
        			
        		}
        }
    }
    //////////////////////////////////////////
    
    
   public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit(); 
    	
    }
    
    
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.add_event);
                break;
            case 2:
                mTitle = getString(R.string.add_task);
                break;
            case 3:
                mTitle = getString(R.string.set_availability);
                break;
            case 4:
                mTitle = getString(R.string.notes);
                break;
            case 5:
                mTitle = getString(R.string.plan);
                break;    
        }
    }
    
    
    
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    
}