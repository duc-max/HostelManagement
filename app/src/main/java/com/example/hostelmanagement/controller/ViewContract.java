package com.example.hostelmanagement.controller;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hostelmanagement.DAO.ContractDAO;
import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Contract;
import com.example.hostelmanagement.model.Room;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViewContract extends AppCompatActivity {

    // UI elements
    private TextView tvHeaderTitle;
    private EditText edtContractNumber, edtCustomerName, edtPhone, edtAddress, edtRoom,
            edtNumPeople, edtRoomPrice, edtDeposit, edtStartDate, edtDuration,
            edtElectricIndex, edtBikeCount, edtNotes;
    private CheckBox chkReminder, chkParking, chkInternet, chkLaundry;
    private Button btnSaveChanges, btnCancelContract;
    private ImageView btnBack;

    // DAO
    private ContractDAO contractDAO;
    private RoomDAO roomDAO;

    // Contract data
    private int contractId = -1;
    private Contract currentContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_contract); // Ensure this matches your XML layout file name

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DAOs
        contractDAO = new ContractDAO(this);
        roomDAO = new RoomDAO(this);

        // Initialize UI elements
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
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
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnCancelContract = findViewById(R.id.btnCancelContract);

        // Make Contract Number and Room fields non-editable as they are identifiers
        edtContractNumber.setFocusable(false);
        edtContractNumber.setClickable(false);
        edtRoom.setFocusable(false);
        edtRoom.setClickable(false);

        // Get contract ID from Intent
        Intent intent = getIntent();
        contractId = intent.getIntExtra("contract_id", -1); // Default to -1 if not found

        if (contractId != -1) {
            // If a contract_id is passed, it means we are viewing/editing an existing contract
            tvHeaderTitle.setText("Thông tin hợp đồng"); // Change header title
            loadContractData(contractId); // Load data for this contract
        } else {
            // Handle case where no contract_id is passed (e.g., error or unexpected navigation)
            Toast.makeText(this, "Không tìm thấy hợp đồng để xem.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
            return;
        }

        // Set Listeners
        btnBack.setOnClickListener(v -> finish()); // Go back to the previous activity

        btnSaveChanges.setOnClickListener(v -> saveContractChanges());
        btnCancelContract.setOnClickListener(v -> showDeleteConfirmationDialog());

        // Set up DatePickerDialog for StartDate
        edtStartDate.setOnClickListener(v -> showDatePickerDialog());

        // Listener for Parking checkbox to show/hide bike count
        chkParking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            edtBikeCount.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (!isChecked) {
                edtBikeCount.setText("0"); // Clear bike count if parking is unchecked
            }
        });
    }

    /**
     * Loads contract data from the database and populates the UI fields.
     * @param id The ID of the contract to load.
     */
    private void loadContractData(int id) {
        currentContract = contractDAO.getContractById(id);
       Room room = roomDAO.getRoomById(currentContract.getRoom());
        if (currentContract != null) {
            edtContractNumber.setText(currentContract.getContractNumber());
            edtCustomerName.setText(currentContract.getCustomerName());
            edtPhone.setText(currentContract.getPhone());
            edtAddress.setText(currentContract.getAddress());
            edtRoom.setText(room.getRoomName());
            edtNumPeople.setText(String.valueOf(currentContract.getNumPeople()));
            edtRoomPrice.setText(String.valueOf(currentContract.getRoomPrice()));
            edtDeposit.setText(String.valueOf(currentContract.getDeposit()));
            edtStartDate.setText(currentContract.getStartDate());
            edtDuration.setText(String.valueOf(currentContract.getDuration()));
            edtElectricIndex.setText(String.valueOf(currentContract.getElectricIndex()));
            edtBikeCount.setText(String.valueOf(currentContract.getBikeCount()));
            edtNotes.setText(currentContract.getNote());

            chkReminder.setChecked(currentContract.getReminder() == 1);
            chkParking.setChecked(currentContract.getHasParking() == 1);
            chkInternet.setChecked(currentContract.getHasInternet() == 1);
            chkLaundry.setChecked(currentContract.getHasLaundry() == 1);

            // Initial visibility of bike count based on loaded data
            edtBikeCount.setVisibility(chkParking.isChecked() ? View.VISIBLE : View.GONE);

        } else {
            Toast.makeText(this, "Không tìm thấy thông tin hợp đồng.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Saves changes to the current contract to the database.
     */
    private void saveContractChanges() {
        if (currentContract == null) {
            Toast.makeText(this, "Không có hợp đồng để lưu.", Toast.LENGTH_SHORT).show();
            return;
        }

        currentContract.setCustomerName(edtCustomerName.getText().toString());
        currentContract.setPhone(edtPhone.getText().toString());
        currentContract.setAddress(edtAddress.getText().toString());

        try {
            currentContract.setNumPeople(Integer.parseInt(edtNumPeople.getText().toString()));
        } catch (NumberFormatException e) {
            currentContract.setNumPeople(0);
        }
        try {
            currentContract.setRoomPrice(Integer.parseInt(edtRoomPrice.getText().toString()));
        } catch (NumberFormatException e) {
            currentContract.setRoomPrice(0);
        }
        try {
            currentContract.setDeposit(Integer.parseInt(edtDeposit.getText().toString()));
        } catch (NumberFormatException e) {
            currentContract.setDeposit(0);
        }
        currentContract.setStartDate(edtStartDate.getText().toString());
        try {
            currentContract.setDuration(Integer.parseInt(edtDuration.getText().toString()));
        } catch (NumberFormatException e) {
            currentContract.setDuration(0);
        }
        currentContract.setReminder(chkReminder.isChecked() ? 1 : 0);
        try {
            currentContract.setElectricIndex(Integer.parseInt(edtElectricIndex.getText().toString()));
        } catch (NumberFormatException e) {
            currentContract.setElectricIndex(0);
        }
        currentContract.setHasParking(chkParking.isChecked() ? 1 : 0);
        try {
            currentContract.setBikeCount(Integer.parseInt(edtBikeCount.getText().toString()));
        } catch (NumberFormatException e) {
            currentContract.setBikeCount(0);
        }
        currentContract.setHasInternet(chkInternet.isChecked() ? 1 : 0);
        currentContract.setHasLaundry(chkLaundry.isChecked() ? 1 : 0);
        currentContract.setNote(edtNotes.getText().toString());

        // Perform the update operation in the database
        int rowsAffected = contractDAO.updateContract(currentContract);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật hợp đồng thành công!", Toast.LENGTH_SHORT).show();
            // You might want to refresh the UI or simply finish the activity
            // finish();
        } else {
            Toast.makeText(this, "Cập nhật hợp đồng thất bại.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays a confirmation dialog before deleting the contract.
     */
    private void showDeleteConfirmationDialog() {
        if (currentContract == null) {
            Toast.makeText(this, "Không có hợp đồng để xóa.", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa hợp đồng")
                .setMessage("Bạn có chắc chắn muốn xóa hợp đồng này? Thao tác này không thể hoàn tác và sẽ cập nhật trạng thái phòng thành 'Đang trống'.")
                .setPositiveButton("Xóa", (dialog, which) -> deleteContract()) // If confirmed, call deleteContract()
                .setNegativeButton("Hủy", null) // Do nothing if cancelled
                .show();
    }

    /**
     * Deletes the current contract from the database and updates the associated room status.
     */
    private void deleteContract() {
        if (currentContract == null) {
            return;
        }

        int rowsAffected = contractDAO.deleteContract(currentContract.getId());
        if (rowsAffected > 0) {
            Toast.makeText(this, "Hợp đồng đã được xóa!", Toast.LENGTH_SHORT).show();

            Room roomToUpdate = null;
            List<Room> allRooms = roomDAO.getAllRooms();
            for (Room r : allRooms) {
                if (r.getRoomName().equals(currentContract.getRoom())) {
                    roomToUpdate = r;
                    break;
                }
            }

            if (roomToUpdate != null) {
                roomToUpdate.setStatus(false);
                int roomUpdateRows = roomDAO.updateRoom(roomToUpdate);
                if (roomUpdateRows > 0) {
                    Toast.makeText(this, "Trạng thái phòng đã được cập nhật.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cập nhật trạng thái phòng thất bại.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không tìm thấy phòng tương ứng để cập nhật trạng thái.", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(ViewContract.this, ListRoom.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Xóa hợp đồng thất bại.", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDatePickerDialog() {
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month is 0-indexed, so add 1 for display
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    edtStartDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }
}