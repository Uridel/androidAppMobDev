package com.example.kevin.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateShowActivity extends AppCompatActivity {
    private EditText showDescription;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showDescription = (EditText)this.findViewById(R.id.showDescription);
        datasource = new DataSource(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long assignmentId = datasource.createShows(showDescription.getText().toString());
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.EXTRA_SHOW_ID, assignmentId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
