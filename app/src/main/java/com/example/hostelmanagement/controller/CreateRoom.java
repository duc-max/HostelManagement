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

import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Room;

public class CreateRoom extends AppCompatActivity {

    EditText edtRoomName, edtRoomPrice, edtNote;
    Button btnCreateRoom;
    int hostelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtRoomName = findViewById(R.id.edtRoomName);
        edtRoomPrice = findViewById(R.id.edtRoomPrice);
        edtNote = findViewById(R.id.edtNote);
        btnCreateRoom = findViewById(R.id.btnSave);

        hostelId = getIntent().getIntExtra("hostel_id", -1);

        btnCreateRoom.setOnClickListener(v -> {
            String name = edtRoomName.getText().toString().trim();
            String priceStr = edtRoomPrice.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);
            String note = edtNote.getText().toString().trim();

            Room room = new Room();
            room.setRoomName(name);
            room.setPrice(price);
            room.setHostelId(hostelId);
            room.setStatus(false);

            RoomDAO roomDAO = new RoomDAO(this);
            long result = roomDAO.insertRoom(room);
            if (result > 0) {
                Toast.makeText(this, "Thêm phòng thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Thêm phòng thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}