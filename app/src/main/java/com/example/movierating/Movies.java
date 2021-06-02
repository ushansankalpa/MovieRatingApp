package com.example.movierating;

import android.os.AsyncTask;

public class Movies  {
    private int movieId;
    private String movieTitle;
    private int movieYear;
    private String movieDirector;
    private String movieActors;
    private int movieRating;
    private String movieReview;
    private Boolean isFavourite;

    public Movies(){

    }

    public Movies(String movieTitle){
        this.movieTitle = movieTitle;
    }


    public Movies(int movieId, String movieTitle, int movieYear, String movieDirector, String movieActors, int movieRating, String movieReview,boolean isFavourite) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
        this.movieActors = movieActors;
        this.movieRating = movieRating;
        this.movieReview = movieReview;
        this.isFavourite = isFavourite;
    }

    public Movies(String movieTitle, int movieYear, String movieDirector, String movieActors, int movieRating, String movieReview,boolean isFavourite) {

        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
        this.movieActors = movieActors;
        this.movieRating = movieRating;
        this.movieReview = movieReview;
        this.isFavourite = isFavourite;
    }


    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String getMovieActors() {
        return movieActors;
    }

    public void setMovieActors(String movieActors) {
        this.movieActors = movieActors;
    }

    public int getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(int movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(String movieReview) {
        this.movieReview = movieReview;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieYear=" + movieYear +
                ", movieDirector='" + movieDirector + '\'' +
                ", movieActors='" + movieActors + '\'' +
                ", movieRating=" + movieRating +
                ", movieReview='" + movieReview + '\'' +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
