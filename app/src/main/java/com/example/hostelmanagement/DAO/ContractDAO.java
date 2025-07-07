package com.example.hostelmanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hostelmanagement.Database.MyDatabaseHelper;
import com.example.hostelmanagement.model.Contract;

import java.util.ArrayList;
import java.util.List;

public class ContractDAO {
    private SQLiteDatabase db;

    public ContractDAO(Context context) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // INSERT
    public long insertContract(Contract contract) {
        ContentValues values = new ContentValues();
        values.put("contractNumber", contract.getContractNumber());
        values.put("customerName", contract.getCustomerName());
        values.put("phone", contract.getPhone());
        values.put("address", contract.getAddress());
        values.put("room", contract.getRoom());
        values.put("numPeople", contract.getNumPeople());
        values.put("roomPrice", contract.getRoomPrice());
        values.put("deposit", contract.getDeposit());
        values.put("startDate", contract.getStartDate());
        values.put("duration", contract.getDuration());
        values.put("reminder", contract.getReminder());
        values.put("electricIndex", contract.getElectricIndex());
        values.put("hasParking", contract.getHasParking());
        values.put("bikeCount", contract.getBikeCount());
        values.put("hasInternet", contract.getHasInternet());
        values.put("hasLaundry", contract.getHasLaundry());
        values.put("note", contract.getNote());

        return db.insert(MyDatabaseHelper.TABLE_CONTRACT, null, values);
    }

    // UPDATE
    public int updateContract(Contract contract) {
        ContentValues values = new ContentValues();
        values.put("contractNumber", contract.getContractNumber());
        values.put("customerName", contract.getCustomerName());
        values.put("phone", contract.getPhone());
        values.put("address", contract.getAddress());
        values.put("room", contract.getRoom());
        values.put("numPeople", contract.getNumPeople());
        values.put("roomPrice", contract.getRoomPrice());
        values.put("deposit", contract.getDeposit());
        values.put("startDate", contract.getStartDate());
        values.put("duration", contract.getDuration());
        values.put("reminder", contract.getReminder());
        values.put("electricIndex", contract.getElectricIndex());
        values.put("hasParking", contract.getHasParking());
        values.put("bikeCount", contract.getBikeCount());
        values.put("hasInternet", contract.getHasInternet());
        values.put("hasLaundry", contract.getHasLaundry());
        values.put("note", contract.getNote());

        return db.update(MyDatabaseHelper.TABLE_CONTRACT, values, "id=?", new String[]{String.valueOf(contract.getId())});
    }

    // DELETE
    public int deleteContract(int id) {
        return db.delete(MyDatabaseHelper.TABLE_CONTRACT, "id=?", new String[]{String.valueOf(id)});
    }

    // GET ALL
    public List<Contract> getAllContracts() {
        List<Contract> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_CONTRACT, null);
        if (cursor.moveToFirst()) {
            do {
                Contract c = new Contract();
                c.setId(cursor.getInt(0));
                c.setContractNumber(cursor.getString(1));
                c.setCustomerName(cursor.getString(2));
                c.setPhone(cursor.getString(3));
                c.setAddress(cursor.getString(4));
                c.setRoom(cursor.getInt(5));
                c.setNumPeople(cursor.getInt(6));
                c.setRoomPrice(cursor.getInt(7));
                c.setDeposit(cursor.getInt(8));
                c.setStartDate(cursor.getString(9));
                c.setDuration(cursor.getInt(10));
                c.setReminder(cursor.getInt(11));
                c.setElectricIndex(cursor.getInt(12));
                c.setHasParking(cursor.getInt(13));
                c.setBikeCount(cursor.getInt(14));
                c.setHasInternet(cursor.getInt(15));
                c.setHasLaundry(cursor.getInt(16));
                c.setNote(cursor.getString(17));
                list.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // GET ONE BY ID
    public Contract getContractById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_CONTRACT + " WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            Contract c = new Contract();
            c.setId(cursor.getInt(0));
            c.setContractNumber(cursor.getString(1));
            c.setCustomerName(cursor.getString(2));
            c.setPhone(cursor.getString(3));
            c.setAddress(cursor.getString(4));
            c.setRoom(cursor.getInt(5));
            c.setNumPeople(cursor.getInt(6));
            c.setRoomPrice(cursor.getInt(7));
            c.setDeposit(cursor.getInt(8));
            c.setStartDate(cursor.getString(9));
            c.setDuration(cursor.getInt(10));
            c.setReminder(cursor.getInt(11));
            c.setElectricIndex(cursor.getInt(12));
            c.setHasParking(cursor.getInt(13));
            c.setBikeCount(cursor.getInt(14));
            c.setHasInternet(cursor.getInt(15));
            c.setHasLaundry(cursor.getInt(16));
            c.setNote(cursor.getString(17));
            cursor.close();
            return c;
        }
        return null;
    }

    // New method: Get contract by room name
    public Contract getContractByRoomNumber(int roomNumber) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_CONTRACT + " WHERE room = ?", new String[]{String.valueOf(roomNumber)});
        if (cursor != null && cursor.moveToFirst()) {
            Contract c = new Contract();
            c.setId(cursor.getInt(0));
            c.setContractNumber(cursor.getString(1));
            c.setCustomerName(cursor.getString(2));
            c.setPhone(cursor.getString(3));
            c.setAddress(cursor.getString(4));
            c.setRoom(cursor.getInt(5));
            c.setNumPeople(cursor.getInt(6));
            c.setRoomPrice(cursor.getInt(7));
            c.setDeposit(cursor.getInt(8));
            c.setStartDate(cursor.getString(9));
            c.setDuration(cursor.getInt(10));
            c.setReminder(cursor.getInt(11));
            c.setElectricIndex(cursor.getInt(12));
            c.setHasParking(cursor.getInt(13));
            c.setBikeCount(cursor.getInt(14));
            c.setHasInternet(cursor.getInt(15));
            c.setHasLaundry(cursor.getInt(16));
            c.setNote(cursor.getString(17));
            cursor.close();
            return c;
        }
        return null;
    }
}