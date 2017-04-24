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
import android.view.View;
import android.view.Window;
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


public class ToDoList extends ListActivity {
    private static final String TAG = ToDoList.class.getSimpleName();
    private Button back_button;
    private Button add_task;
    private EditText input_task;
    private EditText input_duration;
    private PlannerRepo repo_inst;
    private ArrayList<String> content;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.activity_todo); 
        
        /////////////
        
        repo_inst = new PlannerRepo(this);
        ArrayList<HashMap<String, String>> todo_items = repo_inst.getToDoList();
        
        content = new ArrayList<String>();
        for (int i=0; i < todo_items.size(); i++) {
        	content.add(todo_items.get(i).get("taskName")+" yaay");
        }
        
        /////////////
        
        setListAdapter(new DragNDropAdapter(this, new int[]{R.layout.dragitem}, new int[]{R.id.TextView01}, content));//new DragNDropAdapter(this,content)
        ListView listView = getListView();
        
        if (listView instanceof DragNDropListView) {
        	((DragNDropListView) listView).setDropListener(mDropListener);
        	((DragNDropListView) listView).setRemoveListener(mRemoveListener);
        	((DragNDropListView) listView).setDragListener(mDragListener);
        }
       
        back_button = (Button)findViewById(R.id.back_button);
        
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent myIntent = new Intent(ToDoList.this, MainActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
            	ToDoList.this.startActivity(myIntent);
            }
        });
        
        add_task = (Button)findViewById(R.id.add_task_to_db);
        input_task = (EditText)findViewById(R.id.input_task);
        input_duration = (EditText)findViewById(R.id.input_duration);
        
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = input_task.getText().toString();
                String dur = input_duration.getText().toString();
                
                repo_inst.insertToDo_NEW(task, dur);
                content.add(task);
                setListAdapter(new DragNDropAdapter(ToDoList.this, new int[]{R.layout.dragitem}, new int[]{R.id.TextView01}, content));
            }
        });
        
        
        
    }
    
    private DropListener mDropListener = 
    		new DropListener() {
            public void onDrop(int from, int to) {
            	ListAdapter adapter = getListAdapter();
            	if (adapter instanceof DragNDropAdapter) {
            		((DragNDropAdapter)adapter).onDrop(from, to);
            		getListView().invalidateViews();
            	}
            }
        };
        
        private RemoveListener mRemoveListener =
            new RemoveListener() {
            public void onRemove(int which) {
            	ListAdapter adapter = getListAdapter();
            	if (adapter instanceof DragNDropAdapter) {
            		((DragNDropAdapter)adapter).onRemove(which);
            		getListView().invalidateViews();
            	}
            }
        };
        
        private DragListener mDragListener =
        	new DragListener() {

        	int backgroundColor = 0xe0103010;
        	int defaultBackgroundColor;
        	
    			public void onDrag(int x, int y, ListView listView) {
    				// TODO Auto-generated method stub
    			}

    			public void onStartDrag(View itemView) {
    				itemView.setVisibility(View.INVISIBLE);
    				defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
    				itemView.setBackgroundColor(backgroundColor);
    				ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
    				if (iv != null) iv.setVisibility(View.INVISIBLE);
    			}

    			public void onStopDrag(View itemView) {
    				itemView.setVisibility(View.VISIBLE);
    				itemView.setBackgroundColor(defaultBackgroundColor);
    				ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
    				if (iv != null) iv.setVisibility(View.VISIBLE);
    			}
        	
        };

}