package com.example.kevin.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Show> shows;
    private ShowsAdapter showAdapter;
    private RecyclerView recyclerView;
    private DataSource datasource;

    public static final String INTENT_DETAIL_ROW_NUMBER = "Row number";
    public static final String INTENT_DETAIL_REMINDER_TEXT = "Reminder text";
    public static final int REQUESTCODE = 2;
    public static final String EXTRA_SHOW_ID = "extraShowId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new DataSource(this);
        datasource.open();

        recyclerView = (RecyclerView) findViewById(R.id.main_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setAdapter(showAdapter);

        updateUI();

        //registerForContextMenu(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.taskView);
            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CreateShowActivity.class), 1);
            }
        });
    }

    private void updateUI() {
        shows = datasource.getAllShows();
        if (showAdapter == null) {
            showAdapter = new ShowsAdapter(this, shows); // the error
            recyclerView.setAdapter(showAdapter);
        } else {
            showAdapter.updateList(shows);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Handle data
                long showId = data.getLongExtra(EXTRA_SHOW_ID, -1);
                if (showId != -1) {
                    Show show = datasource.getShow(showId);
                    shows.add(show);
                    updateUI();

                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateUI();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

