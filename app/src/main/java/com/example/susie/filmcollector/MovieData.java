package com.example.susie.filmcollector;

// this class is used to hold data retrieved from the movie table
public class MovieData {

    public int id;
    public int actorId;
    public String movieTitle;
    public String movieRating;
    public int movieLength;

    public MovieData(int id, int actorId, String movieTitle, String movieRating, int movieLength) {
        this.id = id;
        this.actorId = actorId;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieLength = movieLength;
    }

    public MovieData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }
}


