package com.example.yah.androidfinal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.example.yah.androidfinal.DeviceDatabaseHelper.KEY_Name;
import static com.example.yah.androidfinal.DeviceDatabaseHelper.TABLE_NAME;
import static com.example.yah.androidfinal.R.layout.dialog;

//todo: REQUIREMENT 1: LIVING ROOM SMART HOME ACTIVITY TASK BY CHRIS BILLINGS. SELECTABLE IN TestToolbar.
//// TODO: Changing the extends to TestToolbar rather than "AppCompat" in your main Activity files will incorporate our menu.
public class LivingRoomActivity extends TestToolbar {
    //The Fragment for all LivingRoom Activity listview details.
    Fragment fragment;
    // The main framelayout that the fragment will be inserted into.
    private FrameLayout frameLayout;

    //The Spinner inside the Custom Dialog box that will be opened when users press the add button.
    Spinner spinner1;

    // Arraylist that Houses the Names of Devices that will fill the ListView.
    ArrayList<String> deviceList = new ArrayList<>();
    // Arraylist that Houses what the type of device it is at that position.
    ArrayList<String> types = new ArrayList<>();

    //Main Listview
    ListView listView;
    //The Chat Adapter for the listview.
    protected ChatAdapter devicesAdapter;

    //Name of device from edittext value in add.
    String newDevice;
    //Type of Device picked from the spinner value in add.
    String deviceType;

    //Type of device taken from the arraylist position that matches the listview item position.
    String typeForCondition;
    //Name of device taken from the arraylist position that matches the listview item position.
    String DevName;

    //Database Helper Object that connects, and builds db.
    protected static DeviceDatabaseHelper deviceHelper;
    //the writable DB.
    SQLiteDatabase devices;
    //The cursor for iterating through the db rows.
    protected Cursor cursor;
    //The values to be inserted and updated in the db.
    protected ContentValues values;

    //progress bar.
    ProgressBar proBar;

    //Boolean used as the trigger for whether a key, value set should be reset.
    boolean removeShared=false;

    boolean isFrameUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        // todo: REQUIREMENT 1: Toolbar functionality is present in TestToolBar.java
        // Sets the action bar to our created custom Toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // todo: REQUIREMENT 9: SNACK BAR
        // Creates a random object for use with my snackbar in order to simulate other available devices in the room.
        Random random = new Random();
        int rand= random.nextInt(7);
        //Setting strings so that they will use resource strings that have been formatted for accepting variables.
        String toast = getResources().getString(R.string.snackbarMsg);
        String toastForm = String.format(toast, rand);
        Snackbar.make(findViewById(R.id.toolbar), toastForm, Snackbar.LENGTH_LONG).setAction("Action", null).show();

        // todo: REQUIREMENT 6: PROGRESS BAR
        //Creates and hides the progress bar until used in the Async.
        proBar = (ProgressBar) findViewById(R.id.progBar);
        proBar.setVisibility(View.INVISIBLE);

        //Sets the Header with string resource.
        TextView livingHeader = (TextView) findViewById(R.id.livingHeader);
        livingHeader.setText(R.string.livingHeader);

        frameLayout = (FrameLayout)findViewById(R.id.messageFrag);

        if(frameLayout==null){
            isFrameUsed=false;
            Log.i("", "You are using the phone layout");
        }
        else{
            isFrameUsed=true;
            Log.i("", "You are using the tablet layout");
        }

        // todo: REQUIREMENT 3: LISTVIEW
        // Creates the listview and sets the adapter.
        listView = (ListView) findViewById(R.id.livingListview);
        devicesAdapter = new ChatAdapter(this);
        listView.setAdapter(devicesAdapter);

        //todo: REQUIREMENT 4: DATABASE So listview items can be stored to reappear next time the app is opened.
        deviceHelper = new DeviceDatabaseHelper(this);
        devices = deviceHelper.getWritableDatabase();

        //Cursor for going through the database and populating the arraylists to be added into the listview.
        cursor = deviceHelper.getMessages();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            deviceList.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Name)));
            types.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Device)));
            cursor.moveToNext();
        }

        //todo: REQUIREMENT 3: LISTVIEW SHOWING DETAILED INFORMATION ON ITEM SELECTED.
        //ON CLICK LISTENER.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent , View v, int position, final long id) {

                //Takes the name of the device from the names arraylist at the position you've clicked on. (example: 4th row down)
                DevName = (String) deviceList.get(position);
                //Takes the type of device from the types arraylist.
                typeForCondition = (String) types.get(position);

