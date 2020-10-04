package com.github.jorque.bootcamp;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Weather {
    public static enum PERIOD {HOURLY, DAILY}

    private Calendar time;
    private double temp;
    private String condition;

    public Weather(JSONObject json) throws JSONException {
        this.time = null; //json.getJSONObject("dt");
        this.temp = json.getDouble("temp");
        this.condition = json.getJSONArray("weather").getJSONObject(0).getString("description");
    }

    public Weather(long epochSec, double temp, String condition) {
        //Calendar.Builder builder = new Calendar.Builder(); //TODO : How to build time ???
        this.time = null;
        this.temp = temp;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return temp + "°C - " + condition;
    }

    public String toString(PERIOD period) {
        switch (period) {
            case HOURLY: return "00:00 - " + toString();
            case DAILY: return "Monday - " + toString();
            default: return toString();
        }
    }

}
