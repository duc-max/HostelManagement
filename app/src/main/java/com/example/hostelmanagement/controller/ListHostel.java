package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hostelmanagement.R;
import com.example.hostelmanagement.DAO.HostelDAO;
import com.example.hostelmanagement.model.Hostel;

import java.util.List;

public class ListHostel extends AppCompatActivity {

    ImageView btnBack, btnCreateHostel;
    LinearLayout layoutHostelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_hostel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnBack);
        btnCreateHostel = findViewById(R.id.btnCreateHostel);
        layoutHostelList = findViewById(R.id.layoutHostelList); // LinearLayout chứa các card

        btnCreateHostel.setOnClickListener(v -> {
            Intent intent = new Intent(ListHostel.this, CreateHostel.class);
            startActivity(intent);
            finish();
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ListHostel.this, Dashboard.class);
            startActivity(intent);
            finish();
        });

        loadHostels();
    }

    private void loadHostels() {
        HostelDAO dao = new HostelDAO(this);
        List<Hostel> list = dao.getAllRooms();

        LayoutInflater inflater = LayoutInflater.from(this);
        layoutHostelList.removeAllViews();

        for (Hostel hostel : list) {
            View cardView = inflater.inflate(R.layout.activity_hostel_detail, null);

            TextView tvAddress = cardView.findViewById(R.id.tvAddress);
            TextView tvOwnerPhone = cardView.findViewById(R.id.tvOwnerPhone);
            TextView tvBankInfo = cardView.findViewById(R.id.tvBankInfo);

            tvAddress.setText(hostel.getAddress());
            tvOwnerPhone.setText(hostel.getOwner() + " - " + hostel.getPhone());

            String bankInfo = "Thông tin chuyển khoản\nChủ TK: " + hostel.getBankOwner()
                    + "\nNgân hàng: " + hostel.getBankName()
                    + "\nSTK: " + hostel.getBankAccount();
            tvBankInfo.setText(bankInfo);

            // Table Layout
            String[][] feeData = {
                    {"Điện", "KWH", hostel.getElectricPrice() + " đ"},
                    {"Nước", "phòng", hostel.getWaterPrice() + " đ"},
                    {"Xe", "chiếc", hostel.getVehiclePrice() + " đ"},
                    {"Internet", "phòng", hostel.getInternetPrice() + " đ"},
                    {"Giặt sấy", "phòng", hostel.getLaundryPrice() + " đ"},
                    {"Thang máy", "phòng", hostel.getElevatorPrice() + " đ"},
                    {"Tivi cáp", "phòng", hostel.getTvPrice() + " đ"},
                    {"Rác", "phòng", hostel.getGarbagePrice() + " đ"},
                    {"Dịch vụ", "phòng", hostel.getServicePrice() + " đ"},
            };

            android.widget.TableLayout tableFees = cardView.findViewById(R.id.tableFees);
            tableFees.removeViews(1, tableFees.getChildCount() - 1);

            for (String[] fee : feeData) {
                android.widget.TableRow row = new android.widget.TableRow(this);

                for (String cell : fee) {
                    TextView tv = new TextView(this);
                    tv.setText(cell);
                    row.addView(tv);
                }

                tableFees.addView(row);
            }

            Button btnView = cardView.findViewById(R.id.btnViewRooms);
            ImageView btnEdit = cardView.findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListHostel.this, EditHostel.class);
                    intent.putExtra("hostel_id", hostel.getId());

                    startActivity(intent);
                }
            });


            btnView.setOnClickListener(view -> {
                Intent intent = new Intent(ListHostel.this, ListRoom.class);
                intent.putExtra("hostel_id", hostel.getId());
                startActivity(intent);

            });

            layoutHostelList.addView(cardView);
        }
    }
}
