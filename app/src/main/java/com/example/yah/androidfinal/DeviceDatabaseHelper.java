package com.example.yah.androidfinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Yah on 2017-02-08.
 */

public class DeviceDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME ="LivingRoom.db";
    public static final String TABLE_NAME = "Devices";

    private static int VERSION_NUM =7;
    private final Context mCtx;

    private DeviceDatabaseHelper dDBHelper;
    SQLiteDatabase dDb=getWritableDatabase();

    public static final String KEY_ID = "_id";
    public static final String KEY_Name = "DeviceName";
    public static final String KEY_Device = "DeviceType";
    public static final String KEY_Selected = "TimesSelected";

    public static final String KEY_seek = "LastSeekPosition";
    public static final String KEY_color = "ColourSelected";
    public static final String KEY_blinds = "BlindsPosition";
    public static final String KEY_channel = "LastChannel";


    public static final String[] Columns = new String[]{
            KEY_ID,
            KEY_Name,
            KEY_Device,
            KEY_Selected,
            KEY_seek,
            KEY_color,
            KEY_blinds,
            KEY_channel
    };

    private static final String Create_Table = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name + " text," + KEY_Device + " text," +
            KEY_Selected + " INTEGER," + KEY_seek + " INTEGER," + KEY_color + " text," + KEY_blinds + " text," + KEY_channel + " INTEGER" + ")";

    public DeviceDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        this.mCtx=ctx;
    }


    public Cursor getMessages(){
        return dDb.query(TABLE_NAME, Columns, null, null, null, null, KEY_Selected + " desc");
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Create_Table);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



}
