package com.example.kevin.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private ListView listView;
    public static final String EXTRA_SHOW = "extraShow";

    public static final String EXTRA_SHOW_ID = "extraShowId";

    private ArrayAdapter<Show> showArrayAdapter;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.main_list);
        TextView emptyView = (TextView) findViewById(R.id.main_list_empty);
            listView.setEmptyView(emptyView);


        datasource = new DataSource(this);
        List<Show> shows = datasource.getAllShows();
        showArrayAdapter = new ArrayAdapter<Show>(this, android.R.layout.simple_list_item_1, shows);
            listView.setAdapter(showArrayAdapter);

        registerForContextMenu(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, EditShowActivity.class);
                intent.putExtra(MainActivity.EXTRA_SHOW_ID, (long) showArrayAdapter.getItem(position).getId());
                startActivityForResult(intent, 2);            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.taskView);
            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CreateShowActivity.class), 1);
            }
        });
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

    public void updateShowListView() {
        List<Show> shows = datasource.getAllShows();
        showArrayAdapter = new ArrayAdapter<Show>(this, android.R.layout.simple_list_item_1, shows);
        listView.setAdapter(showArrayAdapter);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Handle data
                long showId = data.getLongExtra(EXTRA_SHOW_ID, -1);
                if (showId != -1) {
                    Show show = datasource.getShow(showId);
                    showArrayAdapter.add(show);
                    updateShowListView();

                }



            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateShowListView();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            Toast.makeText(getApplicationContext(), "Shows deleted", Toast.LENGTH_LONG).show();
            Show show = showArrayAdapter.getItem(info.position);
            showArrayAdapter.remove(show);
            datasource.deleteShows(show);
        } else {
            return false;
        }
        return true;
    }



}

