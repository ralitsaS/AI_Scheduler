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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;

public class StudyData extends Activity {
	private Button back_button;
	private Button copyclip;
	private TextView data;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.activity_data); 
       
       
        back_button = (Button)findViewById(R.id.go_back);
        
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent myIntent = new Intent(StudyData.this, MainActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
            	StudyData.this.startActivity(myIntent);
            }
        });
        
        String ret = "";
        data = (TextView)findViewById(R.id.data_text);
        
        try {
            InputStream inputStream = this.openFileInput("study_data.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                    stringBuilder.append("\n\r");
                }

                inputStream.close();
                ret = stringBuilder.toString();
                data.setText(ret);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        
        copyclip = (Button)findViewById(R.id.clipboard);
        
        copyclip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
                ClipData clip = ClipData.newPlainText("study data", data.getText().toString());
                clipboard.setPrimaryClip(clip);
                
                Toast.makeText(StudyData.this, "Data copied to clipboard.", Toast.LENGTH_SHORT).show();
            }
        });
        
        
    }
	
}