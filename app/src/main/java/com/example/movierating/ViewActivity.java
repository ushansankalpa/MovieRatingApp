package com.example.movierating;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    ArrayList<Movies> moviesList;
    MovieDB movieDB;
    ListView listMovie;
    CheckBox isCheckFav ;
    Button favouriteBtn ;
    TextView title;
    MovieAdapter movieAdapter=null;
    Movies movies;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Context context= this;

        movieDB = new MovieDB(context);
        listMovie =findViewById(R.id.movieList);
        moviesList = new ArrayList<>();
        isCheckFav = findViewById(R.id.checkFav);
        favouriteBtn =findViewById(R.id.fav_btn);
        title = findViewById(R.id.move_tit);



        //add to favourite
        tickBoxFavourite();


        //reading all data in a database as a object and add it to the arrayList
        Cursor cursor = movieDB.getAllData();
        while (cursor.moveToNext()){
            movies = new Movies(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7)==1);
            moviesList.add(movies);

        }


        //after all fave movie title added into the list view usign array adepter
        moviesList = movieDB.getAllMovies();
        Comparator comparator_1 = Comparator.comparing(Movies::getMovieTitle);
        Collections.sort(moviesList,comparator_1);
        movieAdapter = new MovieAdapter(context,R.layout.single_movie,moviesList);
        listMovie.setAdapter(movieAdapter);




    }

    //tik to the check box and save it in to the database
    public void tickBoxFavourite(){

        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer resTex = new StringBuffer();

                resTex.append("");
                ArrayList<Movies> movies = movieAdapter.movies;
                ArrayList<String> title_array = new ArrayList<>();


                for (int i=0; i<movies.size();i++){
                    Movies movies_box = movies.get(i);

                    if (movies_box.getFavourite()){
                        resTex.append(""+movies_box.getMovieTitle());
                        title_array.add(movies_box.getMovieTitle());



                    }

                }

                System.out.println(title_array.toString());

                //reading all data in a database as a object and add it to the arrayList
                Cursor cursor = movieDB.getAllData();
                System.out.println(cursor);
                while (cursor.moveToNext()) {
                    for (int j = 0; j < title_array.size(); j++) {
                        if (cursor.getString(1).equals(title_array.get(j))) {
                            String title = cursor.getString(1);
                            int year = cursor.getInt(2);
                            String director = cursor.getString(3);
                            String actor = cursor.getString(4);
                            int rating = cursor.getInt(5);
                            String review = cursor.getString(6);
                            Boolean is_fav = true;


                            boolean isSelected = movieDB.updateFav(title, year, director, actor, rating, review,is_fav);

                            if (isSelected) {
                                Toast.makeText(getApplicationContext(), resTex, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }
        });

    }


}