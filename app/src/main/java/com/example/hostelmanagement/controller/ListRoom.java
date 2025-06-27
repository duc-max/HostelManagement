package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Room;

import java.util.List;

public class ListRoom extends AppCompatActivity {

    ImageView btnBack, btnCreateRoom;
    int hostelId;
    RecyclerView rvRooms;
    RoomDAO roomDAO;
    RoomAdapter roomAdapter;
    List<Room> roomList;

    @Override
    protected void onResume() {
        super.onResume();
        RadioGroup radioGroup = findViewById(R.id.radioGroupStatus);
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioAvailable) {
            loadRoomListByStatus(false);
        } else {
            loadRoomListByStatus(true);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_room);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnBack);
        btnCreateRoom = findViewById(R.id.btnCreateRoom);
        rvRooms = findViewById(R.id.rvRooms);
        RadioGroup radioGroupStatus = findViewById(R.id.radioGroupStatus);
        RadioButton radioAvailable = findViewById(R.id.radioAvailable);
        RadioButton radioRented = findViewById(R.id.radioRented);

        hostelId = getIntent().getIntExtra("hostel_id", -1);
        roomDAO = new RoomDAO(this);

        rvRooms.setLayoutManager(new LinearLayoutManager(this));

        radioGroupStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioAvailable) {
                loadRoomListByStatus(false);
            } else if (checkedId == R.id.radioRented) {
                loadRoomListByStatus(true);
            }
        });

        radioAvailable.setChecked(true);

        btnCreateRoom.setOnClickListener(v -> {
            Intent intent = new Intent(ListRoom.this, CreateRoom.class);
            intent.putExtra("hostel_id", hostelId);
            startActivity(intent);
        });


        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ListRoom.this, ListHostel.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadRoomListByStatus(boolean status) {
        roomList = roomDAO.getRoomsByHostelIdAndStatus(hostelId, status);
        roomAdapter = new RoomAdapter(this, roomList);
        rvRooms.setAdapter(roomAdapter);
    }
}
