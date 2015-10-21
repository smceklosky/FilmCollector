package com.example.susie.filmcollector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MovieAdaptor extends ArrayAdapter<MovieData> {

    MovieData movie;

    // create an adapter for the Movie Search Results activity.  the adapter should contain
    // the movies stored in the movie table and it should be formatted like
    // the movie_row resource
    public MovieAdaptor(Context context, MovieData[] movies) {
        super(context, R.layout.movie_row, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View movieView = inflater.inflate(R.layout.movie_row, parent, false);

        // get movie data at the current position
        movie = getItem(position);

        // get references to the movie title, rating, and length TextViews in the movie_row resource
        TextView movieTitle = (TextView) movieView.findViewById(R.id.tvMovieTitle);
        TextView movieRating = (TextView) movieView.findViewById(R.id.tvMovieRating);
        TextView movieLength = (TextView) movieView.findViewById(R.id.tvMovieLength);

        // set the text in the TextViews based on the data in the movie
        movieTitle.setText(movie.getMovieTitle());
        movieRating.setText(movie.getMovieRating());
        movieLength.setText(Integer.toString(movie.getMovieLength()));

        return movieView;
    }
}
