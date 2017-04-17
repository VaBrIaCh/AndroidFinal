package com.example.yah.androidfinal;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class HouseTemp extends TestToolbar {


    static String HouseTemp = "HouseTemp"; // Used in logs
    String originalTemp; // Used in spinner for setting default temperature
    EditText editText;
    ListView listView;
    ArrayList<String> timeTempArray;
    HouseTempDatabaseHelper dbHelper;
    SQLiteDatabase database;
    Cursor cursor;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_temp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // SHARED PREFERENCES
        SharedPreferences sharedPref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        final TextView tempText = (TextView) findViewById(R.id.tempText); // Main LARGE temperature
        originalTemp = tempText.getText().toString();
        tempText.setText(sharedPref.getString("setTemp", "20"));

        Button setButton = (Button) findViewById(R.id.setButton); // Instantiate set button
        editText = (EditText) findViewById(R.id.tempEditText); // Instantiate EditText

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(HouseTemp, "Set temperature selected...");
                String input = editText.getText().toString(); // Copy user input
                int inputTemp = 0;
                try {
                    inputTemp = Integer.parseInt(input);

                    if (inputTemp >= 16 && inputTemp <= 24) {
                        tempText.setText(input);
                        editor.putString("setTemp", input);
                        editor.commit();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.validTemp),
                                    Toast.LENGTH_SHORT).show();
                        }
                } catch (NumberFormatException nfe) {
                    //Log.i(HouseTemp, "Caught nfexception.........");
                    Toast.makeText(getApplicationContext(), getString(R.string.validTemp), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TIME SPINNER
        final Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.time_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter1);

        // TEMPERATURE SPINNER
        final Spinner tempSpinner = (Spinner) findViewById(R.id.tempSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.temperature_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempSpinner.setAdapter(adapter2);

        // DB
        timeTempArray = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.temp_data_listview);
        final HouseTemp.TempAdapter messageAdapter = new HouseTemp.TempAdapter(this);
        listView.setAdapter(messageAdapter);
        dbHelper = new HouseTempDatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        //RETRIEVE DATA
        cursor = database.rawQuery("SELECT * FROM " +
                HouseTempDatabaseHelper.TABLE_NAME +
                " WHERE ? NOT NULL",
                new String[]{HouseTempDatabaseHelper.KEY_TEMPERATURE});

        for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++) {
            Log.i(HouseTemp, "column " + columnIndex + " " + cursor.getColumnName(columnIndex));
        }

        // DISPLAY STORED DATABASE ENTRIES
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String storedTemp = cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TIME))
                    + " " + cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TEMPERATURE));
            timeTempArray.add(storedTemp);
            cursor.moveToNext();
        }
        cursor.close();

        // SET SCHEDULE BUTTON
        Button setScheduleButton = (Button) findViewById(R.id.setScheduleButton);
        setScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*// GET CURRENT TIME
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR); // 12 hour format
                int hourOfDay = cal.get(Calendar.HOUR_OF_DAY); // 24 hour format*/

                String time = timeSpinner.getSelectedItem().toString();
                String temp = tempSpinner.getSelectedItem().toString();

                if((!time.equals("--")) && (!temp.equals("--"))) {
                    timeTempArray.add(time + " " + temp);
                    //insert chat messages to database
                    ContentValues initialValues = new ContentValues();
                    initialValues.put(HouseTempDatabaseHelper.KEY_TIME, time);
                    initialValues.put(HouseTempDatabaseHelper.KEY_TEMPERATURE, temp);
                    database.insert(HouseTempDatabaseHelper.TABLE_NAME, null, initialValues);
                    Log.d("DEBUG", initialValues.toString());
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });

        // DELETE ITEM FROM LISTVIEW AND DATABASE
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int itemPosition = position;
                final String itemValue = (String) listView.getItemAtPosition(position);
                AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(HouseTemp.this);
                DialogBuilder.setTitle(getString(R.string.deleteEntry));

                // YES BUTTON
                DialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        timeTempArray.remove(itemPosition);
                        String[] words = itemValue.split("\\s+");
                        database.delete(HouseTempDatabaseHelper.TABLE_NAME, HouseTempDatabaseHelper.KEY_TIME +
                                "=?" , new String[]{words[0]});
                        messageAdapter.notifyDataSetChanged();
                        //database.delete(HouseTempDatabaseHelper.TABLE_NAME, null, null); // CLEAR DATABASE
                    }
                });

                // NO BUTTON
                DialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = DialogBuilder.create();
                dialog.show();
            }

        });
    }  // End of onCreate

    public void onDestroy() {

        database.close();
        super.onDestroy();
    }

    private class TempAdapter extends ArrayAdapter<String> {

        public TempAdapter(Context ctx) {
            super(ctx,0);
        }

        // must implement
        // int getCount()
        public int getCount(){

            return timeTempArray.size();
        }

        @Override
        public String getItem(int position) {
            return (String)timeTempArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // View getView(int position, View convertView, ViewGroup parent)
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = HouseTemp.this.getLayoutInflater();
            // This will recreate your view that you made in the resource file. If the position
            // is an even number then inflate chat_row_incoming else inflate chat_row_outgoing
            View result;
            result = inflater.inflate(R.layout.temp_listview, null);
            TextView message = (TextView)result.findViewById(R.id.listviewText);
            message.setText( getItem(position)  ); // get the string at a position
            return result;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
} // End of HouseTemp.java