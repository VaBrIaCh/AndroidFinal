package com.example.yah.androidfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

// Living Room Dimmer Fragment by Chris Billings

public class Dimmer extends Fragment {
    //String for housing the bundle argument that gets passed from the activity to the fragment.
    String str;
    //The Boolean passed if a key/value set needs to be changed.
    boolean unShare;
    //The seekBar slider
    SeekBar seekbar;

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
        View rootView =  inflater.inflate(R.layout.fragment_lamp2, container, false);
        //The header text which is based on the passed Listview clicked Item Name.
        TextView title = (TextView) rootView.findViewById(R.id.lamp2);
        title.setText(str);
        //Shows the seek bar value percentage as text.
        final TextView seekBarValue = (TextView) rootView.findViewById(R.id.seekText);

        //Uses sharedPreferences to store the seekbar progress.
        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        //Will remove the key/value set if the boolean has been set to true.
        if(unShare){
            preferences.edit().remove(str).commit();
        }

        //Gets the last seekbar value from sharedpreferences.
        String lastProgress = preferences.getString(str, "0");
        //Adds percentage after the fact so the int is standalone for methods that use it.
        String plusPercent = lastProgress+"%";
        //Textview uses progress + %
        seekBarValue.setText(plusPercent);

        //Set the progressBar to it's last known set value and reflect it in the % text.
        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar2);
        seekbar.setProgress(Integer.valueOf(lastProgress));


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
         @Override
                 public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                    seekBarValue.setText(String.valueOf(progress)+"%");
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
}
