package com.example.susie.filmcollector;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private  static final int DATABASE_VERSION = 2;
    private  static final String DATABASE_NAME = "filmcollector.db";
    public static final String TABLE_ACTOR = "actor";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    private ActorData[] actorData;
    public static final String TABLE_MOVIE = "movie";
    public static final String COLUMN_MID = "_id";
    public static final String COLUMN_ACTORID = "actorid";
    public static final String COLUMN_MOVIETITLE = "movietitle";
    public static final String COLUMN_MOVIERATING = "movierating";
    public static final String COLUMN_MOVIELENGTH = "movielength";
    private MovieData[] movieData;

    // create database.
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // create tables.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ACTOR + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FIRSTNAME + " TEXT," +
                COLUMN_LASTNAME + " TEXT" +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_MOVIE + "(" +
                COLUMN_MID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ACTORID + " INTEGER," +
                COLUMN_MOVIETITLE + " TEXT," +
                COLUMN_MOVIERATING + " TEXT," +
                COLUMN_MOVIELENGTH + " INTEGER" +
                ");";
        db.execSQL(query);
    }

    // drop tables and create them again.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }

    // insert a new row into the actor table.
    public void addActor (String firstName, String lastName){
        ContentValues values = new ContentValues();

        // specify which values will be inserted into to which columns
        values.put(COLUMN_FIRSTNAME, firstName);
        values.put(COLUMN_LASTNAME, lastName);

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        // insert the values
        db.insert(TABLE_ACTOR, null, values);

        // close the database
        db.close();
    }

    // return newly added actor as a String
    public String actorToString(){

        String dbString = "";

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTOR + ";";

        // execute the query.  cursor points to a location in the results of the query.
        Cursor c = db.rawQuery(query, null);

        // move to the last row in the results
        c.moveToLast();

        // if the firstname and lastname in the last row aren't null, store them in the return variable.
        if((c.getString(c.getColumnIndex("firstname")) != null) && (c.getString(c.getColumnIndex("lastname")) != null)){
            dbString = c.getString(c.getColumnIndex("firstname")) + " " + c.getString(c.getColumnIndex("lastname"));
        }

        // close the database
        db.close();

        return dbString;
    }

    // return all actors in the actor table.
    public ActorData[] getActors (){

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTOR + ";";

        // execute the query.  cursor points to a location in the results of the query.
        Cursor c = db.rawQuery(query, null);

        // get the number of rows in the results
        int numActors = c.getCount();

        // if there is one or more rows, process the rows
        if (numActors >= 1) {

            // create the array that will store the rows.  make sure the size of the array
            // is the same as the number of rows
            actorData = new ActorData[numActors];

            // initialize the array index to zero
            int i = 0;

            // move to the first row in the results
            c.moveToFirst();

            // while there is still another row to process
            while (!c.isAfterLast()) {
                // if the firstname, lastname, and _id in the current row aren't null, store the data in the current array
                // element
                if ((c.getString(c.getColumnIndex("firstname")) != null) && (c.getString(c.getColumnIndex("lastname")) != null)) {
                    actorData[i] = new ActorData(c.getInt(c.getColumnIndex("_id")),
                            c.getString(c.getColumnIndex("firstname")),
                            c.getString(c.getColumnIndex("lastname")));
                }

                // move to the next row in the results and increment the index in the array
                c.moveToNext();
                i++;
            }
        }

        // close the database
        db.close();

        return actorData;
    }

    // insert a new row into the movie table
    public void addMovie (int actorId, String movieTitle, String movieRating, int movieLength){

        ContentValues values = new ContentValues();

        // specify which values will be inserted into to which columns
        values.put(COLUMN_ACTORID, actorId);
        values.put(COLUMN_MOVIETITLE, movieTitle);
        values.put(COLUMN_MOVIERATING, movieRating);
        values.put(COLUMN_MOVIELENGTH, movieLength);

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        // insert the values
        db.insert(TABLE_MOVIE, null, values);

        // close the database
        db.close();
    }

    // return newly added movie as a String
    public String movieToString(){

        String dbString = "";

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_MOVIE + ";";

        // execute the query.  cursor points to a location in the results of the query.
        Cursor c = db.rawQuery(query, null);

        // move to the last row in the results.
        c.moveToLast();

        // if the movietitle in the last row isn't null, store it in the return variable.
        if((c.getString(c.getColumnIndex("movietitle")) != null)){
            dbString = c.getString(c.getColumnIndex("movietitle"));
        }

        // close the database
        db.close();

        return dbString;
    }

    // check to see if an actor already exists
    public int checkActor(String firstName, String lastName){

        String dbString = "";

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTOR +
                " WHERE " + COLUMN_FIRSTNAME + " = '" + firstName +
                "' AND " + COLUMN_LASTNAME + " = '" + lastName +
                "';";

        // execute the query.  cursor points to a location in the results of the query.
        Cursor c = db.rawQuery(query, null);

        // get the number of rows in the results
        int numActors = c.getCount();

        // close the database
        db.close();

        // return the number of rows
        return numActors;

    }

    // check to see if a movie already exists
    public int checkMovie (String firstName, String lastName, String movieTitle) {

        String dbString = "";

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        int numMovies = 0;
        int movieExists = 0;

        String query = "SELECT * FROM " + TABLE_ACTOR +
                " WHERE " + COLUMN_FIRSTNAME + " = '" + firstName +
                "' AND " + COLUMN_LASTNAME + " = '" + lastName +
                "';";

        // execute the query.  cursor points to a location in the results of the query.
        Cursor c = db.rawQuery(query, null);

        // get the number of rows in the results
        int numActors = c.getCount();

        // if there is one or more rows, process the rows
        if (numActors >= 1) {

            // move to the last row in the results
            c.moveToLast();

            // if the firstname and lastname in the last row aren't null, store the _id in a variable.
            if ((c.getString(c.getColumnIndex("firstname")) != null) && (c.getString(c.getColumnIndex("lastname")) != null)) {
                dbString = c.getString(c.getColumnIndex("_id"));
            }

            query = "SELECT * FROM " + TABLE_MOVIE +
                    " WHERE " + COLUMN_ACTORID + " = " + dbString +
                    ";";

            // execute the query.  cursor points to a location in the results of the query.
            c = db.rawQuery(query, null);

            // get the number of rows in the results
            numMovies = c.getCount();

            // if there is one or more rows, process the rows
            if (numMovies >= 1) {

                // move to the first row in the results
                c.moveToFirst();

                // while there is still another row to process
                while (!c.isAfterLast()) {

                    // if the movietitle in the current row isn't null.
                    if (c.getString(c.getColumnIndex("movietitle")) != null) {

                        // if the movietitle is equal to the input movie title, movie already exists.
                        if (c.getString(c.getColumnIndex("movietitle")).equals(movieTitle)) {
                            movieExists++;
                        }
                    }

                    // move to the next row in the results
                    c.moveToNext();

                }
            }
        }

        // close database and return movie already exists
        db.close();
        return movieExists;
    }

    // return all movies in the movie table
    public MovieData[] getMovies (String firstName, String lastName){

        String dbString = "";

        // get the database.  this function calls onCreate if database versions are different
        // or if the database file doesn't exist on the device.
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTOR +
                " WHERE " + COLUMN_FIRSTNAME + " = '" + firstName +
                "' AND " + COLUMN_LASTNAME + " = '" + lastName +
                "';";

        // execute the query.  cursor points to a location in the results of the query.
        Cursor c = db.rawQuery(query, null);

        // get the number of rows in the results
        int numActors = c.getCount();

        // if there is one or more rows, process the rows
        if (numActors >= 1) {

            // move to the last row in the results
            c.moveToLast();

            // if the firstname and lastname in the last row aren't null, store the _id in a variable.
            if ((c.getString(c.getColumnIndex("firstname")) != null) && (c.getString(c.getColumnIndex("lastname")) != null)) {
                dbString = c.getString(c.getColumnIndex("_id"));
            }

            query = "SELECT * FROM " + TABLE_MOVIE +
                    " WHERE " + COLUMN_ACTORID + " = " + dbString +
                    ";";

            // execute the query.  cursor points to a location in the results of the query.
            c = db.rawQuery(query, null);

            // get the number of rows in the results
            int numMovies = c.getCount();

            // if there is one or more rows, process the rows
            if (numMovies >= 1) {

                // move to the first row in the results
                c.moveToFirst();

                // create the array that will store the rows.  make sure the size of the array
                // is the same as the number of rows
                movieData = new MovieData[numMovies];

                // initialize the array index to zero
                int i = 0;

                // while there is still another row to process
                while (!c.isAfterLast()) {

                    // if the movietitle in the current row isn't null, store the data in the current array
                    // element
                    if (c.getString(c.getColumnIndex("movietitle")) != null) {
                        movieData[i] = new MovieData(c.getInt(c.getColumnIndex("_id")),
                                c.getInt(c.getColumnIndex("actorid")),
                                c.getString(c.getColumnIndex("movietitle")),
                                c.getString(c.getColumnIndex("movierating")),
                                c.getInt(c.getColumnIndex("movielength")));
                    }

                    // move the cursor to the next row
                    c.moveToNext();

                    // increment the array index
                    i++;
                }
            }

        }

        // close the database and return the results
        db.close();

        return movieData;
    }
}
