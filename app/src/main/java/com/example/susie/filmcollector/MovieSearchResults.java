package com.example.susie.filmcollector;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MovieSearchResults extends ActionBarActivity {

    private MovieData[] movieData;
    DBHandler dbHandler;
    String fn;
    String ln;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_results);

        // get a reference to the film collector database
        dbHandler = new DBHandler(this, null, null, 2);

        // this String will be displayed when no movies have been
        // populated in the database for the input actor
        String[] noMovies = {"No movies found!"};

        Bundle actorData = getIntent().getExtras();
        if(actorData == null)
            return;

        // get the actor first and last names sent from the Movie Search activity
        fn = actorData.getString("actorFirstName");
        ln = actorData.getString("actorLastName");

        // get the movies populated in the movie table and store their data in an array
        movieData = dbHandler.getMovies(fn, ln);

        // if the array is not null, then set up the custom adapter that displays the
        // movie data, else set up a simple adapter that displays the noMovies String
        if (movieData != null) {
            adapter = new MovieAdaptor(this, movieData);
        } else {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noMovies);
        }

        // get a reference to the ListView
        ListView listView = (ListView) findViewById(R.id.lvMovieSearchResults);

        // associate the adapter with the ListView
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_search_results, menu);
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
