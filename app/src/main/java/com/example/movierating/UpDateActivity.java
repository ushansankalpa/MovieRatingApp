package com.example.movierating;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpDateActivity extends AppCompatActivity {

    EditText edTitle,edYear,edDirector,edActor,edRating,edReview;
    Button update;
    MovieDB movieDB;
    RatingBar starRate;
    CheckBox favChecker;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_single_movie);

        Context context = this;
        movieDB = new MovieDB(context);

        Intent intent = getIntent();
        int titeala = intent.getIntExtra("id",0);

        Movies updateMovie = movieDB.getSingleToDo(titeala);



        edTitle = findViewById(R.id.editTitleText);
        edYear = findViewById(R.id.editYearText);
        edDirector = findViewById(R.id.editDirectorText);
        edActor = findViewById(R.id.editActorText);
        edRating = findViewById(R.id.editRatingText);
        edReview = findViewById(R.id.editReviewText);
        update = findViewById(R.id.btn_update);
        starRate = findViewById(R.id.star_rate);
        favChecker = findViewById(R.id.check_fav);



        //set all value to the text boxes
        edTitle.setText(updateMovie.getMovieTitle());
        edYear.setText(updateMovie.getMovieYear()+"");
        edDirector.setText(updateMovie.getMovieDirector());
        edActor.setText(updateMovie.getMovieActors());
        edRating.setText(updateMovie.getMovieRating()+"");
        edReview.setText(updateMovie.getMovieReview());
        System.out.println(updateMovie.getFavourite());
        if (updateMovie.getFavourite()){
            favChecker.setChecked(true);
        }


        starRate.setRating(updateMovie.getMovieRating());


        //then click update btn after save all changes to the database
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String up_tit = edTitle.getText().toString();
                int up_year = Integer.parseInt(edYear.getText().toString());
                String up_dir = edDirector.getText().toString();
                String up_act = edActor.getText().toString();
                int up_rating =(int) (starRate.getRating());
                String up_review = edReview.getText().toString();
                Boolean is_fav= favChecker.isChecked();

                Movies myUpdate = new Movies(titeala,up_tit,up_year,up_dir,up_act,up_rating,up_review,is_fav);
                Boolean checkInsertData = movieDB.updateSingleToDo(myUpdate);
                if(checkInsertData==true){
                    Toast.makeText(UpDateActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,MainActivity.class));
                }else{
                    Toast.makeText(UpDateActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
