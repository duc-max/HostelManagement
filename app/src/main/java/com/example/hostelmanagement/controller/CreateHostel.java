package com.example.hostelmanagement.controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hostelmanagement.DAO.HostelDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Hostel;

public class CreateHostel extends AppCompatActivity {

    EditText edtAddress, edtOwner, edtPhone, edtElectric, edtWater, edtVehicle, edtInternet, edtLaundry, edtElevator, edtTv, edtGarbage, edtService, edtBankOwner, edtBank, edtBankAccount;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_hostel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtAddress = findViewById(R.id.edt_address);
        edtOwner = findViewById(R.id.edt_owner);
        edtPhone = findViewById(R.id.edt_phone);
        edtElectric = findViewById(R.id.edt_electric);
        edtWater = findViewById(R.id.edt_water);
        edtVehicle= findViewById(R.id.edt_vehicle);
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

        btnSave.setOnClickListener(v -> {
            Hostel room = new Hostel(
                    edtAddress.getText().toString(),
                    edtOwner.getText().toString(),
                    edtPhone.getText().toString(),
                    Integer.parseInt(edtElectric.getText().toString()),
                    Integer.parseInt(edtWater.getText().toString()),
                    Integer.parseInt(edtVehicle.getText().toString()),
                    Integer.parseInt(edtInternet.getText().toString()),
                    Integer.parseInt(edtLaundry.getText().toString()),
                    Integer.parseInt(edtElevator.getText().toString()),
                    Integer.parseInt(edtTv.getText().toString()),
                    Integer.parseInt(edtGarbage.getText().toString()),
                    Integer.parseInt(edtService.getText().toString()),
                    edtBankOwner.getText().toString(),
                    edtBank.getText().toString(),
                    edtBankAccount.getText().toString()
            );

            HostelDAO dao = new HostelDAO(this);
            long result = dao.insertRoom(room);

            if (result != -1) {
                Toast.makeText(this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lỗi khi lưu!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}