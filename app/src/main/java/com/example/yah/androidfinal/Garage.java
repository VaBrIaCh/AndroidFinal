package com.example.yah.androidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

public class Garage extends TestToolbar {


    private ProgressBar progressBar;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // PROGRESS BAR
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setMax(60);
            progressBar.setVisibility(View.VISIBLE);

        // IMAGES
        final ImageView doorImg = (ImageView) findViewById(R.id.doorImg);
        final ImageView lightImg = (ImageView) findViewById(R.id.lightImg);

        // TOGGLE BUTTONS
        final ToggleButton toggleDoor = (ToggleButton) findViewById(R.id.toggleDoor);
        final ToggleButton toggleLight = (ToggleButton) findViewById(R.id.toggleLight);

        // SHARED PREFERENCES
        SharedPreferences sharedPref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        boolean door = sharedPref.getBoolean("GarageDoor", false);
        if (door) {
            toggleDoor.setChecked(true);
            doorImg.setImageResource(R.drawable.open);
        } else {
            toggleDoor.setChecked(false);
            doorImg.setImageResource(R.drawable.closed);
        }

        boolean light = sharedPref.getBoolean("GarageLight", false);
        if (light) {
            toggleLight.setChecked(true);
            lightImg.setImageResource(R.drawable.on);
        } else {
            toggleLight.setChecked(false);
            lightImg.setImageResource(R.drawable.off);
        }

        // PROGRESS BAR LOGIC
        if (toggleDoor.isChecked() && toggleLight.isChecked()) {
            progressBar.setProgress(60);
        } else if (!toggleDoor.isChecked() && toggleLight.isChecked()) {
            progressBar.setProgress(30);
        } else if (toggleDoor.isChecked() && !toggleLight.isChecked()) {
            progressBar.setProgress(30);
        } else {
            progressBar.setProgress(0);
        }

        // DOOR LISTENER
        toggleDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // The toggle is enabled
                    //Log.d("Garage", "Door Toggle On");
                    doorImg.setImageResource(R.drawable.open);
                    lightImg.setImageResource(R.drawable.on);
                    if (!toggleLight.isChecked()) {
                        Snackbar.make(buttonView, getString(R.string.gLightOn), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    toggleLight.setChecked(true);
                    // PROGRESS BAR LOGIC
                    if (toggleDoor.isChecked() && toggleLight.isChecked()) {
                        progressBar.setProgress(60);
                        } else if (!toggleDoor.isChecked() && toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else if (toggleDoor.isChecked() && !toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else {
                            progressBar.setProgress(0);
                        }
                    editor.putBoolean("GarageDoor", true);
                    editor.commit();

                } else { // The toggle is disabled
                    //Log.d("Garage", "Door Toggle Off");
                    doorImg.setImageResource(R.drawable.closed);
                    // PROGRESS BAR LOGIC
                    if (toggleDoor.isChecked() && toggleLight.isChecked()) {
                        progressBar.setProgress(60);
                        } else if (!toggleDoor.isChecked() && toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else if (toggleDoor.isChecked() && !toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else {
                            progressBar.setProgress(0);
                        }
                    editor.putBoolean("GarageDoor", false);
                    editor.commit();
                }
            }
        });

        // LIGHT LISTENER
        toggleLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    //Log.d("Garage", "Light Toggle On");
                    lightImg.setImageResource(R.drawable.on);
                    // PROGRESS BAR LOGIC
                    if (toggleDoor.isChecked() && toggleLight.isChecked()) {
                        progressBar.setProgress(60);
                        } else if (!toggleDoor.isChecked() && toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else if (toggleDoor.isChecked() && !toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else {
                            progressBar.setProgress(0);
                        }
                    editor.putBoolean("GarageLight", true);
                    editor.commit();
                } else {
                    // The toggle is disabled
                    //Log.d("Garage", "Light Toggle Off");
                    lightImg.setImageResource(R.drawable.off);
                    // PROGRESS BAR LOGIC
                    if (toggleDoor.isChecked() && toggleLight.isChecked()) {
                        progressBar.setProgress(60);
                        } else if (!toggleDoor.isChecked() && toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else if (toggleDoor.isChecked() && !toggleLight.isChecked()) {
                            progressBar.setProgress(30);
                        } else {
                            progressBar.setProgress(0);
                        }
                    editor.putBoolean("GarageLight", false);
                    editor.commit();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
} // End of Garage.java