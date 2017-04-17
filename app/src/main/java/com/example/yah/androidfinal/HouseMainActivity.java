package com.example.yah.androidfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

// TODO DELETE... THIS IS A USELESS PLACEHOLDER CLASS

public class HouseMainActivity extends TestToolbar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // GARAGE BUTTON
        Button garageButton = (Button) findViewById(R.id.garageButton);
        garageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start new Garage Activity
                Intent intent1 = new Intent (HouseMainActivity.this, Garage.class);
                startActivity(intent1);
            }
        });

        // HOUSE TEMPERATURE BUTTON
        Button tempButton = (Button) findViewById(R.id.tempButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start new House Temperature Activity
                Intent intent2 = new Intent (HouseMainActivity.this, HouseTemp.class);
                startActivity(intent2);
            }
        });

        // WEATHER
        Button weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start new Weather Activity
                Intent intent3 = new Intent (HouseMainActivity.this, Weather.class);
                startActivity(intent3);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
} // End of HouseMainActivity.java