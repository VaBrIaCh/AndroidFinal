package com.example.yah.androidfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.yah.androidfinal.DeviceDatabaseHelper.KEY_Name;
import static com.example.yah.androidfinal.DeviceDatabaseHelper.TABLE_NAME;
import static com.example.yah.androidfinal.R.layout.dialog;


public class LivingRoomActivity extends AppCompatActivity {
private Toolbar toolBar;
    Fragment fragment;
    Spinner spinner1;

    ArrayList<String> interfaces = new ArrayList<>();
    ArrayList<String> types = new ArrayList<>();

    ListView listView;
    protected ChatAdapter devicesAdapter;
    String newDevice;
    String deviceType;
    String typeForCondition;
    String DevName;

    protected static DeviceDatabaseHelper deviceHelper;
    SQLiteDatabase devices;
    protected Cursor cursor;
protected ContentValues values;

    private boolean isFrameUsed;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);


        frameLayout = (FrameLayout)findViewById(R.id.messageFrag);

        if(frameLayout==null){
            isFrameUsed=false;
            Log.i("LivingRoomActivity", "You are using the phone layout");
        }
        else{
            isFrameUsed=true;
            Log.i("LivingRoomActivity", "You are using the tablet layout");
        }


     //   toolBar = (Toolbar) findViewById(R.id.mainToolbar);
        listView = (ListView) findViewById(R.id.livingListview);

        devicesAdapter = new ChatAdapter(this);
        listView.setAdapter(devicesAdapter);

        deviceHelper = new DeviceDatabaseHelper(this);
        devices = deviceHelper.getWritableDatabase();

        cursor = deviceHelper.getMessages();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            interfaces.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Name)));
            types.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Device)));
            //idLog.add(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
          //  Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(chathelper.KEY_MESSAGE)));
            cursor.moveToNext();}

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent , View v, int position, final long id) {
                Log.d("test", "clicked");


                DevName = (String) interfaces.get(position);
                //ChangeFragment();
                typeForCondition = (String) types.get(position);

                //  String x = (String)listView.getItemAtPosition(position);
//                int count = 0;
                // count = map.get(x);
                // map.put(DevName, (count + 1));
                cursor.moveToFirst();
                cursor.moveToPosition(position);


                try {
                    int count = cursor.getInt(cursor.getColumnIndex(deviceHelper.KEY_Selected));
                    count++;

                   // int seek = cursor.getInt(cursor.getColumnIndex(deviceHelper.KEY_seek));

                    Log.i("LR", String.valueOf(cursor.getInt(cursor.getColumnIndex(deviceHelper.KEY_Selected))));
                    Log.i("LR", String.valueOf(count));

                    //   int count = (Integer)listView.getItemAtPosition(position);
                    //  int count =0;
                    //  count++;

                    // int count2 = map.get(DevName);
//                Toast.makeText(getBaseContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();

                   // String strFilter = KEY_Selected;

                    ContentValues args = new ContentValues();
                    args.put(deviceHelper.KEY_Selected, count);


                devices.update(TABLE_NAME, args, KEY_Name + "=?", new String[]{DevName});

                interfaces.clear();
                types.clear();


                cursor = deviceHelper.getMessages();
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    interfaces.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Name)));
                    types.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Device)));
                    //idLog.add(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                    //  Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(chathelper.KEY_MESSAGE)));
                    cursor.moveToNext();
                }

                devicesAdapter.notifyDataSetChanged();

            }catch(Exception e){}

                // ChangeFragment(devicesAdapter.getView(position, v, parent));

               if(typeForCondition.contentEquals("Blinds")) {
                   fragment = new Blinds();
               }

                if(typeForCondition.contentEquals("TV")) {
                    fragment = new TV();
                }


                if(typeForCondition.contentEquals("On/Off Lamp")) {
                    fragment = new Lamp1();
                }

                if(typeForCondition.contentEquals("Dimmer Light")) {
                    fragment = new Lamp2();

                }

                if(typeForCondition.contentEquals("Coloured Dimmer Light")) {
                    fragment = new Lamp3();

                }


                Bundle clickedInfo = new Bundle();
                clickedInfo.putString("deviceName", DevName);
                //clickedInfo.putInt("seek",);

                fragment.setArguments(clickedInfo);

                if(fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.messageFrag, fragment);
                    ft.commit();
                }
            }

        });



        Button addButton = (Button) findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

        AlertDialog.Builder customBuilder = new AlertDialog.Builder(view.getContext());
        // Get the layout inflater
        LayoutInflater inflater = LivingRoomActivity.this.getLayoutInflater();


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        final View rootView = inflater.inflate(dialog, null);
        final EditText newText = (EditText) rootView.findViewById(R.id.dialogText);
                newText.setText("");

                spinner1 = (Spinner) rootView.findViewById(R.id.spinner);
