package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditeActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Movies> listItem;
    ArrayList<String> title_item;
    ArrayAdapter arrayAdapter;
    MovieDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite);

        db =new MovieDB(this);
        listItem = new ArrayList<>();
        title_item = new ArrayList<>();

        listView=findViewById(R.id.editListView);

        //read all data from database and add into the array List
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()){
            Movies moviList2 = new Movies(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7)==1);
            String tit = moviList2.getMovieTitle();
            listItem.add(moviList2);
            title_item.add(tit);
        }
        //and set all title into the array adepter and show the result in list view
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,title_item);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if click to time title and goto next intent as a value
                String item = listView.getItemAtPosition(position).toString();
                Movies movie = listItem.get(position);


                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
                Intent upDateIntent = new Intent(EditeActivity.this,UpDateActivity.class);
                upDateIntent.putExtra("id",movie.getMovieId());
                startActivity(upDateIntent);
            }
        });

    }
}