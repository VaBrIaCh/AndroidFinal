package com.example.yah.androidfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

// Living Room Blinds Fragment by Chris Billings

public class Blinds extends Fragment {
    //Uses Reshaped buttons as the segmented blinds.
    Button blind1, blind2, blind3, blind4, blind5, blind6, blind7, blind8, blind9, blind10;
    //String for housing the bundle argument that gets passed from the activity to the fragment.
    String str;
    //An integer for holding the seekbar progress value.
    int pro;
    //The Boolean passed if a key/value set needs to be changed.
    boolean unShare;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        //Get the activity arguments for use in the fragment.
        Bundle args =  this.getArguments();
        str= args.getString("deviceName");
        unShare = args.getBoolean("delete");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_blinds, container, false);
        //The header text which is based on the passed Listview clicked Item Name.
        TextView title = (TextView) rootView.findViewById(R.id.Bheader);
        title.setText(str);
        //Shows the seek bar value percentage as text.
        final TextView seekBarValueB = (TextView) rootView.findViewById(R.id.seekTextB);
        //The vertical seekbar slider.
        SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekBarB);

        //Uses sharedPreferences to store the seekbar progress.
        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        //Will remove the key/value set if the boolean has been set to true.
        if(unShare){
            preferences.edit().remove(str).commit();
        }

        //Gets the last seekbar value from sharedpreferences.
        final String lastProgress = preferences.getString(str, "0");
        //Adds percentage after the fact so the int is standalone for methods that use it.
        String plusPercent = lastProgress+"%";
        //Textview uses progress + %
        seekBarValueB.setText(plusPercent);

        //Set the progressBar to it's last known set value and reflect it in the % text.
        seekbar = (SeekBar) rootView.findViewById(R.id.seekBarB);
        seekbar.setProgress(Integer.valueOf(lastProgress));

        //Instantiate the various blinds buttons.
        blind1 = (Button) rootView.findViewById(R.id.blind1);
        blind2 = (Button) rootView.findViewById(R.id.blind2);
        blind3 = (Button) rootView.findViewById(R.id.blind3);
        blind4 = (Button) rootView.findViewById(R.id.blind4);
        blind5 = (Button) rootView.findViewById(R.id.blind5);
        blind6 = (Button) rootView.findViewById(R.id.blind6);
        blind7 = (Button) rootView.findViewById(R.id.blind7);
        blind8 = (Button) rootView.findViewById(R.id.blind8);
        blind9 = (Button) rootView.findViewById(R.id.blind9);
        blind10 = (Button) rootView.findViewById(R.id.blind10);

        //Converts the String value of the last known seekbar position to Int for methods.
        pro = Integer.valueOf(lastProgress);
        //Runs the checkPro method which will decide what colour the blinds should be to represent whether they are drawn to block the light coming in the window or not.
        checkPro();

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                pro = progress;
                seekBarValueB.setText(String.valueOf(progress)+"%");
                checkPro();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekbar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekbar){
                //When you stop moving the slider put the value into sharedPrefs.
                SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(str, String.valueOf(seekbar.getProgress()));
                editor.commit();
            }
        });

        return rootView;
    }

    public void checkPro(){
        //Sets all blinds to Sky colour by default. PorterDuff is to maintain the button dimensions. If it's not there the height stretches and they overwrite each other.
        blind1.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind2.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind3.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind4.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind5.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind6.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind7.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind8.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind9.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        blind10.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);

        //Conditionals that will turn the blind segments white to simulate them being blinds convering the windows based on the percentage how far down they are.
        if(pro>10){ blind1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>20){blind2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>30){blind3.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>40){blind4.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>50){blind5.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>60){blind6.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>70){blind7.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>80){blind8.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro>90){blind9.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
        if(pro==100){blind10.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);}
    }
}
