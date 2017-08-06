package com.android.elliotmiller.week4appem1974.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by azeezolaniran on 05/08/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CARDEALER";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "CUSTOMER_TABLE";

    //column names
    public static String COLUMN_ID = "_id";
    public static String COLUMN_FIRST_NAME = "first_name";
    public static String COLUMN_LAST_NAME = "last_name";
    public static String COLUMN_COST = "car_cost";
    public static String COLUMN_MAKE = "car_make";
    public static String COLUMN_MODEL = "car_model";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_COST + " TEXT, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_MAKE + " TEXT, "
                + COLUMN_MODEL + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public synchronized boolean addUser(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result > 0;
    }

    public synchronized boolean deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0;
    }

    public synchronized Cursor getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
    }

    public boolean updateUser(String id, ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0;
    }
}
