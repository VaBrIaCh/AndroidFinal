package com.example.yah.androidfinal;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Timer;

public class KitchenMicrowave extends Fragment {

    String s;
    KitchenActivity kitchenActivity;
    Fragment fragmentKitchenMicrowave;
    String timer = "00:00";
    TextView timerDisplay;
    Boolean timerReset = false;
    View rootView;



    public KitchenMicrowave() {
    }

    public KitchenMicrowave(KitchenActivity activity) {
        kitchenActivity = activity;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Bundle args = this.getArguments();
        s = args.getString("deviceName");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_kitchen_microwave, container, false);

        //The header text which is based on the passed Listview clicked Item Name.
        TextView title = (TextView) rootView.findViewById(R.id.text_kitchen_lights);
        title.setText(s);


        //If we press the back button it will remove the fragment and return to main screen
        ImageButton backArrow = (ImageButton) rootView.findViewById(R.id.button_left_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchenActivity.removeCurrentFragment();

            }
        });

        //define buttons
        Button bm1 = (Button) rootView.findViewById(R.id.bm1);
        Button bm2 = (Button) rootView.findViewById(R.id.bm2);
        Button bm3 = (Button) rootView.findViewById(R.id.bm3);
        Button bm4 = (Button) rootView.findViewById(R.id.bm4);
        Button bm5 = (Button) rootView.findViewById(R.id.bm5);
        Button bm6 = (Button) rootView.findViewById(R.id.bm6);
        Button bm7 = (Button) rootView.findViewById(R.id.bm7);
        Button bm8 = (Button) rootView.findViewById(R.id.bm8);
        Button bm9 = (Button) rootView.findViewById(R.id.bm9);
        Button bm0 = (Button) rootView.findViewById(R.id.bm0);
        Button bmS = (Button) rootView.findViewById(R.id.bmS);
        timerDisplay = (TextView) rootView.findViewById(R.id.kitchen_microwave_timer);
        timerDisplay.setText((timer));
        //use buttons to add time
        bm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               totalTime(1);
            }
        });
        bm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(2);
            }
        });
        bm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(3);
            }
        });
        bm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(4);
            }
        });
        bm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(5);
            }
        });
        bm6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(6);
            }
        });
        bm7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(7);
            }
        });
        bm8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(8);
            }
        });
        bm9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(9);
            }
        });
        bm0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalTime(0);
            }
        });


        //use start button to countdown
        bmS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countdown(timer);
            }
        });



        return rootView;
    }



    public String totalTime(int i){
        if(!timerReset){
            timer = "";
            timerReset = true;
        }
        if (timer.length() < 5) {
            if (timer.length() == 2){
                timer += ":";
            }
            timer += String.valueOf(i);
            timerDisplay.setText((timer));
        }
        return timer;
    }

    public String countdown(String t){

        Long min;
        Long sec;
        String[] timeSplit;
        final String formatTimer;
        if(t.length() >= 3) {
            timeSplit = t.split(":");
            min = Long.valueOf(timeSplit[0])*60000;
            sec = Long.valueOf(timeSplit[1])*1000;

        }
        else{
            min = Long.valueOf(0);
            sec = Long.valueOf(t)*1000;

        }


        //Retrieved from https://developer.android.com/reference/android/os/CountDownTimer.html
        new CountDownTimer((min+sec), 1000) {

            public void onTick(long millisUntilFinished) {
                timerDisplay.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerDisplay.setText("END");

                Vibrator mVibrator = (Vibrator) kitchenActivity.getSystemService(Context.VIBRATOR_SERVICE);

                mVibrator.vibrate(500);
            }
        }.start();


        return t;
    }

}
