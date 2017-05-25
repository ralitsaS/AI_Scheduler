package com.example.timefliesagain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class NavigationDrawerFragment extends Fragment {


    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;
    private PlannerRepo repo_inst;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private int mCurrentSelectedPosition = 0;
    private RelativeLayout mLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        repo_inst = new PlannerRepo(this.getActivity());
        // Select the default item.
       // selectItem(mCurrentSelectedPosition);
        
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // the fragment has menu items to contribute
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        
        mDrawerListView.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.add_event),
                        getString(R.string.add_task),
                        getString(R.string.set_availability),
                        getString(R.string.notes),
                        getString(R.string.plan),
                        getString(R.string.quest),
                }));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    private android.app.ActionBar getActionBar() {
        return this.getActivity().getActionBar();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        //drawerLayout.closeDrawers();
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu();
            }
        };


        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if(position==0)
    	{
        	View view = this.getActivity().getCurrentFocus();
        	if (view != null) {  
        	    InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        	}
            //AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.getActivity());
            View mView = layoutInflaterAndroid.inflate(R.layout.addevent_dialog, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this.getActivity());
            alertDialogBuilderUserInput.setView(mView);

            final DatePicker date_picked = (DatePicker)mView.findViewById(R.id.datePicker1);
            final TimePicker pick_start = (TimePicker)mView.findViewById(R.id.start_time);
            final TimePicker pick_end = (TimePicker)mView.findViewById(R.id.end_time);
            final EditText pick_desc = (EditText)mView.findViewById(R.id.userInputDialog);
            
            
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            // ToDo get user input here
                        	int day = date_picked.getDayOfMonth();
                            int month = date_picked.getMonth();
                            int year =  date_picked.getYear();
                            int hour_start = pick_start.getHour();
                            int min_start = pick_start.getMinute();
                            int hour_end = pick_end.getHour();
                            int min_end = pick_end.getMinute();
                            
                            Calendar cal1 = Calendar.getInstance();
                            cal1.set(Calendar.YEAR, year);
                            cal1.set(Calendar.MONTH, month);
                            cal1.set(Calendar.DATE, day);
                            cal1.set(Calendar.HOUR_OF_DAY, hour_start);
                            cal1.set(Calendar.MINUTE, min_start);
                            cal1.set(Calendar.SECOND, 0);
                            cal1.set(Calendar.MILLISECOND, 0);
                            Date event_start = cal1.getTime();
                            
                            SimpleDateFormat format = new SimpleDateFormat("d-MM-yyyy HH:mm", Locale.ENGLISH);
                            String start = format.format(event_start);
                            
                            Calendar cal2 = Calendar.getInstance();
                            cal2.set(Calendar.YEAR, year);
                            cal2.set(Calendar.MONTH, month);
                            cal2.set(Calendar.DATE, day);
                            cal2.set(Calendar.HOUR_OF_DAY, hour_end);
                            cal2.set(Calendar.MINUTE, min_end);
                            cal2.set(Calendar.SECOND, 0);
                            cal2.set(Calendar.MILLISECOND, 0);
                            Date event_end = cal2.getTime();
                            
                            String end = format.format(event_end);
                            
                            String desc  = pick_desc.getText().toString();
                            
                            repo_inst.insertAppointment_NEW(start, end, desc);
                            
                            int topMargin = (hour_start * 60) + ((min_start * 60) / 100);
                            
                            long timeDifference = event_end.getTime() - event_start.getTime();
                            Calendar mCal = Calendar.getInstance();
                            mCal.setTimeInMillis(timeDifference);
                            int hours = mCal.get(Calendar.HOUR);
                            int minutes = mCal.get(Calendar.MINUTE);
                            int height = (hours * 60) + ((minutes * 60) / 100);
                            
                            
                            mLayout = (RelativeLayout)getActivity().findViewById(R.id.left_event_column);
                            TextView mEventView = new TextView(getActivity());
                            RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                            lParam.topMargin = (int) (topMargin * MainActivity.scr_dens);
                            lParam.leftMargin = 24;
                            mEventView.setLayoutParams(lParam);
                            mEventView.setPadding(24, 0, 24, 0);
                            mEventView.setHeight((int) (height * MainActivity.scr_dens));
                            mEventView.setGravity(0x11);
                            mEventView.setTextColor(Color.parseColor("#ffffff"));
                           
                            mEventView.setText(desc);
                            mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
                            mLayout.addView(mEventView, MainActivity.eventIndex - 1);
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
            mDrawerLayout.closeDrawers();
    	}
        
        if(position==1)
        {
        	Intent myIntent = new Intent(this.getActivity(), ToDoList.class);
            // myIntent.putExtra("key", value); //Optional parameters
        	this.getActivity().startActivity(myIntent);
        }
        
        if(position==2)
        {
        	LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.getActivity());
            final View mView = layoutInflaterAndroid.inflate(R.layout.avail_dialog, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this.getActivity());
            alertDialogBuilderUserInput.setView(mView);
            
            final String curD = MainActivity.currentDate.getText().toString();
            TextView set_avail_text = (TextView)mView.findViewById(R.id.text_avail);
            set_avail_text.setText("Set available hours for "+curD);
            
            
            
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {

                        	String avail_hours=" ";

                        	for(int i=1; i<=24; i++)
                        	{
                        		String checkID = "checkBox"+Integer.toString(i);
                        		int ch_id = getResources().getIdentifier(checkID, "id", getContext().getPackageName());
                        		if(((CheckBox)mView.findViewById(ch_id)).isChecked())
                        		{
                        			avail_hours = avail_hours+i+" ";
                        			//Log.i("checked", checkID);
                        		} 
                        	}
                        	
                        	ArrayList<HashMap<String, String>> avail_list = repo_inst.getAvailabilityList();
                        	for(int a=0; a<avail_list.size(); a++)
                        		if(avail_list.get(a).get("date").equals(curD)) repo_inst.deleteAvailability(curD);
                        	
                        	repo_inst.insertAvailability_NEW(curD, avail_hours);
                        	//MainActivity.setAvailView(MainActivity.currentDate);
                        	String[] av_hrs = avail_hours.split(" ");
                        	for(int x=1; x <= 24; x++)
                			{
                				//Log.i("hour", avail_hours[j]);
                				//String hourID = "hour_"+avail_hours[j];
            					String hourID = "hour_"+x;
                				int h_id = getResources().getIdentifier(hourID, "id", getContext().getPackageName());
                				//Log.i("hour id", Integer.toString(h_id));
                				View hour =(View)getActivity().findViewById(h_id);
                				hour.setBackgroundColor(0xFFDEDEDE);
                				
                				if(av_hrs!=null){
                					for(int j=1; j < av_hrs.length; j++)
                        			{
                        				if(av_hrs[j].equals(Integer.toString(x))) hour.setBackgroundColor(0xFFFFFFFF);
                        			
                        			}
                				}
                				
                			}
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
            
            mDrawerLayout.closeDrawers();
        }
        
        if(position==3)
        {
        	Intent myIntent = new Intent(this.getActivity(), MyNotes.class);
            // myIntent.putExtra("key", value); //Optional parameters
        	this.getActivity().startActivity(myIntent);
        }
        
        if(position==4)
        {
        	//plan button
        	ArrayList<HashMap<String, String>> avail_list = repo_inst.getAvailabilityList();
            String avail = null;
            String[] avail_hours = null;
            ArrayList<Integer> availHours = new ArrayList<Integer>();
            for(int i=0; i < avail_list.size(); i++)
            {
            	if(avail_list.get(i).get("date").equals(MainActivity.currentDate.getText().toString())) 
            		{
            			avail=avail_list.get(i).get("availability");
            			avail_hours = avail.split(" ");
            			
            			for(int k=1; k< avail_hours.length; k++)
            			{
            				availHours.add(Integer.parseInt(avail_hours[k]));
            			}
            		}
            }
            
            ArrayList<HashMap<String, String>> todo_items = repo_inst.getToDoList();
            ArrayList<String> planned_ids = new ArrayList<String>();
            
            for(int j=0;j < todo_items.size();j++)
            {
            	int task_dur = Integer.parseInt(todo_items.get(j).get("taskDuration"));
            	int n=0;
                while(n< availHours.size() && n!=-1 && n+task_dur<=availHours.size()){
                	int check_time = availHours.get(n+(task_dur-1))-availHours.get(n)+1;
                	if(check_time==task_dur) 
                	{
                		double topMargin = 60*(availHours.get(n)-1)+1;
                		double height = 59*task_dur;
                		String message = todo_items.get(j).get("taskName");
                		mLayout = (RelativeLayout)this.getActivity().findViewById(R.id.left_event_column);
                		//int eventIndex = mLayout.getChildCount();
                		TextView mTaskView = new TextView(this.getActivity());
                		RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                		lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                
                		lParam.topMargin = (int) (topMargin * MainActivity.scr_dens);
                		lParam.leftMargin = 40;
                		mTaskView.setLayoutParams(lParam);
                		mTaskView.setPadding(24, 0, 24, 0);
                		mTaskView.setHeight((int) (height * MainActivity.scr_dens));
                		mTaskView.setGravity(0x11);
                		mTaskView.setTextColor(Color.parseColor("#ffffff"));
                		mTaskView.setText(message);
                		mTaskView.setBackgroundColor(Color.parseColor("#3fb57d"));
                		if(mLayout!=null) mLayout.addView(mTaskView, MainActivity.eventIndex - 1);
                        
                		repo_inst.insertPlan(MainActivity.currentDate.getText().toString(), Integer.toString(availHours.get(n)), Integer.toString(task_dur), todo_items.get(j).get("taskName"));
                        planned_ids.add(todo_items.get(j).get("id3"));
                		
                        String file_data = "\n Planned on "+MainActivity.currentDate.getText().toString()+": "+todo_items.get(j).get("taskName")+", "+Integer.toString(task_dur);
                        writeToFile(file_data, this.getActivity());
                        
                		for(int m=n; m < n+task_dur;m++)
                		{
                			Log.i("planned", todo_items.get(j).get("taskName")+Integer.toString(availHours.get(n)));
                			availHours.remove(n);
                		}
                		
                		//repo_inst.deleteToDo(todo_items.get(j).get("id3"));
                		
                		n=-1;
                	}else n++;
                	
                }
            }
            
            for(int i=0; i<planned_ids.size();i++) repo_inst.deleteToDo(planned_ids.get(i));
            mDrawerLayout.closeDrawers();
        }
        
        if(position==5)
        {
        	Intent myIntent = new Intent(this.getActivity(), Questionnaire.class);
            myIntent.putExtra("curDate", MainActivity.currentDate.getText().toString()); 
        	this.getActivity().startActivity(myIntent);
        }
        
    }

    
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("study_data.txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } 
    }
    
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }
}
