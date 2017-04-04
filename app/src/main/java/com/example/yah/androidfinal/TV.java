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

public class TV extends Fragment {

    String str;
    EditText channel;

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
        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        TextView title = (TextView) view.findViewById(R.id.tv);
        title.setText(str);

        channel = (EditText) view.findViewById(R.id.channel);

        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        String lastChannel = preferences.getString(str, "");

        channel.setText(lastChannel);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(str, String.valueOf(channel.getText()));

        editor.commit();
    }

}
