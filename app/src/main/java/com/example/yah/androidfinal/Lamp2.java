package com.example.yah.androidfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class Lamp2 extends Fragment {


    String str;
    SeekBar seekbar;
    String DEFAULT="0";
    //LivingRoomActivity lr;

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
        View rootView =  inflater.inflate(R.layout.fragment_lamp2, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.lamp2);
        title.setText(str);

        final TextView seekBarValue = (TextView) rootView.findViewById(R.id.seekText);

        SharedPreferences preferences = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
        SharedPreferences preferences3;
        preferences3 = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);

        String lastProgress = preferences.getString(str, DEFAULT);
        String plusPercent = lastProgress+"%";

        seekBarValue.setText(plusPercent);

        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar2);

        seekbar.setProgress(Integer.valueOf(lastProgress));
      //  lr = new LivingRoomActivity();

//int seek=50;
//try{              seek = lr.cursor.getInt(lr.cursor.getColumnIndex(lr.deviceHelper.KEY_seek));

//              Log.i("LR", String.valueOf(lr.cursor.getInt(lr.cursor.getColumnIndex(lr.deviceHelper.KEY_seek))));
  //            Log.i("LR", String.valueOf(seek));

            //   int count = (Integer)listView.getItemAtPosition(position);
            //  int count =0;
            //  count++;

            // int count2 = map.get(DevName);
//                Toast.makeText(getBaseContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();

        //    String strFilter = KEY_Selected;





  //          seekbar.setProgress(seek); //}catch(Exception e){}



        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

         @Override
                 public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                    seekBarValue.setText(String.valueOf(progress)+"%");
            }

         @Override
            public void onStartTrackingTouch(SeekBar seekbar){}

         @Override
            public void onStopTrackingTouch(SeekBar seekbar){
            // ContentValues args = new ContentValues();
            // args.put(deviceHelper.KEY_seek, seekbar.getProgress());

//            lr.devices.update(TABLE_NAME, args, KEY_Name + "=?", new String[]{lr.DevName});

             SharedPreferences sharedPref = getActivity().getSharedPreferences(str, Context.MODE_PRIVATE);
             SharedPreferences.Editor editor = sharedPref.edit();
             editor.putString(str, String.valueOf(seekbar.getProgress()));
             editor.commit();
         }

        });


    return rootView;

    }

/* @Override
    public void onDestroy(){

    Fragment frag = new Fragment();
    Bundle clickedInfo = new Bundle();
    clickedInfo.putInt("seek", seekbar.getProgress());
    //clickedInfo.putInt("seek",);
    frag.setArguments(clickedInfo);
} */


}
