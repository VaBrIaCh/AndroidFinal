package com.example.yah.androidfinal;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * REQUIREMENT 1 Kitchen activity, Valeriu Ionita
 */
public class KitchenActivity extends TestToolbar {


    //declare variables
    protected static final String ACTIVITY_NAME = "KitchenActivity";


    Fragment fragmentKitchenMain;


    ListView kitchenList;


    ArrayList<String> kitchenItemList = new ArrayList<>();
    ArrayList<String> kitchenItemType = new ArrayList<>();
    ArrayList<String> kitchenSettings = new ArrayList<>();
    KitchenDatabaseHelper dHelper;
    KitchenAdapter kitchenAdapter;
    SQLiteDatabase db;
    ContentValues cValues;
    Cursor c;

    boolean frameExists;

    Long itemId;

    private View viewHolder;
    private EditText newItemName;
    private Spinner itemSelected;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        /**
         * The activity must be accessible by selecting a graphical icon from a Toolbar.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dHelper = new KitchenDatabaseHelper( this );
        db = dHelper.getWritableDatabase();


        //Initialize Declared chat variables
        kitchenItemList.add("Test");
        kitchenItemType.add("Blah");
        kitchenSettings.add("1000cc");
        
        kitchenList = (ListView) findViewById(R.id.kitchen_list_view_item_name);


        kitchenAdapter = new KitchenAdapter( this );
        kitchenList.setAdapter(kitchenAdapter);





        c = db.query(false, dHelper.TABLE_NAME,
                new String[]{dHelper.KEY_ID, dHelper.KEY_ITEM_NAME, dHelper.KEY_ITEM_TYPE, dHelper.KEY_SETTING, dHelper.KEY_COUNTER},
                null, null, null, null, null, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            kitchenItemList.add(c.getString(c.getColumnIndex(dHelper.KEY_ITEM_NAME)));
            kitchenItemType.add(c.getString(c.getColumnIndex(dHelper.KEY_ITEM_TYPE)));
            kitchenSettings.add(c.getString(c.getColumnIndex(dHelper.KEY_SETTING)));
            c.moveToNext();
        }
        //  c.close();




        /**
         * REQUIREMENT 2: Each Activity must use a fragment in its graphical interface.
         */
        Button lightControls = (Button) findViewById(R.id.button_control_light);
        lightControls.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                addKitchenFragment(new KitchenLights(KitchenActivity.this));
            }
        });

        Button microwaveControls = (Button) findViewById(R.id.button_control_microwave);
        microwaveControls.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                addKitchenFragment(new KitchenMicrowave(KitchenActivity.this));
            }
        });



        //TODO: Add device

        Button addItem = (Button) findViewById(R.id.button_kitchen_add);
        addItem.setText(getResources().getString(R.string.addButton));
        addItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Create the custom dialog box.
                customAlertDialog(view);
            }
        });







//        3.	Each Activity must have a ListView to present items. Selecting an item from the
//              ListView must show detailed information about the item selected.

//todo: REQUIREMENT 3: LISTVIEW SHOWING DETAILED INFORMATION ON ITEM SELECTED.
        //ON CLICK LISTENER.
        kitchenList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent , View v, int position, long id) {

            String itemSelectName, itemSelectType, itemSelectSetting;

                itemSelectName = kitchenItemList.get(position);
                itemSelectType = kitchenItemType.get(position);
                /*if(kitchenSettings.get(position) != null) {
                    itemSelectSetting = kitchenSettings.get(position);
                }*/

                if(itemSelectType.equals("Light")){addKitchenFragment(new KitchenLights(KitchenActivity.this));}
                if(itemSelectType.equals("Microwave")){addKitchenFragment(new KitchenMicrowave(KitchenActivity.this));}
                if(itemSelectType.equals("Stove")){addKitchenFragment(new KitchenLights(KitchenActivity.this));}
                if(itemSelectType.equals("Fridge")){addKitchenFragment(new KitchenMicrowave(KitchenActivity.this));}
                if(itemSelectType.equals("Freezer")){addKitchenFragment(new KitchenMicrowave(KitchenActivity.this));}


            }

        });





