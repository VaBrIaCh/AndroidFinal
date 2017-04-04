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


public class Blinds extends Fragment {
    Button blind1;
    Button blind2;
    Button blind3;
    Button blind4;
    Button blind5;
    Button blind6;
    Button blind7;
    Button blind8;
    Button blind9;
    Button blind10;

    String str;
    int pro;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        Bundle args =  this.getArguments();
        str= args.getString("deviceName");
       // pro= args.getInt("seek");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_blinds, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.Bheader);
        title.setText(str);

        final TextView seekBarValueB = (TextView) rootView.findViewById(R.id.seekTextB);

        SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekBarB);

        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        final String lastProgress = preferences.getString(str, "0");
        String plusPercent = lastProgress+"%";
        seekBarValueB.setText(plusPercent);

        seekbar = (SeekBar) rootView.findViewById(R.id.seekBarB);
        seekbar.setProgress(Integer.valueOf(lastProgress));

       // seekbar.setProgress();


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

        pro = Integer.valueOf(lastProgress);
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

                SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(str, String.valueOf(seekbar.getProgress()));
                editor.commit();

            }

        });

        return rootView;

    }

    public void checkPro(){

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


        if(pro>10){
            //   blind1.setBackgroundColor(getResources().getColor(android.R.color.white));
            blind1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        }

        if(pro>20){
            blind2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro>30){
            blind3.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro>40){
            blind4.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro>50){
            blind5.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro>60){
            blind6.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro>70){
            blind7.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro>80){
            blind8.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro>90){
            blind9.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        if(pro==100){
            blind10.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

    }

}
