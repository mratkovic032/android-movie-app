package com.ratkovic.milos.movies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import java.util.Arrays;
import java.util.LinkedList;

public class MovieModel implements Serializable {
    private static final String TAG = "MovieModel";
    private String original_title, title, overview, release_date, poster_path, original_language, id;
    private String[] imagePaths, videoPaths, genres;
    private double vote_average;
    private int runtime, budget;

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) { this.original_language = original_language; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String[] getImagePaths() { return imagePaths; }

    public void setImagePaths(String[] imagePaths) { this.imagePaths = imagePaths; }

    public String[] getVideoPaths() { return videoPaths; }

    public void setVideoPaths(String[] videoPaths) { this.videoPaths = videoPaths; }

    public String[] getGenres() { return genres; }

    public void setGenres(String[] genres) { this.genres = genres; }

    public int getRuntime() { return runtime; }

    public void setRuntime(int runtime) { this.runtime = runtime; }

    public static LinkedList<MovieModel> parseJSONArray(JSONObject jsonObject) {
        LinkedList<MovieModel> movieList = new LinkedList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                movieList.add(parseJSONObject(jsonArray.getJSONObject(i)));
                Log.d(TAG, "List: " + movieList.get(i).getOriginal_title());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public static MovieModel parseJSONObject(JSONObject object) {
        MovieModel movieDetails = new MovieModel();

        try {
            movieDetails.setOriginal_title(object.getString("original_title"));
            movieDetails.setTitle(object.getString("title"));
            movieDetails.setOriginal_language(object.getString("original_language"));
            movieDetails.setOverview(object.getString("overview"));
            movieDetails.setRelease_date(object.getString("release_date"));
            movieDetails.setVote_average(object.getDouble("vote_average"));
            movieDetails.setPoster_path(object.getString("poster_path"));
            movieDetails.setId(object.getString("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieDetails;
    }

    public static MovieModel parseMovieJSONObject(JSONObject object) {
        MovieModel movieDetails = new MovieModel();
        try {
            movieDetails.setImagePaths(parseImages(object.getJSONObject("images")));
            movieDetails.setVideoPaths(parseVideos(object.getJSONObject("videos")));
            movieDetails.setGenres(parseGenres(object.getJSONArray("genres")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            movieDetails.setId(object.getString("id"));
            movieDetails.setOriginal_title(object.getString("original_title"));
            movieDetails.setTitle(object.getString("title"));
            movieDetails.setOriginal_language(object.getString("original_language"));
            movieDetails.setOverview(object.getString("overview"));
            movieDetails.setRelease_date(object.getString("release_date"));
            movieDetails.setVote_average(object.getDouble("vote_average"));
            movieDetails.setPoster_path(object.getString("poster_path"));
            movieDetails.setRuntime(object.getInt("runtime"));
            movieDetails.setBudget(object.getInt("budget"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieDetails;
    }

    public static String[] parseGenres(JSONArray array) {
        String[] genres;
        genres = new String[array.length()];
        try {
            for (int i = 0; i < array.length(); i++) {
                genres[i] = (array.getJSONObject(i)).getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }

    public static String[] parseImages(JSONObject object) {
        String[] imagePaths;
        try {
            JSONArray jsonArray = object.getJSONArray("backdrops");
            imagePaths = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                imagePaths[i] = (jsonArray.getJSONObject(i)).getString("file_path");
            }
        } catch (Exception e) {
            imagePaths = new String[0];
            e.printStackTrace();
        }

        return imagePaths;
    }

    public static String[] parseVideos(JSONObject object) {
        String[] videoPaths;
        try {
            JSONArray jsonArray = object.getJSONArray("results");
            videoPaths = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                videoPaths[i] = (jsonArray.getJSONObject(i)).getString("key");
            }
        } catch (Exception e) {
            videoPaths = new String[0];
            e.printStackTrace();
        }

        return videoPaths;
    }
}
