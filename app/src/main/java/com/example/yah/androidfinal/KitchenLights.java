package com.example.yah.androidfinal;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class KitchenLights extends Fragment {

    String s;
    Boolean b;
    ToggleButton lightSwitch;
    SeekBar dimmer;
    KitchenActivity kitchenActivity;
    Fragment fragmentKitchenLights;

    public KitchenLights(){}

    public KitchenLights(KitchenActivity activity){
        kitchenActivity = activity;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Bundle args =  this.getArguments();
        s = args.getString("deviceName");


        //fragmentKitchenLights = new KitchenLightsAdd(KitchenLights.this);

//        Bundle clickedInfo = new Bundle();
//        clickedInfo.putString("deviceName", "Add Light");
//        fragmentKitchenLights.setArguments(clickedInfo);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kitchen_lights, container, false);

        //The header text which is based on the passed Listview clicked Item Name.
        TextView title = (TextView) rootView.findViewById(R.id.text_kitchen_lights);
        title.setText(s);

        //Shows the seek bar value percentage as text.
        final TextView dimmerPercentage = (TextView) rootView.findViewById(R.id.light_dim_percentage);
        lightSwitch = (ToggleButton) rootView.findViewById(R.id.kitchen_light_switch);

        //If we press the back button it will remove fragment go back to the main screen.
        ImageButton backArrow = (ImageButton) rootView.findViewById(R.id.button_left_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchenActivity.removeCurrentFragment();

            }
        });



        final SharedPreferences preferences = getActivity().getSharedPreferences(s, Context.MODE_PRIVATE);



            //Boolean for whether the toggle button is off or on.
            //Boolean check = preferences.getBoolean(s, false);
            //lightSwitch.setChecked(check);


        //TODO DIMMER functionality


        //Set the progressBar to it's last known set value and reflect it in the % text.
        dimmer = (SeekBar) rootView.findViewById(R.id.kitchen_light_seekBar);


        dimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                dimmerPercentage.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekbar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekbar){
                //When you stop moving the slider put the value into sharedPrefs.
                SharedPreferences sharedPref = getActivity().getSharedPreferences(s, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(s, String.valueOf(seekbar.getProgress()));
                editor.commit();
            }
        });



//---------------------------------------------

//TODO Functionality:


        Button lightRemove = (Button) rootView.findViewById(R.id.button_control_light_remove);
        lightRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                preferences.edit().remove(s).commit();

            }
        });




        return rootView;
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        //When the fragment is exited we save the button state in shared prefs.
        SharedPreferences sharedPref = getActivity().getSharedPreferences(s, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(s, lightSwitch.isChecked());
        editor.commit();
    }




    public void removeCurrentFragment(){
        fragmentKitchenLights.getFragmentManager().beginTransaction()
                .remove(fragmentKitchenLights)
                .commit();
    }


}
