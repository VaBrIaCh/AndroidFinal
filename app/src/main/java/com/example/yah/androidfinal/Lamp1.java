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


public class Lamp1 extends Fragment {

    String str;
    ToggleButton toggle;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        Bundle args =  this.getArguments();
        str= args.getString("deviceName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lamp1, container, false);

        TextView title = (TextView) view.findViewById(R.id.lamp1);
        title.setText(str);

        toggle = (ToggleButton) view.findViewById(R.id.toggleButton);

        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        Boolean check = preferences.getBoolean(str, false);

        toggle.setChecked(check);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(str, toggle.isChecked());
        editor.commit();
    }

}
