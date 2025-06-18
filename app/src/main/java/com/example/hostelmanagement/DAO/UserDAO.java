package com.example.hostelmanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.hostelmanagement.Database.MyDatabaseHelper;
import com.example.hostelmanagement.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private MyDatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        long result = db.insert(MyDatabaseHelper.TABLE_USER, null, values);
        db.close();
        return result;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_USER, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(MyDatabaseHelper.TABLE_USER, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}