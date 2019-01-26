package com.ratkovic.milos.movies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class CastModel {
    private static final String TAG = "CastModel";
    private String castName, character, profile_path, directorName, producerName;
    private String[] imagePaths;

    public String[] getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(String[] imagePaths) {
        this.imagePaths = imagePaths;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getCastName() { return castName; }

    public void setCastName(String castName) { this.castName = castName; }

    public String getCharacter() { return character; }

    public void setCharacter(String character) { this.character = character; }

    public String getProfile_path() { return profile_path; }

    public void setProfile_path(String profile_path) { this.profile_path = profile_path; }


    public static LinkedList<CastModel> parseJSONArray(JSONObject jsonObject) {
        LinkedList<CastModel> castList = new LinkedList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("cast");
            for (int i = 0; i < jsonArray.length(); i++) {
                castList.add(parseCastJSONObject(jsonArray.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return castList;
    }

    public static CastModel parseCastJSONObject(JSONObject object) {
        CastModel cast = new CastModel();

        try {
            cast.setCastName(object.getString("name"));
            cast.setCharacter(object.getString("character"));
            cast.setProfile_path(object.getString("profile_path"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "parseJSONObject: " + cast.getCastName() + "; " + cast.getCharacter() + "; " + cast.getProfile_path());
        return cast;
    }

    public static LinkedList<CastModel> parseJSONArrayCrew(JSONObject jsonObject) {
        LinkedList<CastModel> crewList = new LinkedList<>();
        try {
            JSONArray jsonArrayCrew = jsonObject.getJSONArray("crew");
            for (int i = 0; i < jsonArrayCrew.length(); i++) {
                crewList.add(parseJSONObjectCrew(jsonArrayCrew.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return crewList;
    }

    public static CastModel parseJSONObjectCrew(JSONObject object) {
        CastModel crew = new CastModel();
        try {
            if ((object.getString("job")).equals("Director")) {
                crew.setDirectorName(object.getString("name"));
            } else if ((object.getString("job")).equals("Producer")) {
                crew.setProducerName(object.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parseJSONObjectCrew: " + crew.toString());
        return crew;
    }

    @Override
    public String toString() {
        return "CastModel{" +
                "castName='" + castName + '\'' +
                ", character='" + character + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", directorName='" + directorName + '\'' +
                ", producerName='" + producerName + '\'' +
                '}';
    }
}
