package com.example.susie.filmcollector;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddMovie extends ActionBarActivity {

    int actorId;
    Spinner spMovieRating;
    static final String[] ratings = {"R", "PG", "PG-13", "G"};
    String actorFirstName;
    String actorLastName;
    String movieRating;
    String movieTitle;
    int movieLength;
    EditText etMovieTitle;
    EditText etMovieLength;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // get a reference to the film collector database
        dbHandler = new DBHandler(this, null, null, 2);

        // get a reference to the actor name EditText
        final EditText etActorName = (EditText) findViewById(R.id.etActorName);

        // enable the actor name EditText
        etActorName.setEnabled(true);

        // get the data sent from the Movie activity.  this activity wasn't named correctly.
        // it should have been named ActorResults or something like that.
        Bundle actorData = getIntent().getExtras();
        if(actorData != null) {

            // store the data in variables
            actorFirstName = actorData.getString("actorFirstName");
            actorLastName = actorData.getString("actorLastName");
            actorId = actorData.getInt("actorId");

            // set some of the data in the actor name EditText
            etActorName.setText(actorFirstName + " " + actorLastName);
        }

        // get a reference to the movie title EditText
        etMovieTitle = (EditText) findViewById(R.id.etMovieTitle);

        // get a reference to the movie length EditText
        etMovieLength = (EditText) findViewById(R.id.etMovieLength);

        // get a reference to the movie rating Spinner
        spMovieRating = (Spinner)findViewById (R.id.spMovingRating);

        // create an adapter for the movie rating Spinner.  the adapter should contain
        // the strings in the ratings array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMovie.this,
                android.R.layout.simple_spinner_item, ratings);

        // format the adapter - simple_spinner_dropdown_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // associate the adapter with the movie rating Spinner.
        spMovieRating.setAdapter(adapter);

        // set uup a listener on the movie rating Spinner so that when an item is selected,
        // it's stored in a variable.
        spMovieRating.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        movieRating = String.valueOf(parent.getItemAtPosition(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(AddMovie.this, "Please select a movie rating!", Toast.LENGTH_LONG).show();
                    }
                }
        );

        // set uup a listener on the actor name EditText so that when it's clicked,
        // the Movie activity is opened
        etActorName.setOnClickListener(
            new EditText.OnClickListener(){
                public void onClick(View view){
                    Intent i = new Intent(AddMovie.this, Movie.class);
                    startActivity(i);
                }
            }
        );
    }

    // this function gets called when the Add button is pushed
    public void addMovie (View view){

        // get the text entered in the movie title and length EditTexts
        movieTitle = etMovieTitle.getText().toString();
        String length = etMovieLength.getText().toString();

        // if text wasn't entered, display an error
        if (movieTitle.trim().equals("") || length.trim().equals("") || movieRating.trim().equals("")) {

            Toast.makeText(this, "Please enter a movie title, rating, and length!", Toast.LENGTH_LONG).show();

        } else {

            // check to see if movie already exists in the database
            int numMovies = dbHandler.checkMovie(actorFirstName, actorLastName, movieTitle);

            // if movie doesn't exist, add the movie and display a message
            if (numMovies == 0) {
                movieLength = Integer.parseInt(length);
                dbHandler.addMovie(actorId, movieTitle, movieRating, movieLength);
                String dbResult = dbHandler.movieToString();
                Toast.makeText(this, dbResult + " added!", Toast.LENGTH_LONG).show();
            } else {
                // if movie already exists, display a message
                Toast.makeText(this, movieTitle + " already exists!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_movie, menu);
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
