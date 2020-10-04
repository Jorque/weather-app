package com.github.jorque.bootcamp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {

    public static final int HOURLY_PREDICTIONS = 12;
    public static final int DAILY_PREDICTIONS = 6;

    private Weather current;
    private List<Weather> hourly;
    private List<Weather> daily;

    public WeatherForecast(JSONObject json) throws JSONException {
        this.current = new Weather(json.getJSONObject("current"));
        this.hourly = new ArrayList<>();
        this.daily = new ArrayList<>();

        for (int i = 0; i < HOURLY_PREDICTIONS; ++i) {
            hourly.add(new Weather(json.getJSONArray("hourly").getJSONObject(i)));
        }
        for (int i = 0; i < DAILY_PREDICTIONS; ++i) {
            daily.add(new Weather(json.getJSONArray("daily").getJSONObject(i)));
        }
    }

    public Weather getCurrentWeather() {
        return current;
    }

    public Weather getHourlyWeather(int i) {
        if (i >= HOURLY_PREDICTIONS) return null;
        return hourly.get(i);
    }

    public Weather getDailyWeather(int i) {
        if (i >= DAILY_PREDICTIONS) return null;
        return daily.get(i);
    }

}
