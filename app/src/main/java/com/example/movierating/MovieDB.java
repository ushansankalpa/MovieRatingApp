package com.example.movierating;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MovieDB extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final  String DB_Name = "MoviesData.db";
    private static final String TABLE_Name = "MoviesDetails";

    private static final String MOV_ID = "mov_id";
    private static final String MOV_TITLE ="mov_title";
    private static final String MOV_YEAR = "mov_year";
    private static final String MOV_DIRECTOR = "mov_director";
    private static final String MOV_ACTORS = "mov_actors";
    private static final String MOV_RATING = "mov_rating";
    private static final String MOV_REVIEW = "mov_review";
    private static final String MOV_FAV = "mov_favourite";


    public MovieDB(Context context){
        super(context,DB_Name,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create on the table
        String createQuery = " create Table "+TABLE_Name+" ("
                +MOV_ID+" INTEGER primary key Autoincrement, "
                +MOV_TITLE+" TEXT  , "
                +MOV_YEAR+" TEXT, "
                +MOV_DIRECTOR+" TEXT, "
                +MOV_ACTORS+" TEXT, "
                +MOV_RATING+" TEXT, "
                +MOV_REVIEW+" TEXT , "
                +MOV_FAV+" TEXT "+
                ");";
        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //update on the table
        db.execSQL("drop Table if exists "+TABLE_Name);

    }


    public Boolean insertMovieData(Movies movies){
        //insert data to the table
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MOV_ID,movies.getMovieId());
        contentValues.put(MOV_TITLE,movies.getMovieTitle());
        contentValues.put(MOV_YEAR,movies.getMovieYear());
        contentValues.put(MOV_DIRECTOR,movies.getMovieDirector());
        contentValues.put(MOV_ACTORS,movies.getMovieActors());
        contentValues.put(MOV_RATING,movies.getMovieRating());
        contentValues.put(MOV_REVIEW,movies.getMovieReview());
        contentValues.put(MOV_FAV,movies.getFavourite());

        long result = DB.insert(TABLE_Name,null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public ArrayList<Movies> getAllMovies(){
        //getAll movies and save to arrayList
        ArrayList<Movies> movie = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = " Select * from "+TABLE_Name;
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                Movies movies = new Movies();

                movies.setMovieTitle(cursor.getString(1));
                movies.setMovieYear(cursor.getInt(2));
                movies.setMovieDirector(cursor.getString(3));
                movies.setMovieActors(cursor.getString(4));
                movies.setMovieRating(cursor.getInt(5));
                movies.setMovieReview(cursor.getString(6));
                movies.setFavourite(cursor.getWantsAllOnMoveCalls());



                movie.add(movies);

            }while (cursor.moveToNext());

        }
        return movie;
    }

    //update favourite column
    public Boolean updateFav(String title,int year,String director,String actor,int rating,String review,Boolean isFav){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOV_TITLE,title);
        contentValues.put(MOV_YEAR,year);
        contentValues.put(MOV_DIRECTOR,director);
        contentValues.put(MOV_ACTORS,actor);
        contentValues.put(MOV_RATING,rating);
        contentValues.put(MOV_REVIEW,review);
        contentValues.put(MOV_FAV,isFav);
        DB.update(TABLE_Name,contentValues,"mov_title = ?",new String[]{title});
        return true;
    }

    //get all data to Cursor
    public Cursor getAllData(){
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = " Select * from "+TABLE_Name;
        Cursor cursor = DB.rawQuery(query,null);
        return cursor;
    }

    //get values from database
    public Movies getSingleToDo(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_Name,new String[]{MOV_ID,MOV_TITLE,MOV_YEAR,MOV_DIRECTOR, MOV_ACTORS,MOV_RATING,MOV_REVIEW,MOV_FAV},
                 "mov_id =?",new String[]{String.valueOf(id)}
                ,null,null,null);

        Movies edMovie;
        if(cursor != null){
            cursor.moveToFirst();
            edMovie = new Movies(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7)==1

            );
            return edMovie;
        }
        return null;
    }

    //update changes
    public Boolean updateSingleToDo(Movies movieUp){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MOV_TITLE,movieUp.getMovieTitle());
        contentValues.put(MOV_YEAR,movieUp.getMovieYear());
        contentValues.put(MOV_DIRECTOR,movieUp.getMovieDirector());
        contentValues.put(MOV_ACTORS,movieUp.getMovieActors());
        contentValues.put(MOV_RATING,movieUp.getMovieRating());
        contentValues.put(MOV_REVIEW,movieUp.getMovieReview());
        contentValues.put(MOV_FAV,movieUp.getFavourite());



        //int status = db.update(TABLE_Name,contentValues,"mov_title = ?", new String[]{String.valueOf(movieUp.getMovieTitle())});

        Cursor cursor = db.rawQuery(" SELECT * from "+TABLE_Name+" where mov_id =?" ,new String[]{String.valueOf(movieUp.getMovieId())});

        if (cursor.getCount() >0){
            long result = db.update(TABLE_Name,contentValues,"mov_id =?",new String[]{String.valueOf(movieUp.getMovieId())});
            if (result==-1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }


    }


}
