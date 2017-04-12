package com.example.yah.androidfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;


public class OnOffLamp extends Fragment {
    //String for housing the bundle argument that gets passed from the activity to the fragment.
    String str;
    //The Boolean passed if a key/value set needs to be changed.
    boolean unShare;
    //The ToggleButton for keeping it on.
    ToggleButton toggle;

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
        View view = inflater.inflate(R.layout.fragment_lamp1, container, false);
        //The header text which is based on the passed Listview Item Name.
        TextView title = (TextView) view.findViewById(R.id.lamp1);
        title.setText(str);
        //The centered toggle Button.
        toggle = (ToggleButton) view.findViewById(R.id.toggleButton);
        //Uses sharedPreferences to store the seekbar progress.
        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        //Will remove the key/value set if the boolean has been set to true.
        if(unShare){preferences.edit().remove(str).commit();}

        //Boolean for whether the toggle button is off or on.
        Boolean check = preferences.getBoolean(str, false);
        toggle.setChecked(check);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //When the fragment is exited we save the button state in shared prefs.
        SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(str, toggle.isChecked());
        editor.commit();
    }

}
