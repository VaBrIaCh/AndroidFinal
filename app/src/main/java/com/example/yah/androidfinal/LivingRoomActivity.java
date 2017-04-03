package com.example.yah.androidfinal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class LivingRoomActivity extends AppCompatActivity {
private Toolbar toolBar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        toolBar = (Toolbar) findViewById(R.id.mainToolbar);
    }

    public void ChangeFragment (View view){


        if(view == findViewById(R.id.LivingButton)){
            fragment  = new Lamp1();
        }

        if(view == findViewById(R.id.kitchenButton)){
            fragment  = new Lamp2();
        }

        if(view == findViewById(R.id.homeButton)){
                fragment = new Lamp3();
        }

        if(view == findViewById(R.id.garageButton)) {
            fragment = new Blinds();
        }

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentPlace, fragment);
            ft.commit();

    }

}