//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.LivingTypes, android.R.layout.simple_spinner_dropdown_item);

  //              spinner1.setAdapter(adapter);

                spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());


                customBuilder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                                TextView newText = (TextView) findViewById(R.id.dialogText);
                     //   newTask1msg= newText.getText().toString();

                        newDevice = newText.getText().toString();
                        interfaces.add(newDevice);

                        deviceType = String.valueOf(spinner1.getSelectedItem());
                        types.add(deviceType);

                        Toast.makeText(LivingRoomActivity.this, "Device type is:" + String.valueOf(spinner1.getSelectedItem()), Toast.LENGTH_SHORT).show();

                        values = new ContentValues();
                        values.put(deviceHelper.KEY_Name, newDevice);
                        values.put(deviceHelper.KEY_Device, deviceType);
                        values.put(deviceHelper.KEY_Selected, 0);
                        values.put(deviceHelper.KEY_seek, 50);

                        devices.insert(TABLE_NAME, KEY_Name, values);
 //                       devices.insert(TABLE_NAME, KEY_Device, values);


                        devicesAdapter.notifyDataSetChanged();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        customBuilder.create();

        customBuilder.show();

            }
        });


        Button delButton = (Button) findViewById(R.id.remove);

        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {Long.toString(id)});
                devices.delete(TABLE_NAME, KEY_Name + "=?", new String[] {DevName});
                interfaces.clear();
                types.clear();


                cursor = deviceHelper.getMessages();
                cursor.moveToFirst();

                while(!cursor.isAfterLast()){
                    interfaces.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Name)));
                    types.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Device)));
                    //idLog.add(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                    //  Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(chathelper.KEY_MESSAGE)));
                    cursor.moveToNext();}

                devicesAdapter.notifyDataSetChanged();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                ft.commit();

            }
        });


        if(isFrameUsed) {

            //ChatWindow cw = new ChatWindow();


            fragment = new Fragment();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.messageFrag, fragment).addToBackStack(null);
            ft.commit();

            devicesAdapter.notifyDataSetChanged();
        }



    }

/*    public void ChangeFragment (View view){


        if(view == findViewById(R.id.LivingButton)){
            fragment  = new Lamp1();
        }

        if(view == findViewById(R.id.kitchenButton)){
            fragment  = new Lamp2();
        }

        if(view == findViewById(R.id.homeButton)){
                fragment = new Lamp3();
        }

        if(view == findViewById(R.id.garageButton)) {
            fragment = new Blinds();
        }

        if(view == findViewById(R.id.remove)) {
            fragment = new TV();
        }

        if(view == findViewById(R.id.devices_list)){
            fragment = new Blinds();

        }
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentPlace, fragment);
            ft.commit();
 */

    public static class CustomDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder customBuilder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            customBuilder.setView(inflater.inflate(dialog, null))
                    // Add action buttons
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                                     //newDevice = newText.getText().toString();
                        }

                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            return customBuilder.create();
        }
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int  getCount(){
            return interfaces.size();
        }

        public String getItem(int position){
            return interfaces.get(position);
        }

//        public long getItemId(int position){ return idLog.get(position);}

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = LivingRoomActivity.this.getLayoutInflater();
            View result = null;

                result = inflater.inflate(R.layout.deviceslist, null);

            TextView message = (TextView) result.findViewById(R.id.devices_list);
            message.setText( getItem(position));
            return result;
        }

    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
            String deviceHolder = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0){
        }
    }


}
