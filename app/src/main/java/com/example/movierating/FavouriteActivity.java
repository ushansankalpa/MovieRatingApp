package com.example.movierating;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavouriteActivity extends AppCompatActivity {

    MovieDB db;
    ArrayList<Movies> moviearray ;
    Movies moviList2;
    ListView favLists;
    MovieAdapter dataAdepter = null;
    Context context;
    Button saveBtn;
    ArrayList<Movies> trueFave;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        context =this;
        favLists = findViewById(R.id.favList);
        db = new MovieDB(this);
        moviearray = new ArrayList<>();
        saveBtn = findViewById(R.id.save_btn);

        //reading all data in a database as a object and add it to the arrayList
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()){
            moviList2 = new Movies(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7)==1);
            moviearray.add(moviList2);
            System.out.println(moviearray.toString()+"\n");

        }

        //if favourite column is true,add true movies another array
        trueFave = new ArrayList<>();
        for(Movies mov : moviearray){
            if (mov.getFavourite()){
                trueFave.add(mov);
            }
        }

        //after all fave movie title added into the list view usign array adepter
        dataAdepter = new MovieAdapter(context,R.layout.single_movie,trueFave);
        Comparator comparator_1 = Comparator.comparing(Movies::getMovieTitle);
        Collections.sort(trueFave,comparator_1);
        favLists.setAdapter(dataAdepter);


        saveBtn();
    }

    public  void saveBtn(){

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer resTex = new StringBuffer();


                ArrayList<String> title_array = new ArrayList<>();
                for (int i=0; i<moviearray.size();i++){
                    Movies movies_box = moviearray.get(i);

                    if (!movies_box.getFavourite()){
                        resTex.append(""+movies_box.getMovieTitle());
                        title_array.add(movies_box.getMovieTitle());



                    }

                }

                //add all changes are into the database
                Cursor cursor = db.getAllData();
                while (cursor.moveToNext()) {
                    for (int j = 0; j < title_array.size(); j++) {
                        if (cursor.getString(1).equals(title_array.get(j))) {
                            String title = cursor.getString(1);
                            int year = cursor.getInt(2);
                            String director = cursor.getString(3);
                            String actor = cursor.getString(4);
                            int rating = cursor.getInt(5);
                            String review = cursor.getString(6);
                            Boolean is_fav = false;


                            boolean isSelected = db.updateFav(title, year, director, actor, rating, review,is_fav);

                            if (isSelected == true) {
                                Toast.makeText(getApplicationContext(), resTex, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }
        });

    }
}