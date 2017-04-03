package com.example.yah.androidfinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class Lamp2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_lamp2, container, false);

        final TextView seekBarValue = (TextView) rootView.findViewById(R.id.seekText);

        SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekBar2);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

         @Override
                 public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                    seekBarValue.setText(String.valueOf(progress)+"%");
            }

         @Override
            public void onStartTrackingTouch(SeekBar seekbar){}

         @Override
            public void onStopTrackingTouch(SeekBar seekbar){}

        });


    return rootView;

    }




}
