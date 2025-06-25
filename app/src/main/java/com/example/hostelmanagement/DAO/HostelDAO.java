package com.example.hostelmanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hostelmanagement.Database.MyDatabaseHelper;
import com.example.hostelmanagement.model.Hostel;

import java.util.ArrayList;
import java.util.List;

public class HostelDAO {
    private MyDatabaseHelper dbHelper;

    public HostelDAO(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public long insertRoom(Hostel room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("address", room.getAddress());
        values.put("owner", room.getOwner());
        values.put("phone", room.getPhone());
        values.put("electric", room.getElectricPrice());
        values.put("water", room.getWaterPrice());
        values.put("vehicle", room.getVehiclePrice());
        values.put("internet", room.getInternetPrice());
        values.put("laundry", room.getLaundryPrice());
        values.put("elevator", room.getElevatorPrice());
        values.put("tv", room.getTvPrice());
        values.put("garbage", room.getGarbagePrice());
        values.put("service", room.getServicePrice());
        values.put("bank_owner", room.getBankOwner());
        values.put("bank_name", room.getBankName());
        values.put("bank_account", room.getBankAccount());

        return db.insert(MyDatabaseHelper.TABLE_HOSTEL, null, values);
    }
    public List<Hostel> getAllRooms() {
        List<Hostel> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(MyDatabaseHelper.TABLE_HOSTEL, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Hostel room = new Hostel();
                room.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                room.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                room.setOwner(cursor.getString(cursor.getColumnIndexOrThrow("owner")));
                room.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                room.setElectricPrice(cursor.getInt(cursor.getColumnIndexOrThrow("electric")));
                room.setWaterPrice(cursor.getInt(cursor.getColumnIndexOrThrow("water")));
                room.setVehiclePrice(cursor.getInt(cursor.getColumnIndexOrThrow("vehicle")));
                room.setInternetPrice(cursor.getInt(cursor.getColumnIndexOrThrow("internet")));
                room.setLaundryPrice(cursor.getInt(cursor.getColumnIndexOrThrow("laundry")));
                room.setElevatorPrice(cursor.getInt(cursor.getColumnIndexOrThrow("elevator")));
                room.setTvPrice(cursor.getInt(cursor.getColumnIndexOrThrow("tv")));
                room.setGarbagePrice(cursor.getInt(cursor.getColumnIndexOrThrow("garbage")));
                room.setServicePrice(cursor.getInt(cursor.getColumnIndexOrThrow("service")));
                room.setBankOwner(cursor.getString(cursor.getColumnIndexOrThrow("bank_owner")));
                room.setBankName(cursor.getString(cursor.getColumnIndexOrThrow("bank_name")));
                room.setBankAccount(cursor.getString(cursor.getColumnIndexOrThrow("bank_account")));
                list.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public void deleteUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(MyDatabaseHelper.TABLE_HOSTEL, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int updateRoom(Hostel room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("address", room.getAddress());
        values.put("owner", room.getOwner());
        values.put("phone", room.getPhone());
        values.put("electric", room.getElectricPrice());
        values.put("water", room.getWaterPrice());
        values.put("vehicle", room.getVehiclePrice());
        values.put("internet", room.getInternetPrice());
        values.put("laundry", room.getLaundryPrice());
        values.put("elevator", room.getElevatorPrice());
        values.put("tv", room.getTvPrice());
        values.put("garbage", room.getGarbagePrice());
        values.put("service", room.getServicePrice());
        values.put("bank_owner", room.getBankOwner());
        values.put("bank_name", room.getBankName());
        values.put("bank_account", room.getBankAccount());

        // Cập nhật dựa trên ID của phòng
        return db.update(MyDatabaseHelper.TABLE_HOSTEL, values, "id = ?", new String[]{String.valueOf(room.getId())});
    }


}
