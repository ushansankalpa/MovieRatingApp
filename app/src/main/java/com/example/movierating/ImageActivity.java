package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

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

public class ImageActivity extends AppCompatActivity {

    ImageView apiImage;
    String picture_url;
    Bitmap movie_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        apiImage = findViewById(R.id.imgviewImbd);
        Intent intent = getIntent();
        String name = intent.getStringExtra("imageUrl");

        new Thread(new ImageActivity.ImbdMovieRatingApi(name)).start();
    }

    class ImbdMovieRatingApi implements Runnable{

        String requested_movie;
        ImbdMovieRatingApi(String name){
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
                URL url = new URL("https://imdb-api.com/en/API/SearchMovie/k_9v3ck035/"+requested_movie.trim());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));


                //read all lines in StringBuilder
                String line;
                while ((line = bf.readLine()) != null){
                    stb.append(line);
                }

                //json prahase
                JSONObject json = new JSONObject(stb.toString());
                JSONArray jsonArray = json.getJSONArray("results");

                //find movie name and find imageUrl and add into pictureUrl
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_movie = jsonArray.getJSONObject(i);
                    String movie_name = json_movie.getString("title");
                    if (movie_name.toLowerCase().equals(requested_movie.toLowerCase())) {
                        movieId.append(json_movie.getString("id"));
                        moviestit.append(json_movie.getString("title"));
                        moviesdes.append(json_movie.getString("description"));
                        picture_url = json_movie.getString("image");
                    }

                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            movie_poster = getBitmap();


            //update imageView
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    apiImage.setImageBitmap(movie_poster);
                }
            });
        }

        // retrieve a bitmap image from the URL in JSON
        Bitmap getBitmap() {
            Bitmap bitmap = null;
            try {
                URL url = new URL(picture_url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedInputStream bfstream = new BufferedInputStream(con.getInputStream());

                bitmap = BitmapFactory.decodeStream(bfstream);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            return bitmap;
        }
    }
}