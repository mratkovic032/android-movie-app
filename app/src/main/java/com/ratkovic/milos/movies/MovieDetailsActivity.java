package com.ratkovic.milos.movies;

import android.content.Context;
import android.media.Image;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MovieDetailsActivity extends YouTubeBaseActivity {
    private static final String TAG = "MovieDetailsActivity";

    private String urlMovie;
    YouTubePlayerView youtubePlayer;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        youtubePlayer = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        MovieModel movie = (MovieModel) getIntent().getExtras().getSerializable("movie_details");
        urlMovie = Api.BASE_MOVIE_API +  movie.getId() + Api.TMDB_API + "&append_to_response=images,videos";

//        if (movie != null) {
//            Picasso.get().load("https://image.tmdb.org/t/p/w780" + movie.getPoster_path()).into((ImageView) findViewById(R.id.imageDetailsPoster));
//            ((TextView) findViewById(R.id.labelDetailsTitle)).setText(movie.getOriginal_title());
//            ((TextView) findViewById(R.id.labelDetailsId)).setText(movie.getId());
//        }

        initMovie();
    }

    public void initMovie() {
        Api.getJson(urlMovie, new ReadDataHandler(){
            @Override
            public void handleMessage(Message msg) {
                try {
                    JSONObject jsonObject = getJson();
                    MovieModel movie = MovieModel.parseMovieJSONObject(jsonObject);

                    initAllDetails(movie);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initAllDetails(MovieModel movie) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) youtubePlayer.getLayoutParams();

        final String[] videoKeys = movie.getVideoPaths();
        final String[] imagePaths = movie.getImagePaths();
        String[] genres = movie.getGenres();
        Log.d(TAG, "handleMessage: " + Arrays.toString(videoKeys));
        Log.d(TAG, "handleMessage: " + Arrays.toString(imagePaths));

        String releaseDate = movie.getRelease_date();
        String month = releaseDate.substring(5, 7);
        String year = releaseDate.substring(0, 4);
        String day = releaseDate.substring(8);
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

        if (videoKeys != null && videoKeys.length > 0) {
            onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//                    youTubePlayer.loadVideo(videoKeys[0]);
                    List<String> movieList = new ArrayList<>();
                    for (String movieKey : videoKeys) {
                        movieList.add(movieKey);
                    }
                    youTubePlayer.loadVideos(movieList);
                    Log.d(TAG, "onInitializationSuccess: " + videoKeys[0]);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            };
            youtubePlayer.initialize(YouTubeConfig.getApiKey(), onInitializedListener);

        } else {
            youtubePlayer.setVisibility(View.GONE);
            params.height = 0;
            youtubePlayer.setLayoutParams(params);
            ImageView imageBackdrop = new ImageView(MovieDetailsActivity.this);
            RelativeLayout detailsLayout = (RelativeLayout) findViewById(R.id.detailsLayout);
            imageBackdrop.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Picasso.get().load(Api.BASE_PICTURE_API + "w1280" + imagePaths[0]).into(imageBackdrop);
            detailsLayout.addView(imageBackdrop);
        }

        ((TextView) findViewById(R.id.labelDetailsTitle)).setText(movie.getTitle());

        if (!(movie.getOriginal_language()).equals("en")) {
            ((TextView) findViewById(R.id.labelDetailsOriginalTitle)).setText("Original title: " + movie.getOriginal_title());
            TextView originalTitle = findViewById(R.id.labelDetailsOriginalTitle);
            originalTitle.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams paramsOriginalTitle = (ViewGroup.LayoutParams) originalTitle.getLayoutParams();
            paramsOriginalTitle.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            originalTitle.setLayoutParams(paramsOriginalTitle);
        }

        ((TextView) findViewById(R.id.labelDetailsReleaseDate)).setText("Release Date:  " + day + " " + month + " " + year);

        String runtimeString = "";
        int runtimeInt = movie.getRuntime();
        if (runtimeInt == 60) {
            runtimeString = "1h";
        } else if (runtimeInt > 60 && runtimeInt < 120) {
            runtimeString = "1h " + String.valueOf(runtimeInt - 60) + "min";
        } else if (runtimeInt == 120) {
            runtimeString = "2h";
        } else if (runtimeInt > 120 && runtimeInt < 180) {
            runtimeString = "2h " + String.valueOf(runtimeInt - 120) + "min";
        } else if (runtimeInt == 180) {
            runtimeString = "3h";
        } else if (runtimeInt > 180 && runtimeInt < 240) {
            runtimeString = "3h " + String.valueOf(runtimeInt - 180) + "min";
        } else if (runtimeInt == 240) {
            runtimeString = "4h";
        } else if (runtimeInt > 240) {
            runtimeString = "4h " + String.valueOf(runtimeInt - 240) + "min";
        }

        ((TextView) findViewById(R.id.labelDetailsRuntime)).setText(runtimeString);

        TextView labelGenres = findViewById(R.id.labelDetailsGenres);
        String genreString = "";
        for (String genre : genres) {
            genreString += genre + ", ";
        }
        labelGenres.setText(genreString.substring(0, genreString.length() - 2));

        Picasso.get().load(Api.BASE_PICTURE_API + "w342" + movie.getPoster_path()).into((ImageView) findViewById(R.id.imageDetailsPoster));
        String movieOverview = movie.getOverview();
        if (movieOverview.length() > 245) {
            movieOverview = movieOverview.substring(0, 246) + "...";
        }
        ((TextView) findViewById(R.id.labelDetailsOverview)).setText(movieOverview);
        ((TextView) findViewById(R.id.labelDetailsRating)).setText(String.valueOf(movie.getVote_average()) + "/10");


        Api.getJson(Api.BASE_MOVIE_API + movie.getId() + "/credits" + Api.TMDB_API, new ReadDataHandler(){
            @Override
            public void handleMessage(Message msg) {
                try {
                    JSONObject jsonObject = getJson();
                    LinkedList<CastModel> castList = CastModel.parseJSONArray(jsonObject);

                    LinearLayout horizontalLinearCastView = (LinearLayout) findViewById(R.id.horizontalLinearCastView);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    for (CastModel cast : castList) {
                        RelativeLayout castCardView = (RelativeLayout) inflater.inflate(R.layout.cast_card_view, null);

                        if (!(cast.getProfile_path()).equals("null")) {
                            Picasso.get().load(Api.BASE_PICTURE_API + "w185" + cast.getProfile_path()).into((ImageView) castCardView.findViewById(R.id.imageCastProfile));
                        } else {
                            ImageView defaultProfileImage = castCardView.findViewById(R.id.imageCastProfile);
                            ViewGroup.LayoutParams paramsDefaultProfileImage = (ViewGroup.LayoutParams) defaultProfileImage.getLayoutParams();
                            paramsDefaultProfileImage.width = 185;
                            paramsDefaultProfileImage.height = 278;
                            defaultProfileImage.setLayoutParams(paramsDefaultProfileImage);
                            defaultProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.default_profile, getApplicationContext().getTheme()));
//                            Picasso.get().load("http://www.bestfootball.fr/uploads/img/profile.png").into((ImageView) castCardView.findViewById(R.id.imageCastProfile));
                        }
                        ((TextView) castCardView.findViewById(R.id.labelCastName)).setText(cast.getCastName());
                        ((TextView) castCardView.findViewById(R.id.labelCastCharacterName)).setText(cast.getCharacter());

                        horizontalLinearCastView.addView(castCardView);
                    }

                    LinkedList<CastModel> crewList = CastModel.parseJSONArrayCrew(jsonObject);
                    String directors = "";
                    String producers = "";

                    for (CastModel crew : crewList) {
                        if (crew.getDirectorName() != null) {
                            directors += crew.getDirectorName() + ", ";
                        }
                        if (crew.getProducerName() != null) {
                            producers += crew.getProducerName() + ", ";
                        }
                    }

                    ((TextView) findViewById(R.id.labelDetailsDirectors)).setText(directors.substring(0, directors.length() - 2));
                    ((TextView) findViewById(R.id.labelDetailsProducers)).setText(producers.substring(0, producers.length() - 2));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        LinearLayout horizontalLinearImageView = (LinearLayout) findViewById(R.id.horizontalLinearImageView);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (String imagePath : imagePaths) {
            RelativeLayout imageCardView = (RelativeLayout) inflater.inflate(R.layout.image_card_view, null);
            Picasso.get().load(Api.BASE_PICTURE_API + "w500" + imagePath).into((ImageView) imageCardView.findViewById(R.id.imageScene));
            horizontalLinearImageView.addView(imageCardView);
        }

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        ((TextView) findViewById(R.id.labelDetailsBudget)).setText(numberFormat.format(movie.getBudget()));
    }
}
