package com.example.hostelmanagement.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hostelmanagement.Database.MyDatabaseHelper;
import com.example.hostelmanagement.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private MyDatabaseHelper dbHelper;

    public RoomDAO(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    // CREATE
    public long insertRoom(Room room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hostelId", room.getHostelId());
        values.put("price", room.getPrice());
        values.put("roomName", room.getRoomName());
        values.put("status", room.isStatus() ? 1 : 0);
        return db.insert("room", null, values);
    }

    // READ - Get one room by ID
    public Room getRoomById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("room", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Room room = new Room();
            room.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            room.setHostelId(cursor.getInt(cursor.getColumnIndexOrThrow("hostelId")));
            room.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("price")));
            room.setRoomName(cursor.getString(cursor.getColumnIndexOrThrow("roomName")));
            room.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")) == 1);
            cursor.close();
            return room;
        }
        return null;
    }

    // RoomDAO.java
    public List<Room> getRoomsByHostelId(int hostelId) {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM room WHERE hostelId = ?", new String[]{String.valueOf(hostelId)});
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                room.setHostelId(cursor.getInt(cursor.getColumnIndexOrThrow("hostelId")));
                room.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("price")));
                room.setRoomName(cursor.getString(cursor.getColumnIndexOrThrow("roomName")));
                room.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")) == 1);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return roomList;
    }


    // READ - Get all rooms
    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM room", null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                room.setHostelId(cursor.getInt(cursor.getColumnIndexOrThrow("hostelId")));
                room.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("price")));
                room.setRoomName(cursor.getString(cursor.getColumnIndexOrThrow("roomName")));
                room.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")) == 1);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return roomList;
    }
    public List<Room> getRoomsByHostelIdAndStatus(int hostelId, boolean status) {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM room WHERE hostelId = ? AND status = ?",
                new String[]{String.valueOf(hostelId), status ? "1" : "0"});
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                room.setHostelId(cursor.getInt(cursor.getColumnIndexOrThrow("hostelId")));
                room.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("price")));
                room.setRoomName(cursor.getString(cursor.getColumnIndexOrThrow("roomName")));
                room.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")) == 1);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return roomList;
    }


    // UPDATE
    public int updateRoom(Room room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hostelId", room.getHostelId());
        values.put("price", room.getPrice());
        values.put("roomName", room.getRoomName());
        values.put("status", room.isStatus() ? 1 : 0);
        return db.update("room", values, "id = ?", new String[]{String.valueOf(room.getId())});
    }

    // DELETE
    public int deleteRoom(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("room", "id = ?", new String[]{String.valueOf(id)});
    }
}
