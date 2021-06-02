package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RatingActivity extends AppCompatActivity {
    Button getMoviebtn ,chooseMovie;
    TextView movieTitle,movieDes;
    EditText getMovieName,getChooseMovie;
    ImageView apiImage;
    String picture_url;
    Bitmap movie_poster;
    String movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        getMoviebtn = findViewById(R.id.findBtn);
        getMovieName = findViewById(R.id.getMovieApi);
        movieTitle = findViewById(R.id.titleOfMovie);
        movieDes = findViewById(R.id.descOfMovie);
        apiImage = findViewById(R.id.imgviewImbd);
        chooseMovie = findViewById(R.id.chooseImageBtn);
        getChooseMovie = findViewById(R.id.chooseMovie);

        getMoviebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getMovieName.getText().toString();
                new Thread(new ImbdMovieRatingApi(name)).start();
            }
        });

        chooseMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chooseName = getChooseMovie.getText().toString();
                Intent imageAct = new Intent(RatingActivity.this,ImageActivity.class);
                imageAct.putExtra("imageUrl",chooseName);
                startActivity(imageAct);
            }
        });


    }

    class ImbdMovieRatingApi implements Runnable {

        String requested_movie;

        ImbdMovieRatingApi(String name) {
            requested_movie = name;
        }

        @Override
        public void run() {

            StringBuilder stb = new StringBuilder("");
            StringBuilder stb2 = new StringBuilder("");
            StringBuilder moviesdes = new StringBuilder("");
            StringBuilder moviestit = new StringBuilder("");
            StringBuilder movierating = new StringBuilder("");
            StringBuilder movieId = new StringBuilder("");

            try {
                // Make the connection and get the input stream
                URL url = new URL("https://imdb-api.com/en/API/SearchMovie/k_9v3ck035/" + requested_movie.trim());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));


                //read all lines in StringBuilder
                String line;
                while ((line = bf.readLine()) != null) {
                    stb.append(line);
                }


                //json prahase
                JSONObject json = new JSONObject(stb.toString());
                JSONArray jsonArray = json.getJSONArray("results");


                //find the movie name and put it moviestit
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_drink = jsonArray.getJSONObject(i);
                    String movie_name = json_drink.getString("title");


                    moviestit.append(movie_name + "\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    //set all movies in text box
                    movieDes.setText(moviestit);

                }
            });
        }

    }

}