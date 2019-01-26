package com.ratkovic.milos.movies;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.mainListView);
        listView.setOnItemClickListener(this);
        initMovies();

    }

    public void initMovies() {

//        https://api.themoviedb.org/3/search/movie?api_key=c1392e19dc86b61a896ba3a8a2712d32&query=batman
        Api.getJson(Api.BASE_MOVIE_API + "upcoming" + Api.TMDB_API, new ReadDataHandler(){
            @Override
            public void handleMessage(Message msg) {
                try {
                    JSONObject jsonObject = getJson();
                    LinkedList<MovieModel> movies = MovieModel.parseJSONArray(jsonObject);
                    MovieArrayAdapter movieArrayAdapter = new MovieArrayAdapter(MainActivity.this, R.layout.movie_view_list, movies);
                    listView.setAdapter(movieArrayAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie_details", (MovieModel) adapterView.getItemAtPosition(i));
        startActivity(intent);
    }
}
