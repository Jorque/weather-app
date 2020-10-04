package com.github.jorque.bootcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    private LocationService locationService;
    private WeatherService weatherService;
    private GeocodingService geocodingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        locationService = new LocationService(this);
        weatherService = new WeatherService(getResources().getString(R.string.APIKEY));
        geocodingService = new GeocodingService(this);

        findViewById(R.id.searchButton).setOnClickListener(v -> {
            loadWeatherEntry();
        });

        findViewById(R.id.gpsButton).setOnClickListener(v -> {
            loadWeatherGPS();
        });
    }

    private void loadWeatherEntry() {
        try {
            GeoCoordinates loc;

            // Here we use the given city name as our query location
            String cityName = ((TextView) findViewById(R.id.location_entry)).getText().toString();
            loc = geocodingService.getCoordinates(cityName);

            WeatherForecast forecast = weatherService.requestWeatherForecast(loc);
            String address = geocodingService.getAddress(loc);
            setViews(address, forecast);
        } catch (IOException e) {
            Log.e("WeatherActivity", "Error when retrieving forecast.", e);
        }
    }

    private void loadWeatherGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }

        try {
            GeoCoordinates loc;

            // Here we use the given city name as our query location
            loc = locationService.getUserCoordinates();

            if (loc == null) return;

            WeatherForecast forecast = weatherService.requestWeatherForecast(loc);
            String address = geocodingService.getAddress(loc);
            setViews(address, forecast);
        } catch (IOException e) {
            Log.e("WeatherActivity", "Error when retrieving forecast.", e);
        }
    }

    private void setViews(String location, WeatherForecast forecast) {
        TextView locationView = findViewById(R.id.locationName);
        locationView.setText(location);

        TextView currentWeatherView = findViewById(R.id.currentWeather);
        currentWeatherView.setText(forecast.getCurrentWeather().toString());

        for (int i = 0; i < WeatherForecast.HOURLY_PREDICTIONS; ++i) {
            TextView hourlyWeatherView = findViewById(getHourlyId(i));
            hourlyWeatherView.setText(forecast.getHourlyWeather(i).toString(Weather.PERIOD.HOURLY));
        }

        for (int i = 0; i < WeatherForecast.DAILY_PREDICTIONS; ++i) {
            TextView dailyWeatherView = findViewById(getDailyId(i));
            dailyWeatherView.setText(forecast.getDailyWeather(i).toString(Weather.PERIOD.DAILY));
        }
    }

    private int getHourlyId(int i) {
        switch (i) {
            case 0: return R.id.hourly0;
            case 1: return R.id.hourly1;
            case 2: return R.id.hourly2;
            case 3: return R.id.hourly3;
            case 4: return R.id.hourly4;
            case 5: return R.id.hourly5;
            case 6: return R.id.hourly6;
            case 7: return R.id.hourly7;
            case 8: return R.id.hourly8;
            case 9: return R.id.hourly9;
            case 10: return R.id.hourly10;
            case 11: return R.id.hourly11;
            default: return 0;
        }
    }

    private int getDailyId(int i) {
        switch (i) {
            case 0: return R.id.daily0;
            case 1: return R.id.daily1;
            case 2: return R.id.daily2;
            case 3: return R.id.daily3;
            case 4: return R.id.daily4;
            case 5: return R.id.daily5;
            default: return 0;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadWeatherGPS();
            // We immediately call back the function, if the permission was not granted the prompt will be shown again
            // This is a dirty solution indeed, in the real world one would display an error message and the app
            // would work in a degraded way.
        }
    }
}