//                cursor.moveToFirst();
                cursor.moveToPosition(position);

                //todo: THE ITEMS IN THE LIST SHOULD BE SORTED IN THE ORDER OF MOST FREQUENTLY USED.
                try {
                    //Database uses the sorted by clause to sort by the Key_Selected Column. Which is an integer that keeps being updated in the db on click with an int counter.
                    //Reads the current counter value for that row.
                    int count = cursor.getInt(cursor.getColumnIndex(deviceHelper.KEY_Selected));
                    count++;
                    //Puts the value into the ContentValues.
                    ContentValues args = new ContentValues();
                    args.put(deviceHelper.KEY_Selected, count);
                //Updates the database with the new Key_Selected keyvalue where the Name is equal to the name of the device currently clicked on.
                devices.update(TABLE_NAME, args, KEY_Name + "=?", new String[]{DevName});

                //Clears all the arraylists and then repopulates them by reading through the database again.
                deviceList.clear();
                types.clear();

                //Repopulating arraylist from DB.
                cursor = deviceHelper.getMessages();
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    deviceList.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Name)));
                    types.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Device)));
                    cursor.moveToNext();
                }
                //Notify the adapter that the list is different and needs to be updated on screen.
                devicesAdapter.notifyDataSetChanged();




            }catch(Exception e){}
                //todo: REQUIREMENT 2: SETTING THE FRAGMENT.
                //These Conditionals determine which Class Object Type should be created based on the Device Type. Example: Add a new 32" tv for the kids as a new TV type. You'll want the remote interface.

