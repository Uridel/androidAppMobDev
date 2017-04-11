package com.example.kevin.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 29-3-2017.
 */

public class EditShowActivity extends AppCompatActivity {
    private Show show;
    private DataSource datasource;
    private EditText editText;
    private int mPosInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Init local variables
        editText = (EditText) findViewById(R.id.details_updateText);

        editText.setText(getIntent().getStringExtra(MainActivity.INTENT_DETAIL_REMINDER_TEXT));

        //Obtain the parameters provided by MainActivity
        mPosInArray =  getIntent().getIntExtra(MainActivity.INTENT_DETAIL_ROW_NUMBER, -1);
        //If no "position in list" can be found, the default value is -1. This could be used to recognize an issue.
      //  datasource = new DataSource(this);
        //long showId =  getIntent().getLongExtra(MainActivity.EXTRA_SHOW_ID, -1);
       // show = datasource.getShow(showId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return entered data to MainActivity (if not empty, else throw a snackbar message
                String updatedReminderText = editText.getText().toString();
                if (updatedReminderText.length() != 0) {
                    //Prepare the return parameter and return
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.INTENT_DETAIL_ROW_NUMBER, mPosInArray);
                    resultIntent.putExtra(MainActivity.INTENT_DETAIL_REMINDER_TEXT,editText.getText().toString());
                  //  datasource.updateShow(show);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Snackbar.make(view, "Enter some data", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}