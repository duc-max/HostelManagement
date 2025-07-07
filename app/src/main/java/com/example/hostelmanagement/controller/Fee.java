package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelmanagement.DAO.ContractDAO;
import com.example.hostelmanagement.DAO.HostelDAO;
import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Contract;
import com.example.hostelmanagement.model.Hostel;
import com.example.hostelmanagement.model.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fee extends AppCompatActivity {

    Spinner spinnerHostel;
    LinearLayout roomListContainer;
    ImageView btnBack;

    HostelDAO hostelDAO;
    RoomDAO roomDAO;
    ContractDAO contractDAO;
    List<Hostel> hostelList;
    List<String> hostelNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        spinnerHostel = findViewById(R.id.spinnerHostel);
        roomListContainer = findViewById(R.id.roomListContainer);
        btnBack = findViewById(R.id.btnBack);
        hostelDAO = new HostelDAO(this);
        roomDAO = new RoomDAO(this);
        contractDAO = new ContractDAO(this);

        loadHostels();

        spinnerHostel.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Hostel selectedHostel = hostelList.get(position);
                loadRentedRooms(selectedHostel.getId());
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });


        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Fee.this, Dashboard.class);
            startActivity(intent);
        });
    }

    private void loadHostels() {
        hostelList = hostelDAO.getAllRooms();
        hostelNames = new ArrayList<>();
        for (Hostel hostel : hostelList) {
            hostelNames.add(hostel.getAddress());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hostelNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHostel.setAdapter(adapter);
    }

    private void loadRentedRooms(int hostelId) {
        roomListContainer.removeAllViews();
        List<Room> rooms = roomDAO.getRoomsByHostelIdAndStatus(hostelId, true);

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        for (Room room : rooms) {
            LinearLayout roomCard = new LinearLayout(this);
            roomCard.setOrientation(LinearLayout.VERTICAL);
            roomCard.setBackgroundResource(R.drawable.room_item_background);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 24);
            roomCard.setLayoutParams(params);
            roomCard.setPadding(32, 32, 32, 32);

            TextView roomName = new TextView(this);
            roomName.setText(room.getRoomName());
            roomName.setTextSize(20);
            roomName.setTypeface(null, Typeface.BOLD);
            roomName.setPadding(0, 20, 0, 8);

            Contract contract = contractDAO.getContractByRoomNumber(room.getId());
            TextView renter = new TextView(this);
            if (contract != null) {
                renter.setText("Người thuê: " + contract.getCustomerName() + " - ĐT: " + contract.getPhone());
            } else {
                renter.setText("Không tìm thấy hợp đồng thuê.");
            }
            renter.setTextSize(14);
            renter.setPadding(0, 0, 0, 16);

            Button btnBaoPhi = new Button(this);
            btnBaoPhi.setText("Báo phí tháng " + currentMonth + " cho khách");
            btnBaoPhi.setTextColor(Color.WHITE);
            btnBaoPhi.setBackgroundColor(Color.parseColor("#9C27B0"));
            btnBaoPhi.setPadding(20, 16, 20, 16);
            btnBaoPhi.setOnClickListener(v -> {
                showElectricInputDialog(room.getId(), room.getRoomName());
            });

            roomCard.addView(roomName);
            roomCard.addView(renter);
            roomCard.addView(btnBaoPhi);

            roomListContainer.addView(roomCard);
        }

        if (rooms.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("Không có phòng đã cho thuê.");
            tv.setPadding(16, 16, 16, 16);
            roomListContainer.addView(tv);
        }
    }
    private void showElectricInputDialog(int roomId, String roomName) {
        Contract contract = contractDAO.getContractByRoomNumber(roomId);
        if (contract == null) {
            Toast.makeText(this, "Không tìm thấy hợp đồng!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_enter_electric, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView tvRoomInfo = dialogView.findViewById(R.id.tvRoomInfo);
        EditText edtOld = dialogView.findViewById(R.id.edtOldElectric);
        EditText edtNew = dialogView.findViewById(R.id.edtNewElectric);
        TextView tvError = dialogView.findViewById(R.id.tvElectricError);
        Button btnViewBill = dialogView.findViewById(R.id.btnViewBill);

        tvRoomInfo.setText("Phòng: " + roomName + "   Người thuê: " + contract.getCustomerName());
        edtOld.setText(String.valueOf(contract.getElectricIndex()));

        btnViewBill.setOnClickListener(v -> {
            String newIndexStr = edtNew.getText().toString().trim();
            if (newIndexStr.isEmpty()) {
                tvError.setText("Vui lòng nhập chỉ số mới");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            int oldIndex = contract.getElectricIndex();
            int newIndex = Integer.parseInt(newIndexStr);

            if (newIndex <= oldIndex) {
                tvError.setText("Chỉ số mới phải lớn hơn chỉ số cũ");
                tvError.setVisibility(View.VISIBLE);
            } else {
                tvError.setVisibility(View.GONE);
                Intent intent = new Intent(Fee.this, FeeDetail.class);
                intent.putExtra("contractId", contract.getId());
                intent.putExtra("newIndex", newIndex);
                startActivity(intent);
                dialog.dismiss();


            }
        });

        dialog.show();
    }

}
