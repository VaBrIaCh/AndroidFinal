package com.example.yah.androidfinal;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class Lamp3 extends Fragment {

    public Button colorChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_lamp3, container, false);

        final TextView seekBarValue3 = (TextView) rootView.findViewById(R.id.seekText2);

        SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekBar3);

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


        colorChange = (Button) rootView.findViewById(R.id.colorChange);
        colorChange.setBackgroundColor(Color.BLUE);


        Button color1 = (Button) rootView.findViewById(R.id.color7);
        color1.setBackgroundColor(Color.BLUE);
        color1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.BLUE);
            }
        });


        Button color2 = (Button) rootView.findViewById(R.id.color1);
        color2.setBackgroundColor(Color.YELLOW);
        color2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.YELLOW);
            }
        });

        Button color3 = (Button) rootView.findViewById(R.id.color8);
        color3.setBackgroundColor(Color.GREEN);
        color3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.GREEN);
            }
        });


        Button color4 = (Button) rootView.findViewById(R.id.color3);
        color4.setBackgroundColor(Color.RED);
        color4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.RED);
            }
        });

        Button color5 = (Button) rootView.findViewById(R.id.color4);
        color5.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        color5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
            }
        });

        Button color6 = (Button) rootView.findViewById(R.id.color5);
        color6.setBackgroundColor(Color.WHITE);
        color6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.WHITE);
            }
        });

        Button color7 = (Button) rootView.findViewById(R.id.color6);
        color7.setBackgroundColor(Color.CYAN);
        color7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(Color.CYAN);
            }
        });

        Button color8 = (Button) rootView.findViewById(R.id.color2);
        color8.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        color8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                colorChange.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
            }
        });


//        Button color1 = (Button) rootView.findViewById(R.id.color1);
  //      color1.setBackgroundColor(Color.BLUE);




        return rootView;

    }




}
