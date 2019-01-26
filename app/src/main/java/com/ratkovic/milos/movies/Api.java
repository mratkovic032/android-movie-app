package com.ratkovic.milos.movies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public static final String TMDB_API = "?api_key=c1392e19dc86b61a896ba3a8a2712d32";
    public static final String BASE_MOVIE_API = "https://api.themoviedb.org/3/movie/";
    public static final String BASE_PICTURE_API = "https://image.tmdb.org/t/p/";
    public static final String BASE_SEARCH_API = "https://api.themoviedb.org/3/search/";

    public static void getJson(String url, final ReadDataHandler rdh) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String response = "";
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    response = br.readLine();
                    br.close();
                } catch (Exception e) {
                    Log.e("Error message:", e.getMessage());
                    response = "[]";
                }

                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                try {
                    JSONObject jsonObject =  new JSONObject(response);
                    rdh.setJson(jsonObject);
                    rdh.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        task.execute(url);
    }
}
