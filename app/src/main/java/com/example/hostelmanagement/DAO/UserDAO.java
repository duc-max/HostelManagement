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

    // Thêm người dùng mới
    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("phone", user.getPhone());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

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
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                user.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    // Xóa người dùng theo id
    public void deleteUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(MyDatabaseHelper.TABLE_USER, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Kiểm tra số điện thoại đã tồn tại chưa
    public boolean isPhoneExists(String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT 1 FROM " + MyDatabaseHelper.TABLE_USER + " WHERE phone = ? LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{phone});
        boolean exists = cursor.moveToFirst(); // true nếu có bản ghi nào
        cursor.close();
        db.close();
        return exists;
    }

}


