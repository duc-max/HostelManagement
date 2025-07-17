package com.example.hostelmanagement.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelmanagement.DAO.HostelDAO;
import com.example.hostelmanagement.DAO.InvoiceDAO;
import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Hostel;
import com.example.hostelmanagement.model.Invoice;
import com.example.hostelmanagement.model.Room;

import java.util.List;

public class Statistical extends AppCompatActivity {

    private LinearLayout layoutRevenue;
    private HostelDAO hostelDAO;
    private RoomDAO roomDAO;
    private InvoiceDAO invoiceDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        layoutRevenue = findViewById(R.id.layoutRevenue);

        hostelDAO = new HostelDAO(this);
        roomDAO = new RoomDAO(this);
        invoiceDAO = new InvoiceDAO(this);

        showRevenuePerHostel();
    }

    private void showRevenuePerHostel() {
        List<Hostel> hostelList = hostelDAO.getAllRooms(); // lấy tất cả hostel

        for (Hostel hostel : hostelList) {
            int totalRevenue = 0;

            // Lấy các phòng thuộc hostel
            List<Room> roomList = roomDAO.getRoomsByHostelId(hostel.getId());

            for (Room room : roomList) {
                List<Invoice> invoiceList = invoiceDAO.getAllInvoices();

                for (Invoice invoice : invoiceList) {
                    if (invoice.getRoomId() == room.getId()) {
                        totalRevenue += invoice.getTotal();
                    }
                }
            }

            // Inflate custom view để hiển thị
            View view = LayoutInflater.from(this).inflate(R.layout.item_revenue_stat, layoutRevenue, false);

            TextView tvHostelAddress = view.findViewById(R.id.tvHostelAddress);
            TextView tvTotalRevenue = view.findViewById(R.id.tvTotalRevenue);

            tvHostelAddress.setText("Địa chỉ: " + hostel.getAddress());
            tvTotalRevenue.setText("Tổng doanh thu: " + totalRevenue + " VNĐ");

            layoutRevenue.addView(view);
        }
    }
}
