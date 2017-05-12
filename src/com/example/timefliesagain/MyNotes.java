package com.example.timefliesagain;

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
import android.app.ListActivity;
import android.content.Intent;

public class MyNotes extends ListActivity {
	private Button back_button;
	private Button add_note;
	private EditText input_note;
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
        input_note = (EditText)findViewById(R.id.editText_mult);
        
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = input_note.getText().toString();
                
                repo_inst.insertNote(note);
                db_notes.add(note);
                listView.setAdapter(adapter);
            }
        });
        
        
        
    }
	
}
