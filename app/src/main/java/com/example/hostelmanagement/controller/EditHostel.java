package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hostelmanagement.DAO.HostelDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Hostel;

import java.util.List;

public class EditHostel extends AppCompatActivity {

    private EditText edtAddress, edtOwner, edtPhone,
            edtElectric, edtWater, edtVehicle, edtInternet, edtLaundry,
            edtElevator, edtTv, edtGarbage, edtService,
            edtBankOwner, edtBank, edtBankAccount;

    private Button btnSave;
    private HostelDAO hostelDAO;
    private Hostel currentHostel;
    private ImageView btnBack;

    private int hostelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hostel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mappingViews();

        // 2. Lấy dữ liệu từ Intent
        hostelId = getIntent().getIntExtra("hostel_id", -1);
        hostelDAO = new HostelDAO(this);

        if (hostelId != -1) {
            // 3. Lấy thông tin phòng từ DB
            List<Hostel> hostelList = hostelDAO.getAllRooms();
            for (Hostel h : hostelList) {
                if (h.getId() == hostelId) {
                    currentHostel = h;
                    break;
                }
            }

            if (currentHostel != null) {
                fillDataToViews(currentHostel);
            } else {
                Toast.makeText(this, "Không tìm thấy phòng", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Không nhận được ID phòng", Toast.LENGTH_SHORT).show();
            finish();
        }


        btnSave.setOnClickListener(v -> {
            updateHostelFromViews();
            long result = hostelDAO.updateRoom(currentHostel);
            if (result != -1) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditHostel.this, ListHostel.class);
                startActivity(intent);
            }
        });
    }

    private void mappingViews() {
        edtAddress = findViewById(R.id.edt_address);
        edtOwner = findViewById(R.id.edt_owner);
        edtPhone = findViewById(R.id.edt_phone);
        edtElectric = findViewById(R.id.edt_electric);
        edtWater = findViewById(R.id.edt_water);
        edtVehicle = findViewById(R.id.edt_vehicle);
        edtInternet = findViewById(R.id.edt_internet);
        edtLaundry = findViewById(R.id.edt_laundry);
        edtElevator = findViewById(R.id.edt_elevator);
        edtTv = findViewById(R.id.edt_tv);
        edtGarbage = findViewById(R.id.edt_garbage);
        edtService = findViewById(R.id.edt_service);
        edtBankOwner = findViewById(R.id.edt_bank_owner);
        edtBank = findViewById(R.id.edt_bank);
        edtBankAccount = findViewById(R.id.edt_bank_account);
        btnSave = findViewById(R.id.btn_save);
        btnBack = findViewById(R.id.btnBack);

    }

    private void fillDataToViews(Hostel room) {
        edtAddress.setText(room.getAddress());
        edtOwner.setText(room.getOwner());
        edtPhone.setText(room.getPhone());
        edtElectric.setText(String.valueOf(room.getElectricPrice()));
        edtWater.setText(String.valueOf(room.getWaterPrice()));
        edtVehicle.setText(String.valueOf(room.getVehiclePrice()));
        edtInternet.setText(String.valueOf(room.getInternetPrice()));
        edtLaundry.setText(String.valueOf(room.getLaundryPrice()));
        edtElevator.setText(String.valueOf(room.getElevatorPrice()));
        edtTv.setText(String.valueOf(room.getTvPrice()));
        edtGarbage.setText(String.valueOf(room.getGarbagePrice()));
        edtService.setText(String.valueOf(room.getServicePrice()));
        edtBankOwner.setText(room.getBankOwner());
        edtBank.setText(room.getBankName());
        edtBankAccount.setText(room.getBankAccount());
    }

    private void updateHostelFromViews() {
        currentHostel.setAddress(edtAddress.getText().toString());
        currentHostel.setOwner(edtOwner.getText().toString());
        currentHostel.setPhone(edtPhone.getText().toString());
        currentHostel.setElectricPrice(parseIntSafe(edtElectric.getText().toString()));
        currentHostel.setWaterPrice(parseIntSafe(edtWater.getText().toString()));
        currentHostel.setVehiclePrice(parseIntSafe(edtVehicle.getText().toString()));
        currentHostel.setInternetPrice(parseIntSafe(edtInternet.getText().toString()));
        currentHostel.setLaundryPrice(parseIntSafe(edtLaundry.getText().toString()));
        currentHostel.setElevatorPrice(parseIntSafe(edtElevator.getText().toString()));
        currentHostel.setTvPrice(parseIntSafe(edtTv.getText().toString()));
        currentHostel.setGarbagePrice(parseIntSafe(edtGarbage.getText().toString()));
        currentHostel.setServicePrice(parseIntSafe(edtService.getText().toString()));
        currentHostel.setBankOwner(edtBankOwner.getText().toString());
        currentHostel.setBankName(edtBank.getText().toString());
        currentHostel.setBankAccount(edtBankAccount.getText().toString());
    }

    private int parseIntSafe(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}