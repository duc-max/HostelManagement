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

import com.example.hostelmanagement.R;

public class Login extends AppCompatActivity {

    EditText phone, password;
    TextView errorLogin, createLink, forgetPassLink;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnForgetPass), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phone = findViewById(R.id.editPhone);
        password = findViewById(R.id.editPassword);
        createLink = findViewById(R.id.linkCreate);
        errorLogin = findViewById(R.id.loginError);
        forgetPassLink = findViewById(R.id.btnForgetPass);
        btnLogin = findViewById(R.id.btnLogin);


    }
}