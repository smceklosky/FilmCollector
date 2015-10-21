package com.example.susie.filmcollector;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MovieSearch extends ActionBarActivity {

    EditText etFirstName;
    EditText etLastName;
    String fn;
    String ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        // get a reference to the first and last name EditTexts
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
    }

    // this method gets called when the add button is pushed
    public void getMovies (View view){

        // get the text entered in the first and last name EditTexts
        fn = etFirstName.getText().toString();
        ln = etLastName.getText().toString();

        // if text wasn't entered, display an error
        if (fn.trim().equals("") || ln.trim().equals("")) {

            Toast.makeText(this, "Please enter a first and last name!", Toast.LENGTH_LONG).show();

        } else {

            // send the text entered in the first and last name EditTexts to the
            // Movie Search Results activity and start the activity
            Intent i = new Intent(this, MovieSearchResults.class);
            i.putExtra("actorFirstName", fn);
            i.putExtra("actorLastName", ln);
            startActivity(i);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_search, menu);
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
