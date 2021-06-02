package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //Button movieReg,movieView;
    ImageView movieRegBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //movieReg =findViewById(R.id.register_btn);
        //movieView=findViewById(R.id.btn_view);
        movieRegBtn = findViewById(R.id.regMovie);


    }

    //click register movie
    public void moviesRegister(View view) {
        Intent Movie_Reg = new Intent(MainActivity.this,RgisterMovies.class);
        startActivity(Movie_Reg);
    }

    //click display movies
    public void displayMovies(View view) {
        Intent Movie_Dis = new Intent(MainActivity.this,ViewActivity.class);
        startActivity(Movie_Dis);
    }

    //update movies
    public void updateMovieDetails(View view) {
        Intent Movie_Edit = new Intent(MainActivity.this,EditeActivity.class);
        startActivity(Movie_Edit);
    }

    //favourite movies
    public void favouriteMovie(View view) {
        Intent Movie_fav = new Intent(MainActivity.this,FavouriteActivity.class);
        startActivity(Movie_fav);
    }

    //search movies
    public void searchOnMovies(View view) {
        Intent Movie_search = new Intent(MainActivity.this,SearchActivity.class);
        startActivity(Movie_search);
    }

    //Imdb rating movies
    public void ImbdRatingInMovies(View view) {
        Intent Movie_rating = new Intent(MainActivity.this,RatingActivity.class);
        startActivity(Movie_rating);
    }
}