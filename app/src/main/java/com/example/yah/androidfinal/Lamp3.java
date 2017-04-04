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


public class Lamp3 extends Fragment {

    String str;
    String str2;
    int colour;
    String DEFAULT="0";
    SeekBar seekbar;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        Bundle args =  this.getArguments();
        str= args.getString("deviceName");
        String str2 = str+"c";
    }


    public Button colorChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_lamp3, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.lamp3);
        title.setText(str);

        final TextView seekBarValue3 = (TextView) rootView.findViewById(R.id.seekText2);
        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar3);

        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        String lastProgress = preferences.getString(str, "0");
        String plusPercent = lastProgress+"%";
        seekBarValue3.setText(plusPercent);

        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar3);
        seekbar.setProgress(Integer.valueOf(lastProgress));

        colorChange = (Button) rootView.findViewById(R.id.colorChange);
        int blueValue = Color.BLUE;
        int lastColour = preferences.getInt(str2, blueValue);

        colorChange.setBackgroundColor(lastColour);


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


//        Button color1 = (Button) rootView.findViewById(R.id.color1);
  //      color1.setBackgroundColor(Color.BLUE);




        return rootView;

    }

@Override
    public void onDestroy(){
    super.onDestroy();
    SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(str, String.valueOf(seekbar.getProgress()));
    editor.putInt(str2, colour);
    editor.commit();
}


}
