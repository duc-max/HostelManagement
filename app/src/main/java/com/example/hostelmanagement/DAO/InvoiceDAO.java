package com.example.hostelmanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hostelmanagement.Database.MyDatabaseHelper;
import com.example.hostelmanagement.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    private MyDatabaseHelper dbHelper;

    public InvoiceDAO(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    // CREATE
    public long insertInvoice(Invoice invoice) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("roomId", invoice.getRoomId());
        values.put("total", invoice.getTotal());
        values.put("contractId", invoice.getContractId());
        return db.insert("invoice", null, values);
    }

    // READ ALL
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoiceList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM invoice", null);

        if (cursor.moveToFirst()) {
            do {
                Invoice invoice = new Invoice();
                invoice.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                invoice.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow("roomId")));
                invoice.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow("total")));
                invoice.setContractId(cursor.getInt(cursor.getColumnIndexOrThrow("contractId")));
                invoiceList.add(invoice);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return invoiceList;
    }

    // READ BY ID
    public Invoice getInvoiceById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("invoice", null, "id=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Invoice invoice = new Invoice();
            invoice.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            invoice.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow("roomId")));
            invoice.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow("total")));
            invoice.setContractId(cursor.getInt(cursor.getColumnIndexOrThrow("contractId")));
            cursor.close();
            return invoice;
        }

        return null;
    }

    // UPDATE
    public int updateInvoice(Invoice invoice) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("roomId", invoice.getRoomId());
        values.put("total", invoice.getTotal());
        values.put("contractId", invoice.getContractId());
        return db.update("invoice", values, "id=?", new String[]{String.valueOf(invoice.getId())});
    }

    // DELETE
    public int deleteInvoice(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("invoice", "id=?", new String[]{String.valueOf(id)});
    }
}