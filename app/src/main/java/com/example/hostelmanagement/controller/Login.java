package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

public class Login extends AppCompatActivity {

    EditText phone, password;
    TextView errorLogin, createLink, forgetPassLink;
    Button btnLogin;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phone = findViewById(R.id.editPhoneRes);
        password = findViewById(R.id.editPasswordRes);
        createLink = findViewById(R.id.linkCreate);
        errorLogin = findViewById(R.id.resError);
        forgetPassLink = findViewById(R.id.btnForgetPass);
        btnLogin = findViewById(R.id.btnRes);

        userDAO = new UserDAO(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneInput = phone.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                errorLogin.setText("");

                if (phoneInput.isEmpty() || passwordInput.isEmpty()) {
                    errorLogin.setText("Vui lòng nhập đầy đủ số điện thoại và mật khẩu.");
                    return;
                }


                if (!phoneInput.matches("^0[0-9]{9}$")) {
                    errorLogin.setText("Số điện thoại không hợp lệ.");
                    return;
                }


                if (passwordInput.length() < 3 || passwordInput.length() > 12) {
                    errorLogin.setText("Mật khẩu phải từ 3 đến 12 ký tự.");
                    return;
                }

                boolean loginSuccess = false;

                List<User> userList = userDAO.getAllUsers();
                for (User user : userList) {
                    if (user.getPhone().equals(phoneInput) && user.getPassword().equals(passwordInput)) {
                        loginSuccess = true;
                        break;
                    }
                }
                if (loginSuccess) {
                    errorLogin.setText("");
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    errorLogin.setText("Số điện thoại hoặc mật khẩu không đúng.");
                }

            }
        });




        createLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }
}