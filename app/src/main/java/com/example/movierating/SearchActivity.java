package com.example.movierating;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchActivity extends AppCompatActivity {

    EditText searchBar ;
    Button searchBtn;
    ListView movieList;
    ArrayList<Movies> movieArray;
    MovieDB db;
    Movies movieObj;
    //MovieAdapter dataAdepter = null;
    ArrayAdapter<String> dataAdepterq;
    Context context;
    ArrayList<String> diplaySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = this;
        searchBar = findViewById(R.id.movieSearch_bar);
        searchBtn =  findViewById(R.id.searchButton);
        movieArray = new ArrayList<>();
        movieList = findViewById(R.id.searchMovieList);
        db = new MovieDB(context);


        //reading all data in a database as a object and add it to the arrayList
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()){
            movieObj = new Movies(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7)==1);
            movieArray.add(movieObj);


        }

        diplaySearch = new ArrayList<>();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diplaySearch.removeAll(diplaySearch);
                String getSearch = searchBar.getText().toString().toLowerCase();
                //then search data from the array
                for (Movies movies: movieArray){
                    if (movies.getMovieTitle().toLowerCase().contains(getSearch)|| movies.getMovieDirector().contains(getSearch) || movies.getMovieActors().contains(getSearch)){
                        System.out.println(movies);
                        String tit = movies.getMovieTitle().toLowerCase();
                        int year = movies.getMovieYear();
                        diplaySearch.add(tit);


                    }
                }
                if (diplaySearch.isEmpty()){
                    System.out.println(diplaySearch);
                    Toast.makeText(getApplicationContext(),"Incorrect Not matching",Toast.LENGTH_SHORT).show();
                }

                dataAdepterq = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,diplaySearch);
                movieList.setAdapter(dataAdepterq);



            }
        });




    }
}