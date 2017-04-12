package com.example.yah.androidfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

// Living Room TV Fragment by Chris Billings

public class TV extends Fragment {
    //String for housing the bundle argument that gets passed from the activity to the fragment.
    String str;
    //The Boolean passed if a key/value set needs to be changed.
    boolean unShare;
    //The EditText for holding the channel.
    EditText channel;

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
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        //The header text which is based on the passed Listview clicked Item Name.
        TextView title = (TextView) view.findViewById(R.id.tv);
        title.setText(str);
        //Edit Text for Channel Entry
        channel = (EditText) view.findViewById(R.id.channel);
        //Uses sharedPreferences to store the seekbar progress.
        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        //Will remove the key/value set if the boolean has been set to true.
     if(unShare){
            preferences.edit().remove(str).commit();
        }

        //Gets the last channel selected from shared prefs.
        String lastChannel = preferences.getString(str, "");
        channel.setText(lastChannel);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //OnDestroy store the last channel selected in shared prefs.
        SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(str, String.valueOf(channel.getText()));

        editor.commit();
    }

}
