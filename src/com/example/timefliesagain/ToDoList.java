package com.example.timefliesagain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.ListActivity;


public class ToDoList extends ListActivity {
    private static final String TAG = ToDoList.class.getSimpleName();
    private Button back_button;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_todo); 
        
        ArrayList<String> content = new ArrayList<String>(mListContent.length);
        for (int i=0; i < mListContent.length; i++) {
        	content.add(mListContent[i]);
        }
        
        setListAdapter(new DragNDropAdapter(this, new int[]{R.layout.dragitem}, new int[]{R.id.TextView01}, content));//new DragNDropAdapter(this,content)
        ListView listView = getListView();
        
        if (listView instanceof DragNDropListView) {
        	((DragNDropListView) listView).setDropListener(mDropListener);
        	((DragNDropListView) listView).setRemoveListener(mRemoveListener);
        	((DragNDropListView) listView).setDragListener(mDragListener);
        }
       
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
        
        private static String[] mListContent={"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7"};

}