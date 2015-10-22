package com.example.susie.filmcollector;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

// this class probably should have been named ActorSearchResults because it displays
// the list of actors in the film collector database
public class Movie extends ActionBarActivity {

    private ActorData[] actorData;
    private ActorData actor;
    DBHandler dbHandler;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // get a reference to the film collector database
        dbHandler = new DBHandler(this, null, null, 2);

        // this String will be displayed when no actors have been
        // populated in the database
        String[] noActors = {"No actors were found!"};

        // get the actors populated in the actor table and store their data in an array
        actorData = dbHandler.getActors();

        // if the array is not null, then set up the custom adapter that displays the
        // actor data, else set up a simple adapter that displays the noActors String
        if (actorData != null) {
            adapter = new CustomAdapter(this, actorData);
        } else {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noActors);
        }

        // get a reference to the ListView
        ListView listView = (ListView) findViewById(R.id.lvActors);

        // associate the adapter with the ListView
        listView.setAdapter(adapter);

        // set uup a listener on the ListView so that when an item is selected,
        // it's data is sent to the Add Movie activity and the Add Movie activity
        // is started
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        actor = (ActorData) parent.getItemAtPosition(position);

                        Intent i = new Intent(Movie.this, AddMovie.class);
                        i.putExtra("actorId", actor.getId());
                        i.putExtra("actorFirstName", actor.getFirstName());
                        i.putExtra("actorLastName", actor.getLastName());

                        startActivity(i);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;

        // get the id of the option that's selected in the overflow menu
        switch(item.getItemId()){

            // this code will get executed if the Add Actor option is selected
            case R.id.menu_add_actor :
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                // open the Add Actor activity
                i = new Intent(this, Actor.class);
                startActivity(i);

                return true;

            // this code will get executed if the Add Movie option is selected
            case R.id.menu_add_movie :
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                // open the Add Movie activity
                i = new Intent(this, AddMovie.class);
                startActivity(i);

                return true;

            // this code will get executed if the Get Movies option is selected
            case R.id.menu_get_movies :
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                // open the Get Movies activity
                i = new Intent(this, MovieSearch.class);
                startActivity(i);

                return true;

            default :
                return super.onOptionsItemSelected(item);
        }
    }
}
