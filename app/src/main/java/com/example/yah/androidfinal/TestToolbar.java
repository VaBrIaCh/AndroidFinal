package com.example.yah.androidfinal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

// Toolbar Modified from Lab 8 by Chris Billings.

public class TestToolbar extends AppCompatActivity {
    // Holds the intent for whichever activity is selected based on the menu icons.
    Intent changer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This is added in to attempt to hide the very top bar because we're hiding the action bar.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {

        switch (mi.getItemId()) {
            case R.id.home:
                Log.d("Toolbar", "Option 1 selected");

                break;

            case R.id.living:
                //// TODO: For your Activities switch the 2nd parameter to the name of your main home screen activity.
                changer = new Intent(this, LivingRoomActivity.class);
                startActivity(changer);
                break;

            case R.id.kitchen:
                Log.d("Toolbar", "Option 3 selected");

                break;


            case R.id.garage:
                Log.d("Toolbar", "Option 3 selected");

                break;


            case R.id.about:
                //todo: REQUIREMENT 10: Uses a custom builder dialogue box to hold the help/about information.
                android.app.AlertDialog.Builder customBuilder = new android.app.AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View rootView = inflater.inflate(R.layout.help_alert, null);

                //Textviews that house the header and body text messages for the about help screen.
                TextView helpTop = (TextView) rootView.findViewById(R.id.helpHeader);
                TextView helpMid = (TextView) rootView.findViewById(R.id.helpBody);

                //This gets the name of the current class the about menu is being called from.
                Activity host = (Activity) rootView.getContext();

                //// TODO: If you make your <string> files in the Strings.xml you can use instanceof (YOUR ACTIVITY NAME) and then change the setTexts to your Strings.  
                //For the LivingRoomActivity it gets set to the standard overall header and my living room help string from strings.xml
                if (host instanceof LivingRoomActivity) {
                    helpTop.setText(R.string.menu_help);
                    helpMid.setText(R.string.living_help);
                }

                customBuilder.setView(rootView)
                        // Add action buttons
                        .setPositiveButton(getResources().getString(R.string.okayButton), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                customBuilder.create();
                customBuilder.show();
                break;
        }
        return true;
    }
}
