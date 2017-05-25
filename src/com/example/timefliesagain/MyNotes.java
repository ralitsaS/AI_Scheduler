package com.example.timefliesagain;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;

public class MyNotes extends ListActivity {
	private Button back_button;
	private Button add_note;
	private EditText input_prob;
	private EditText input_fix;
	private PlannerRepo repo_inst;
	private ArrayList<String> db_notes ;
	private ListView listView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.activity_notes); 
        
        repo_inst = new PlannerRepo(this);
        db_notes = repo_inst.getNotesList();
        
        listView = getListView();
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.listitem, R.id.TextView11, db_notes);
        listView.setAdapter(adapter);
        
        
       
       
        back_button = (Button)findViewById(R.id.back_b);
        
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent myIntent = new Intent(MyNotes.this, MainActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
            	MyNotes.this.startActivity(myIntent);
            }
        });
        
       
        
        add_note = (Button)findViewById(R.id.add_note);
        input_prob = (EditText)findViewById(R.id.mult_prob);
        input_fix = (EditText)findViewById(R.id.mult_fix);
        
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prob = input_prob.getText().toString();
                String fix = input_fix.getText().toString();
                
                String note = "Problem: "+prob+" \n Solution: "+fix;
                repo_inst.insertNote(note);
                db_notes.add(note);
                listView.setAdapter(adapter);
                
                String file_data = "\n Note added on "+MainActivity.currentDate.getText().toString()+": "+note;
                writeToFile(file_data, MyNotes.this);
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
