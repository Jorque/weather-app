package com.github.jorque.bootcamp;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherService {

    private final String units = "metric";
    private final String APIKEY;

    public WeatherService(String key) {
        APIKEY = key;
    }

    public WeatherForecast requestWeatherForecast(GeoCoordinates coordinates) {
        String json = getAPIResponse(coordinates.getLatitude(), coordinates.getLongitude());
        if (json == null) {
            System.err.println("API bug");
            //TODO : do something ?
            return null;
        }

        JSONObject object = getJSONObject(json);
        if (object == null) {
            System.err.println("JSON bug");
            //TODO : do something ?
            return null;
        }

        WeatherForecast forecast;
        try {
            forecast = new WeatherForecast(object);
        } catch (JSONException e) {
            System.out.println(e);
            //TODO : do something ?
            return null;
        }
        return forecast;
    }

    private String getAPIResponse(double latitude, double longitude) {
        String queryUrl = buildURL(latitude, longitude);
        String json = null;

        try {
            URL url = new URL(queryUrl);

            InputStream stream;
            HttpsURLConnection connection;

            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");

            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            // Do something with responseCode
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                connection.disconnect();
                return null;
            }

            stream = connection.getInputStream();
            // Do something with the stream
            json = streamToString(stream);

            stream.close();
            connection.disconnect();
        } catch (IOException e) {
            System.err.println("Error requesting weather : " + e);
        }
        return json;
    }

    private String buildURL(double latitude, double longitude) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.openweathermap.org/data/2.5/onecall?");
        builder.append("lat=").append(latitude);
        builder.append("&lon=").append(longitude);
        builder.append("&appid=").append(APIKEY);
        builder.append("&units=").append(units);
        return builder.toString();
    }

    private String streamToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    private JSONObject getJSONObject(String str) {
        JSONObject object;
        try {
            object = (JSONObject) new JSONTokener(str).nextValue();
        } catch (JSONException e) {
            System.err.println("Error parsing JSONObject : " + e);
            return null;
        }
        return object;
    }

}