//        4.	The items listed in the ListView must be stored by the application so that appear
//              the next time the application is launched. The user must be able to add and delete items,
//              which would then also be stored.
//        5.	Each activity must use an AsyncTask in the code. This can be to open a Database, retrieve
//              data from a server, save data, or any other reasonable circumstance.
//        6.	Each activity must have at least 1 progress bar
//        7.	Each activity must have at least 1 button
//        8.	Each activity must have at least 1 edit text with appropriate text input method.
//        9.	Each activity must have at least 1 Toast, Snackbar, and custom dialog notification.
//        10.	A help menu item that displays a dialog with the author’s name, Activity version number,
//              and instructions for how to use the interface.
//        11.	There must be at least 1 other language supported by your Activity. If you are not bilingual,
//              then you must support both British and American English (words like colour, color, neighbour, neighbor, etc).
//              If you know a language other than English, then you can support that language in your application and don’t need
//              to support American English.




        //---------------END onCreate
    }


    public void customAlertDialog(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        viewHolder = inflater.inflate(R.layout.kitchen_item_selection, null);
        // Inflate and set the layout for the dialog
        builder.setView(viewHolder);

        newItemName = (EditText) viewHolder.findViewById(R.id.kitchen_item_selection_dialog);

        itemSelected = (Spinner) viewHolder.findViewById(R.id.kitchen_item_selection_dropdown);


        // Add action buttons
        builder.setPositiveButton(R.string.okayButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                //Adds the item name to the arraylist.
                kitchenItemList.add(newItemName.getText().toString());
                //Adds the item type to the arraylist.
                kitchenItemType.add(String.valueOf(itemSelected.getSelectedItem()));

                //todo: REQUIREMENT 9: Displays a toast that says what kind of device they've selected.
                Toast.makeText(KitchenActivity.this, "New " + String.valueOf(itemSelected.getSelectedItem()) + " added!",
                        Toast.LENGTH_SHORT).show();


                cValues = new ContentValues();
                cValues.put(dHelper.KEY_ITEM_NAME, newItemName.getText().toString());
                cValues.put(dHelper.KEY_ITEM_TYPE, String.valueOf(itemSelected.getSelectedItem()));
                db.insert(dHelper.TABLE_NAME, null, cValues);

                c = db.query(false, dHelper.TABLE_NAME, new String[]{dHelper.KEY_ID, dHelper.KEY_ITEM_NAME, dHelper.KEY_ITEM_TYPE},
                        null, null, null, null, null, null);

                kitchenAdapter.notifyDataSetChanged();
                
            }
        })
                .setNegativeButton(R.string.cancelButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
        
    }





    private class KitchenAdapter extends ArrayAdapter<String> {
        public KitchenAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return kitchenItemList.size();
        }

        public String getItem(int position) { return kitchenItemList.get(position); }

        public String getItem(ArrayList<String> al, int position){
            return al.get(position);
        }


        /*public long getItemId(int position){

            c.moveToPosition(position);
            itemId = c.getLong(c.getColumnIndex(dHelper.KEY_ID));

            Log.d(ACTIVITY_NAME, "GetitemID: " + Long.toString(itemId));


            return itemId;
        }*/

        public View getView(int position, View contentView, ViewGroup parent){
            LayoutInflater inflater = KitchenActivity.this.getLayoutInflater();

            View result = inflater.inflate(R.layout.kitchen_item_list, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            String itemFormat = getItem(kitchenItemType, position) + "  :::  " +
                        getItem(kitchenItemList, position);
            /*if (kitchenSettings.get(position) != null){
                itemFormat += "  :::  " + getItem(kitchenSettings, position);
            }*/

            message.setText(itemFormat); //get string at position

            return result;
        }
    }

public void addKitchenFragment(Fragment f){
    //fragmentKitchenMain = new KitchenLights(KitchenActivity.this);
    fragmentKitchenMain = f;
    Bundle clickedInfo = new Bundle();
    clickedInfo.putString("deviceName", "Light Controls");
    clickedInfo.putBoolean("delete", true);
    fragmentKitchenMain.setArguments(clickedInfo);
    kitchenList.setEnabled(false);
    //todo: REQUIREMENT 2: LOADING FRAGMENT

    getSupportFragmentManager().beginTransaction()
            .add(R.id.kitchen_fragment_placeholder, fragmentKitchenMain)
            .commit();


    //--------------END OF KITCHEN LIGHTS FRAGMENT
}


    public void removeCurrentFragment(){

        kitchenList.setEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .remove(fragmentKitchenMain)
                .commit();

    }



}
