package com.example.proyectocarasuptx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout userField, passField;
    MaterialButton loginBtn, fpassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userField = findViewById(R.id.username_field);
        passField = findViewById(R.id.password_field);

        loginBtn = findViewById(R.id.login_btn);
        fpassBtn = findViewById(R.id.forget_pass_btn);

        loginBtn.setOnClickListener(v -> {
            String username = Objects.requireNonNull(userField.getEditText()).getText().toString();
            String password = Objects.requireNonNull(passField.getEditText()).getText().toString();

            //if(username.equals("admin") && password.equals("pass")){
                Intent load = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(load);
                finish();
            //}
        });


    }
}