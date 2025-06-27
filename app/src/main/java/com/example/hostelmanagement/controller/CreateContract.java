package com.example.hostelmanagement.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hostelmanagement.DAO.ContractDAO;
import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Contract;
import com.example.hostelmanagement.model.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CreateContract extends AppCompatActivity {

    ImageView btnBack;
    TextView edtContractNumber;
    EditText edtCustomerName, edtPhone, edtAddress, edtRoom, edtNumPeople,
            edtRoomPrice, edtDeposit, edtStartDate, edtDuration, edtElectricIndex,
            edtBikeCount, edtNotes;
    CheckBox chkReminder, chkParking, chkInternet, chkLaundry;
    Button btnSaveContract;

    private ContractDAO contractDAO;
    private RoomDAO roomDAO;
    private int roomId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_contract);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contractDAO = new ContractDAO(this);
        roomDAO = new RoomDAO(this);

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        edtContractNumber = findViewById(R.id.edtContractNumber);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtRoom = findViewById(R.id.edtRoom);
        edtNumPeople = findViewById(R.id.edtNumPeople);
        edtRoomPrice = findViewById(R.id.edtRoomPrice);
        edtDeposit = findViewById(R.id.edtDeposit);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtDuration = findViewById(R.id.edtDuration);
        edtElectricIndex = findViewById(R.id.edtElectricIndex);
        edtBikeCount = findViewById(R.id.edtBikeCount);
        edtNotes = findViewById(R.id.edtNotes);
        chkReminder = findViewById(R.id.chkReminder);
        chkParking = findViewById(R.id.chkParking);
        chkInternet = findViewById(R.id.chkInternet);
        chkLaundry = findViewById(R.id.chkLaundry);
        btnSaveContract = findViewById(R.id.btnSaveContract);

        // Set up "Back" button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(CreateContract.this, ListRoom.class);
            startActivity(intent);
            finish(); // Close current activity
        });

        // Get room ID from intent
        roomId = getIntent().getIntExtra("room_id", -1);

        if (roomId != -1) {
            Room room = roomDAO.getRoomById(roomId);
            if (room != null) {
                edtRoom.setText(room.getRoomName());
                edtRoom.setEnabled(false); // Make room name uneditable
                edtRoomPrice.setText(String.valueOf(room.getPrice()));
                edtRoomPrice.setEnabled(false); // Make room price uneditable
            }
        } else {
            Toast.makeText(this, "Không có thông tin phòng được chọn.", Toast.LENGTH_SHORT).show();
            finish(); // Close if no room ID is passed
            return;
        }

        edtStartDate.setOnClickListener(v -> showDatePickerDialog());

        btnSaveContract.setOnClickListener(v -> saveContract());
    }

    private void showDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            edtStartDate.setText(dateFormatter.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private String generateUniqueContractNumber() {
        Random random = new Random();
        String contractNum;
        boolean isUnique;
        do {
            contractNum = String.format(Locale.US, "%06d", random.nextInt(1000000));
            isUnique = true;
            // Check if contract number already exists in the database
            List<Contract> allContracts = contractDAO.getAllContracts();
            for (Contract contract : allContracts) {
                if (contract.getContractNumber().equals(contractNum)) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);
        return contractNum;
    }

    private void saveContract() {
        // Generate and set contract number
        String contractNumber = generateUniqueContractNumber();
        edtContractNumber.setText(contractNumber);

        // Get values from EditTexts
        String customerName = edtCustomerName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String room = edtRoom.getText().toString().trim();
        String numPeopleStr = edtNumPeople.getText().toString().trim();
        String roomPriceStr = edtRoomPrice.getText().toString().trim();
        String depositStr = edtDeposit.getText().toString().trim();
        String startDate = edtStartDate.getText().toString().trim();
        String durationStr = edtDuration.getText().toString().trim();
        String electricIndexStr = edtElectricIndex.getText().toString().trim();
        String bikeCountStr = edtBikeCount.getText().toString().trim();
        String note = edtNotes.getText().toString().trim();

        // Validate required fields
        if (customerName.isEmpty() || address.isEmpty() || room.isEmpty() || numPeopleStr.isEmpty() ||
                roomPriceStr.isEmpty() || startDate.isEmpty() || durationStr.isEmpty() || electricIndexStr.isEmpty() || bikeCountStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tất cả các trường có dấu *", Toast.LENGTH_LONG).show();
            return;
        }

        int numPeople = Integer.parseInt(numPeopleStr);
        int roomPrice = Integer.parseInt(roomPriceStr);
        int deposit = depositStr.isEmpty() ? 0 : Integer.parseInt(depositStr);
        int duration = Integer.parseInt(durationStr);
        int electricIndex = Integer.parseInt(electricIndexStr);
        int bikeCount = Integer.parseInt(bikeCountStr);

        int reminder = chkReminder.isChecked() ? 1 : 0;
        int hasParking = chkParking.isChecked() ? 1 : 0;
        int hasInternet = chkInternet.isChecked() ? 1 : 0;
        int hasLaundry = chkLaundry.isChecked() ? 1 : 0;

        // Create Contract object
        Contract contract = new Contract(contractNumber, customerName, phone, address, room, numPeople,
                roomPrice, deposit, startDate, duration, reminder, electricIndex,
                hasParking, bikeCount, hasInternet, hasLaundry, note);

        // Insert contract into database
        long result = contractDAO.insertContract(contract);

        if (result > 0) {
            // Update room status to true (occupied)
            Room currentRoom = roomDAO.getRoomById(roomId);
            if (currentRoom != null) {
                currentRoom.setStatus(true); // Set status to true
                roomDAO.updateRoom(currentRoom); // Update room in database
                Toast.makeText(this, "Hợp đồng đã được tạo và trạng thái phòng đã cập nhật.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hợp đồng đã được tạo nhưng không tìm thấy phòng để cập nhật trạng thái.", Toast.LENGTH_LONG).show();
            }
            // Navigate back to ListRoom or show success
            Intent intent = new Intent(CreateContract.this, ListRoom.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi tạo hợp đồng. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }
}