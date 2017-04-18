package com.example.yah.androidfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vallav on 2017-03-02.
 *
 */

public class KitchenDatabaseHelper extends SQLiteOpenHelper {

        public static String DATABASE_NAME = "Kitchen.db";
        public static int VERSION_NUM = 3;
        public static String KEY_ID = "_id";
        public static String KEY_ITEM_NAME = "NAME";
        public static String KEY_ITEM_TYPE = "TYPE";
        public static String KEY_COUNTER = "COUNTER";
        public static String KEY_SETTING = "SETTING";
        public static String TABLE_NAME = "KITCHEN";

        public KitchenDatabaseHelper(Context ctx){
            super (ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_ITEM_NAME + " text, " +
                    KEY_ITEM_TYPE + " text, " +
                    KEY_SETTING + " text, " +
                    KEY_COUNTER + " INTEGER );");
            Log.i("ChatDatabaseHelper", "Calling onCreate");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
            Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        }




    }

/*
    //Design Change to SharedPreferences.
    public static final String[] Columns = new String[]{
            KEY_ID,
            KEY_NAME,
            KEY_DEVICE,
            KEY_Selected,
       //     KEY_seek,
       //     KEY_color,
       //     KEY_blinds,
       //     KEY_channel
    };

    //Design Change to SharedPreferences.
    private static final String Create_Table = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
    KEY_NAME + " text," + KEY_DEVICE + " text," +
    //        KEY_Selected + " INTEGER," + KEY_seek + " INTEGER," + KEY_color + " text," + KEY_blinds + " text," + KEY_channel + " INTEGER" + ")";
    KEY_Selected + " INTEGER" + ")";

    public KitchenDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        this.kitchenContext = ctx;
    }

    public Cursor getMessages(){
        return db.query(TABLE_NAME, Columns, null, null, null, null, KEY_Selected + " desc");
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

 */