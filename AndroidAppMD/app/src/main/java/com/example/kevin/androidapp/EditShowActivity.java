package com.example.kevin.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

       datasource = new DataSource(this);
       long showId = (long) getIntent().getLongExtra(MainActivity.EXTRA_SHOW_ID, -1);
        show = datasource.getShow(showId);

        TextView textView = (TextView) findViewById(R.id.details_textview);
        textView.setText(show.getShow());

        editText = (EditText) findViewById(R.id.details_updateText);

        Button updateButton = (Button) findViewById(R.id.details_updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.setShows(editText.getText().toString());
                datasource.updateShow(show);
                Toast.makeText(EditShowActivity.this, "Assignment Updated", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

        });
    }
}