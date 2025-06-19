package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hostelmanagement.DAO.UserDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.User;

public class Register extends AppCompatActivity {

    Button btnLoginLink, btnRegister;
    EditText username, phone, password, confirmPass;
    TextView resError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.editUsername);
        phone = findViewById(R.id.editPhoneRes);
        password = findViewById(R.id.editPasswordRes);
        confirmPass = findViewById(R.id.editPasswordRes2);
        btnRegister = findViewById(R.id.btnRes);
        btnLoginLink = findViewById(R.id.btnLoginLink);
        resError = findViewById(R.id.resError);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameInput = username.getText().toString().trim();
                String phoneInput = phone.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                String confirmPassInput = confirmPass.getText().toString().trim();

                resError.setText("");

                if (usernameInput.isEmpty() || phoneInput.isEmpty() || passwordInput.isEmpty() || confirmPassInput.isEmpty()) {
                    resError.setText("Vui lòng nhập đầy đủ thông tin.");
                    return;
                }

                if (!phoneInput.matches("^0[0-9]{9}$")) {
                    resError.setText("Số điện thoại không hợp lệ.");
                    return;
                }

                if (passwordInput.length() < 3 || passwordInput.length() > 12) {
                    resError.setText("Mật khẩu phải từ 3 đến 12 ký tự.");
                    return;
                }

                if (!passwordInput.equals(confirmPassInput)) {
                    resError.setText("Mật khẩu xác nhận không khớp.");
                    return;
                }

                UserDAO userDAO = new UserDAO(Register.this);
                if (userDAO.isPhoneExists(phoneInput)) {
                    resError.setText("Số điện thoại đã được đăng ký.");
                    return;
                }

                User newUser = new User(usernameInput, phoneInput, passwordInput, "renter");
                boolean insertSuccess = userDAO.addUser(newUser) > 0;
                if (insertSuccess) {
                    resError.setText("");
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    resError.setText("Đăng ký thất bại. Vui lòng thử lại.");
                }
            }
        });


        btnLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}