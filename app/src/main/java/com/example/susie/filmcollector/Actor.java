package com.example.susie.filmcollector;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Actor extends ActionBarActivity {

    // declare references
    EditText firstName;
    EditText lastName;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        // get a reference to the first and last name EditTexts
        firstName = (EditText) findViewById(R.id.etFirstName);
        lastName = (EditText) findViewById(R.id.etLastName);

        // get a reference to the film collector database
        dbHandler = new DBHandler(this, null, null, 2);
    }

    // add an actor to the actor table
    public void addActor (View view){

        // get the text entered in the first and last name EditTexts
        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();

        // if text wasn't entered, display an error
        if (fn.trim().equals("") || ln.trim().equals("")) {

            Toast.makeText(this, "Please enter a first and last name!", Toast.LENGTH_LONG).show();

        } else {

            // check to see if actor already exists in the database
            int numActors = dbHandler.checkActor(fn, ln);

            // if actor doesn't exist, add the actor and display a message
            if (numActors == 0) {
                dbHandler.addActor(fn, ln);
                String dbResult = dbHandler.actorToString();
                Toast.makeText(this, dbResult + " added!", Toast.LENGTH_LONG).show();
            } else {
                // if actor already exists, display a message
                Toast.makeText(this, fn + " " + ln + " already exists!", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actor, menu);
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
