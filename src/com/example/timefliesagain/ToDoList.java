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
    private Button save_changes;
    private Button add_task;
    private EditText input_task;
    private EditText input_duration;
    private PlannerRepo repo_inst;
    private ArrayList<String> content;
    private ArrayList<HashMap<String, String>> todo_items;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.activity_todo); 
        
        /////////////
        
        repo_inst = new PlannerRepo(this);
        todo_items = repo_inst.getToDoList();
        
        content = new ArrayList<String>();
        for (int i=0; i < todo_items.size(); i++) {
        	Log.v("dbitemID", todo_items.get(i).get("id3"));
        	content.add(todo_items.get(i).get("taskName")+"-"+todo_items.get(i).get("taskDuration"));
        }
        
        /////////////
        
        setListAdapter(new DragNDropAdapter(this, new int[]{R.layout.dragitem}, new int[]{R.id.TextView01}, content));//new DragNDropAdapter(this,content)
        ListView listView = getListView();
        
        if (listView instanceof DragNDropListView) {
        	((DragNDropListView) listView).setDropListener(mDropListener);
        	((DragNDropListView) listView).setRemoveListener(mRemoveListener);
        	((DragNDropListView) listView).setDragListener(mDragListener);
        }
        
        registerForContextMenu(listView);
        
        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                // TODO Auto-generated method stub

                Log.v("long clicked","pos: " + pos);

                return true;
            }
        }); */
       
        back_button = (Button)findViewById(R.id.back_button);
        
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent myIntent = new Intent(ToDoList.this, MainActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
            	ToDoList.this.startActivity(myIntent);
            }
        });
        
        save_changes = (Button)findViewById(R.id.save_changes);
        
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	String get_all;
            	String[] parts; 
            	String get_task;
            	String get_dur;
            	repo_inst.deleteToDoALL();
            	
            	for (int i=0; i < content.size(); i++) {
                	get_all=content.get(i);
                	parts = get_all.split("-");
                	get_task = parts[0];
                	get_dur = parts[1];
                	Log.v("changes", get_task+get_dur);
                	repo_inst.insertToDo_NEW(get_task, get_dur);
                	//repo_inst.updateToDo_NEW(i+1, get_task, get_dur);
                }
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
                content.add(task+"-"+dur);
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
        
        
        ///////////////
        
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        	menu.setHeaderTitle("Select The Action");
        	menu.add(0, 0, 0, "Edit");
        	menu.add(0, 1, 0, "Delete");  
        	super.onCreateContextMenu(menu, v, menuInfo);
              
        }
        
        @Override
        public boolean onContextItemSelected(MenuItem item) {
          AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
          int menuItemIndex = item.getItemId();
          int list_index = info.position;
          
          if(menuItemIndex==0)
          {
        	  //Log.v("db_item", String.valueOf(todo_items.get(i).get("id3")));
          }
          if(menuItemIndex==1)
          {
        	  //String DBitem_index;
        	  String get_all = content.get(list_index);
        	  String[] split = get_all.split("-");
        	  String get_task = split[0];
        	  Log.v("listitem", String.valueOf(get_task));
        	  for (int i=0; i < todo_items.size(); i++) {
        		  Log.v("db_item", String.valueOf(todo_items.get(i).get("taskName")));
                  
        		  if(get_task.equals(todo_items.get(i).get("taskName")))
        		  {
        			  Log.v("db_item", String.valueOf(todo_items.get(i).get("id3")));
        			  repo_inst.deleteToDo(todo_items.get(i).get("id3"));
        		  }
        	  }
        	  
        	  
        	  
        	  content.remove(list_index);
              setListAdapter(new DragNDropAdapter(ToDoList.this, new int[]{R.layout.dragitem}, new int[]{R.id.TextView01}, content));

          }
          
          return true;
        }

}