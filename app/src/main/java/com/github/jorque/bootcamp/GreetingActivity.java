package com.github.jorque.bootcamp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GreetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.greetingMessage);
        textView.setText("Hello " + name + " !");
    }

    public void goWeather(View view) {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
}