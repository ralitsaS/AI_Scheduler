package com.example.timefliesagain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import android.widget.ListView;
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
                            
                            //CHANGE THIS LATER
                            
                            repo_inst.insertAppointment_NEW(start, end, "oh jeez rick");
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
                        	
                        	repo_inst.insertAvailability_NEW(curD, avail_hours);
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
        
        if(position==3)
        {
        	Intent myIntent = new Intent(this.getActivity(), MyNotes.class);
            // myIntent.putExtra("key", value); //Optional parameters
        	this.getActivity().startActivity(myIntent);
        }
        
        if(position==4)
        {
        	//plan button
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
