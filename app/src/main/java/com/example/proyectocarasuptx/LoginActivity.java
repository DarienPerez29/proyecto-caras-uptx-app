package com.example.proyectocarasuptx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout userField = (TextInputLayout) findViewById(R.id.username_field);
        TextInputLayout passField = (TextInputLayout) findViewById(R.id.password_field);

        MaterialButton loginBtn = (MaterialButton) findViewById(R.id.login_btn);
        MaterialButton fpassBtn = (MaterialButton) findViewById(R.id.forget_pass_btn);

        try{
            loginBtn.setOnClickListener(v -> {
                String username = Objects.requireNonNull(userField.getEditText()).getText().toString();
                String password = Objects.requireNonNull(passField.getEditText()).getText().toString();

                if(username.equals("admin") && password.equals("pass")){
                    Intent load = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(load);
                    finish();
                }
            });
        }catch(Exception e){
            System.out.println(e);
        }


    }
}