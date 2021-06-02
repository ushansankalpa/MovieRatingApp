package com.example.movierating;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RgisterMovies extends AppCompatActivity {

    EditText title,director,year,actors,rating,review;
    Button save;
    //Context context;
    MovieDB movieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister_movies);


        title =findViewById(R.id.titleText);
        year =findViewById(R.id.yearText);
        director =findViewById(R.id.directorText);
        actors =findViewById(R.id.actorText);
        rating =findViewById(R.id.ratingText);
        review =findViewById(R.id.reviewText);
        save =findViewById(R.id.btn_save);
        movieDB = new MovieDB(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title_mov="";
                int year_mov=0;
                String director_mov="";
                String actor_mov="";
                int rating_mov=0;
                String review_mov="";
                Boolean isFav = false;

                try {
                    //get all value from, the edit text
                    title_mov = title.getText().toString();
                    year_mov = Integer.parseInt(year.getText().toString());
                    director_mov = director.getText().toString();
                    actor_mov = actors.getText().toString();
                    rating_mov = Integer.parseInt(rating.getText().toString());
                    review_mov = review.getText().toString();


                    Boolean checkInsertData =false;
                    //check year and rating are validate and and value pass to the database
                    if ((year_mov<2022 && year_mov>1895) && (rating_mov<=10 && rating_mov>0)){
                        Movies movi  = new Movies(title_mov,year_mov,director_mov,actor_mov,rating_mov,review_mov, isFav);
                        checkInsertData = movieDB.insertMovieData(movi);
                    }else {
                        yearAndRatingMsg();
                    }







                    if(checkInsertData==true)
                        Toast.makeText(RgisterMovies.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RgisterMovies.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    correctEndMessage();
                }






            }
        });




    }

    //if some edit text are isEmpty sending Alert box
    public void correctEndMessage(){
        AlertDialog.Builder builder_alertBox = new AlertDialog.Builder(this);
        builder_alertBox.setTitle("Empty");
        builder_alertBox.setMessage("Try again");

        builder_alertBox.setCancelable(true);

        builder_alertBox.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertBox = builder_alertBox.create();

        alertBox.show();


    }

    //year and rating are incorrect send this massage
    public void yearAndRatingMsg(){
        AlertDialog.Builder builder_alertBox = new AlertDialog.Builder(this);
        builder_alertBox.setTitle("Your year or rating are Incorrect");
        builder_alertBox.setMessage("2022 > year > 1895 ||||| 1 < Rating < 10");

        builder_alertBox.setCancelable(true);

        builder_alertBox.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertBox = builder_alertBox.create();

        alertBox.show();


    }
}