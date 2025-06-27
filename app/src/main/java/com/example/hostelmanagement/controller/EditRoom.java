package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Room;

public class EditRoom extends AppCompatActivity {

    EditText edtRoomName, edtRoomPrice;
    Button btnSave;
    RoomDAO roomDAO;
    Room room;
    ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_room);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        edtRoomName = findViewById(R.id.edtRoomName);
        edtRoomPrice = findViewById(R.id.edtRoomPrice);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        roomDAO = new RoomDAO(this);
        int roomId = getIntent().getIntExtra("room_id", -1);

        if (roomId != -1) {
            room = roomDAO.getRoomById(roomId);
            if (room != null) {
                edtRoomName.setText(room.getRoomName());
                edtRoomPrice.setText(String.valueOf(room.getPrice()));
                // edtNote.setText(room.getNote()); // nếu có trường note
            }
        }

        btnSave.setOnClickListener(v -> {
            String name = edtRoomName.getText().toString().trim();
            String priceStr = edtRoomPrice.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);

            room.setRoomName(name);
            room.setPrice(price);

            int result = roomDAO.updateRoom(room);
            if (result > 0) {
                Toast.makeText(this, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show();
                finish(); // quay lại màn trước
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(EditRoom.this, ListRoom.class);
            startActivity(intent);
        });
    }

}