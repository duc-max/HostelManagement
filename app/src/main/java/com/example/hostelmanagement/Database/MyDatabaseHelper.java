package com.example.hostelmanagement.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyDatabase.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USER = "users";
    public static final String TABLE_HOSTEL = "hostel";

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "phone TEXT," +
                "password TEXT," +
                "role TEXT" +
                ")";
        db.execSQL(createTable);

        String createRoomTable = "CREATE TABLE " + TABLE_HOSTEL +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "address TEXT," +
                "owner TEXT," +
                "phone TEXT," +
                "electric INTEGER," +
                "water INTEGER," +
                "vehicle INTEGER," +
                "internet INTEGER," +
                "laundry INTEGER," +
                "elevator INTEGER," +
                "tv INTEGER," +
                "garbage INTEGER," +
                "service INTEGER," +
                "bank_owner TEXT," +
                "bank_name TEXT," +
                "bank_account TEXT" +
                ")";
        db.execSQL(createRoomTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
