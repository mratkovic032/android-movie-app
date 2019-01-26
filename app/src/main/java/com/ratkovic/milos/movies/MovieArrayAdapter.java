package com.ratkovic.milos.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class MovieArrayAdapter extends ArrayAdapter {
    
    private LinkedList<MovieModel> movieDetailsList;
    private int resources;
    private Context context;

    public MovieArrayAdapter(@NonNull Context context, int resource, LinkedList<MovieModel> movieDetailsList) {
        super(context, resource, movieDetailsList);
        this.movieDetailsList = movieDetailsList;
        this.resources = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieModel movie = movieDetailsList.get(position);

        View view = LayoutInflater.from(context).inflate(resources, parent, false);

        String releaseDate = movie.getRelease_date();
        String month = releaseDate.substring(5, 7);
        String year = releaseDate.substring(0, 4);
        String day = releaseDate.substring(8);

        ((TextView) view.findViewById(R.id.labelListTitle)).setText(movie.getTitle() + " (" + year + ")");
        String movieOverview = movie.getOverview();
        if (movieOverview.length() > 134) {
            movieOverview = movieOverview.substring(0, 135) + "...";
        }
        
        ((TextView) view.findViewById(R.id.labelListOverview)).setText(movieOverview);
        switch(month) {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }
        ((TextView) view.findViewById(R.id.labelListReleaseDate)).setText(day + " " + month + " " + year);
        ((TextView) view.findViewById(R.id.labelListRating)).setText(String.valueOf(movie.getVote_average()) + "/10");
        Picasso.get().load(Api.BASE_PICTURE_API + "w342" + movie.getPoster_path()).into((ImageView) view.findViewById(R.id.imageListPoster));

        if (!(movie.getOriginal_language()).equals("en")) {
            ((TextView) view.findViewById(R.id.labelListOriginalTitle)).setText("Original title: " + movie.getOriginal_title());
            TextView originalTitle = view.findViewById(R.id.labelListOriginalTitle);
            originalTitle.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) originalTitle.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            originalTitle.setLayoutParams(params);
        }

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return movieDetailsList.get(position);
    }
}