//                if(typeForCondition.contentEquals(getResources().getString(R.string.array1))) { fragment = new OnOffLamp();}
//                if(typeForCondition.contentEquals(getResources().getString(R.string.array2))) { fragment = new Dimmer();}
//                if(typeForCondition.contentEquals(getResources().getString(R.string.array3))) { fragment = new ColourDimmer();}
//                if(typeForCondition.contentEquals(getResources().getString(R.string.array4))) { fragment = new Blinds(); }
//                if(typeForCondition.contentEquals(getResources().getString(R.string.array5))) { fragment = new TV(); }

                if(typeForCondition.contentEquals("On/Off Lamp") || typeForCondition.contentEquals("Lampe Marche / Arret")) { fragment = new OnOffLamp();}
                if(typeForCondition.contentEquals("Dimmer Light") || typeForCondition.contentEquals("Lumiere plus faible" )) { fragment = new Dimmer();}
                if(typeForCondition.contentEquals("Coloured Dimmer Light") || typeForCondition.contentEquals("Lumiere dimmer coloree")) { fragment = new ColourDimmer();}
                if(typeForCondition.contentEquals("Blinds") || typeForCondition.contentEquals("Stores")) { fragment = new Blinds(); }
                if(typeForCondition.contentEquals("TV") || typeForCondition.contentEquals("Tele")) { fragment = new TV(); }

                //This creates a bundle so that we can take the arguments from the class and put them into the various fragments.
                Bundle clickedInfo = new Bundle();
                //We want to pass the Name you gave to the specific device so that we can use it in that fragment's textview to display its name as the header.
                clickedInfo.putString("deviceName", DevName);
                //We also want to pass a boolean. This is for when we have removed a device from the list but we might recreate a new device with the same name. Example: "TV" gets removed and replaced with a new "TV".
                // In the fragment if removeShared is true, we want to get rid of the value associated with that key and start fresh.
                clickedInfo.putBoolean("delete", removeShared);
                fragment.setArguments(clickedInfo);
                //After clicking in with the boolean as TRUE. We flag it back to false so that it's only made true during remove button click events.
                removeShared=false;

                //todo: REQUIREMENT 2: LOADING FRAGMENT
                //If the fragment has been populated with a "scene" then we will replace the empty fragment inside the framelayout with the new populated fragment.
                if(isFrameUsed == false) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.messageFrag, fragment);
                    ft.commit();
                    //Because our framelayout isn't being loaded into an all new activity. It just "Paints" over the existing activity. Disabling the listview in the background makes sure you can't click through.
                    listView.setEnabled(false);
                }
                else{
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.messageFrag, fragment);
                    ft.commit();
                }

            }
        });

        //todo: REQUIREMENT 7: USING BUTTONS
        //If we press the back button it will remove the populated fragment from the framelayout and go back to the listview screen.
        Button backButton = (Button) findViewById(R.id.back);
        backButton.setText(getResources().getString(R.string.backButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We want to re-enable the listview so we can go back to clicking on listview items.
                listView.setEnabled(true);
                if (fragment != null){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                ft.commit();}
            }
        });

        //todo: REQUIREMENT 7: USING BUTTONS
        //If we press the add button it will bring up a custom dialog box that allows a User to name their new device and pick the type of device it is.
        Button addButton = (Button) findViewById(R.id.add);
        addButton.setText(getResources().getString(R.string.addButton));
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
        //Create the custom dialog box.
        AlertDialog.Builder customBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = LivingRoomActivity.this.getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View rootView = inflater.inflate(dialog, null);
                //todo: REQUIREMENT 8: USING AN EDIT TEXT FOR DEVICE NAME INPUT.
                //The EditText field that will allow the user to input the device name.
                final EditText newText = (EditText) rootView.findViewById(R.id.dialogText);
                newText.setText("");
                newText.setHint(getResources().getString(R.string.editHint));
                //The spinner that has a list of premade device types a user can choose from. Limits them so they can't choose a device type that hasn't been programmed.
                spinner1 = (Spinner) rootView.findViewById(R.id.spinner);
                spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());

                customBuilder.setView(rootView)
                //If a user clicks the Okay Button. The device will be added to the arraylists. Then added to the database as an Async Task
                .setPositiveButton(getResources().getString(R.string.okayButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Adds the Device name to the arraylist.
                        newDevice = newText.getText().toString();
                        deviceList.add(newDevice);
                        //Adds the type of device to the arraylist.
                        deviceType = String.valueOf(spinner1.getSelectedItem());
                        types.add(deviceType);

                        //todo: REQUIREMENT 9: Displays a toast that says what kind of device they've selected.
                        Toast.makeText(LivingRoomActivity.this, "Device type is:" + String.valueOf(spinner1.getSelectedItem()), Toast.LENGTH_SHORT).show();

                        //todo: REQUIREMENT 5: Async Task that shows the progress bar as the new Device is added to the database.
                        aSync syncTask = new aSync();
                        proBar.setVisibility(View.VISIBLE);
                        syncTask.execute();
                        //List has changed so notify to update.
                        devicesAdapter.notifyDataSetChanged();
                    }
                })
                        //Negative Button just returns to main screen.
                .setNegativeButton(getResources().getString(R.string.cancelButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        customBuilder.create();
        customBuilder.show();
            }
        });


        //todo: REQUIREMENT 7: USING BUTTONS
        //If we click the remove button it will remove the selected device from the list (or the top most entry if not inside a device fragment).
        Button delButton = (Button) findViewById(R.id.remove);
        delButton.setText(getResources().getString(R.string.removeButton));
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Removing an item from the list will also bring it back to the listview so we need to reenable from the fragment again.
                listView.setEnabled(true);

                //Deletes from the DB where the device name is equal to our static String and clears the arraylists.
                devices.delete(TABLE_NAME, KEY_Name + "=?", new String[] {DevName});
                deviceList.clear();
                types.clear();

                //We're removing the device so we want to set the boolean to true so we can remove the previous value from that key in sharedPrefs.
                removeShared = true;

                //Repopulates the arraylist based on the Database.
                cursor = deviceHelper.getMessages();
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    deviceList.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Name)));
                    types.add(cursor.getString(cursor.getColumnIndex(deviceHelper.KEY_Device)));
                    cursor.moveToNext();}
                //Notify the adapter to update the list.
                devicesAdapter.notifyDataSetChanged();
                //Remove the fragment from the framelayout.

                if(isFrameUsed == true) {

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.remove(fragment);
                    ft.commit();
                }
            }
        });
    } //End of onCreate


    //Inner Chat Adapter Class from Lab 4.
    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int  getCount(){
            return deviceList.size();
        }

        public String getItem(int position){
            return deviceList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = LivingRoomActivity.this.getLayoutInflater();
            View result = null;

                result = inflater.inflate(R.layout.deviceslist, null);

            TextView message = (TextView) result.findViewById(R.id.devices_list);
            message.setText( getItem(position));
            return result;
        }
    }

    // A custom Listener class for use with the Spinner that gets used in the add custom dialogue box.
    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
            String deviceHolder = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0){
        }
    }

    //todo: REQUIREMENT 5: The Async Task that gets used when writing a new device into the database when a user clicks add.
    private class aSync extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            //Dummy String to return.
            String allTogether="";

            //todo: REQUIREMENT 4: SAVING SETTINGS TO DATABASE.
            //Adds Name, Type, how many times it's been selected.
            values = new ContentValues();
            values.put(deviceHelper.KEY_Name, newDevice);
            publishProgress(25);
            values.put(deviceHelper.KEY_Device, deviceType);
            publishProgress(50);
            values.put(deviceHelper.KEY_Selected, 0);
            publishProgress(75);
            devices.insert(TABLE_NAME, KEY_Name, values);
            publishProgress(100);

            return allTogether;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            //todo: REQUIREMENT 6: USING THE PROGRESS BAR.
            proBar.setVisibility(View.VISIBLE);
            proBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result){
           proBar.setVisibility(View.INVISIBLE);
        }
    } // END OF ASYNC
}
