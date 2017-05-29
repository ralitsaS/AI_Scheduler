package com.example.timefliesagain;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;

public class Questionnaire extends ListActivity {
	private Button back_button;
	private Button set_tasks;
	private Button open_data;
	private PlannerRepo repo_inst;
	private ArrayList<HashMap<String, String>> planned_tasks ;
	private ListView listView;
	private TextView tip;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.activity_quest); 
        final String cur_date = getIntent().getStringExtra("curDate");
        repo_inst = new PlannerRepo(this);
        planned_tasks = repo_inst.getPlannedList();
        ArrayList<String> task_desc = new ArrayList<String>();
        
        for(int i=0; i<planned_tasks.size();i++)
        {
        	if(cur_date.equals(planned_tasks.get(i).get("day")))
        			task_desc.add(planned_tasks.get(i).get("message"));
        			
        }
        
        listView = getListView();
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.list_quest_item, R.id.TextView21, task_desc);
        listView.setAdapter(adapter);
        
        
        tip = (TextView)findViewById(R.id.notes_tip);
        
        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent myIntent = new Intent(Questionnaire.this, MyNotes.class);
                // myIntent.putExtra("key", value); //Optional parameters
            	Questionnaire.this.startActivity(myIntent);
            }
        });
       
        back_button = (Button)findViewById(R.id.bb);
        
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent myIntent = new Intent(Questionnaire.this, MainActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
            	Questionnaire.this.startActivity(myIntent);
            }
        });
        
       
        
        set_tasks = (Button)findViewById(R.id.set);
        
        set_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	for (int i = 0; i < listView.getCount(); i++) {
            		Log.i("list view count", Integer.toString(listView.getCount()));
            		
            		if(listView.getChildAt(i)!=null){
                    EditText  et = (EditText ) listView.getChildAt(i).findViewById(R.id.quest_left);
                    if (!et.getText().toString().equals("") && !et.getText().toString().equals("0")) {
                    	TextView  task = (TextView ) listView.getChildAt(i).findViewById(R.id.TextView21);
                    	Log.i("task", task.getText().toString());
                    	Log.i("timeleft", et.getText().toString());
                    	
                    	repo_inst.insertToDo_NEW(task.getText().toString(), et.getText().toString());
                    	String file_data = "\n Put back on "+cur_date+": "+task.getText().toString()+", "+et.getText().toString();
                        writeToFile(file_data, Questionnaire.this);
                        
                        Toast.makeText(Questionnaire.this, "To-Do list updated.", Toast.LENGTH_SHORT).show();
                       }
            		}else Log.i("problem", "child is null");
                    }
            }
        });
        
        open_data = (Button)findViewById(R.id.data);
        
        open_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent myIntent = new Intent(Questionnaire.this, StudyData.class);
                // myIntent.putExtra("key", value); //Optional parameters
            	Questionnaire.this.startActivity(myIntent);
            }
        });
        
        
        
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
	
}
