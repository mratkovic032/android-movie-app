package com.ratkovic.milos.movies;

import android.os.Handler;

import org.json.JSONObject;

public class ReadDataHandler extends Handler {
    private JSONObject json;

    public JSONObject getJson() {

        return json;
    }

    public void setJson(JSONObject json) {

        this.json = json;
    }
}
