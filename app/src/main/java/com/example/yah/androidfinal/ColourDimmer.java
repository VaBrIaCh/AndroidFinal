package com.example.yah.androidfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

// Living Room Coloured Dimmer Fragment by Chris Billings

public class ColourDimmer extends Fragment {
    //String for housing the bundle argument that gets passed from the activity to the fragment.
    String str;
    //The Boolean passed if a key/value set needs to be changed.
    boolean unShare;
    //The seekBar slider
    SeekBar seekbar;
    //Holds the last colour selected
    int colour;
    //Button to represent the "light" to show colour change.
    Button colorChange;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        //Get the activity arguments for use in the fragment.
        Bundle args =  this.getArguments();
        str= args.getString("deviceName");
        unShare = args.getBoolean("delete");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_lamp3, container, false);
        //The header text which is based on the passed Listview clicked Item Name.
        TextView title = (TextView) rootView.findViewById(R.id.lamp3);
        title.setText(str);
        //Shows the seek bar value percentage as text.
        final TextView seekBarValue3 = (TextView) rootView.findViewById(R.id.seekText2);
        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar3);

        //Uses sharedPreferences to store the seekbar progress.
        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        //Will remove the key/value set if the boolean has been set to true.
        if(unShare){
            preferences.edit().remove(str).commit();
            preferences.edit().remove("colour").commit();
        }

        //Gets the last seekbar value from sharedpreferences.
        String lastProgress = preferences.getString(str, "0");
        //Adds percentage after the fact so the int is standalone for methods that use it.
        String plusPercent = lastProgress+"%";
        //Textview uses progress + %
        seekBarValue3.setText(plusPercent);

        //Set the progressBar to it's last known set value and reflect it in the % text.
        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar3);
        seekbar.setProgress(Integer.valueOf(lastProgress));

        //Sets the colour of the "light" button by last known value from shared prefs.
        colorChange = (Button) rootView.findViewById(R.id.colorChange);
        colour = preferences.getInt("colour", Color.WHITE);
        colorChange.setBackgroundColor(colour);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                seekBarValue3.setText(String.valueOf(progress)+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekbar){}
        });

        //CREATES THE COLOUR PALLETE. A set of different coloured buttons that will change the colour of the "light" when pressed. Color.etc returns an int so colour is stores that int value.
        Button color1 = (Button) rootView.findViewById(R.id.color7);
        color1.setBackgroundColor(Color.BLUE);
        color1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.BLUE);
                colour=Color.BLUE;
            }
        });


        Button color2 = (Button) rootView.findViewById(R.id.color1);
        color2.setBackgroundColor(Color.YELLOW);
        color2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.YELLOW);
                colour=Color.YELLOW;
            }
        });

        Button color3 = (Button) rootView.findViewById(R.id.color8);
        color3.setBackgroundColor(Color.GREEN);
        color3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.GREEN);
                colour=Color.GREEN;
            }
        });


        Button color4 = (Button) rootView.findViewById(R.id.color3);
        color4.setBackgroundColor(Color.RED);
        color4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.RED);
                colour=Color.RED;
            }
        });

        Button color5 = (Button) rootView.findViewById(R.id.color4);
        color5.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        color5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
                colour=getResources().getColor(android.R.color.holo_purple);
            }
        });

        Button color6 = (Button) rootView.findViewById(R.id.color5);
        color6.setBackgroundColor(Color.WHITE);
        color6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.WHITE);
                colour=Color.WHITE;
            }
        });

        Button color7 = (Button) rootView.findViewById(R.id.color6);
        color7.setBackgroundColor(Color.CYAN);
        color7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.CYAN);
                colour=Color.CYAN;
            }
        });

        Button color8 = (Button) rootView.findViewById(R.id.color2);
        color8.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        color8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                colour=getResources().getColor(android.R.color.holo_orange_light);
            }
        });

        return rootView;
    }

@Override
    public void onDestroy(){
    super.onDestroy();
    //When you stop moving the slider put the value into sharedPrefs.
    SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(str, String.valueOf(seekbar.getProgress()));
    editor.putInt("colour", colour);
    editor.commit();
}


}
