package com.example.proyectocarasuptx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout userLayout, passLayout;
    MaterialButton loginBtn, forgottenAccountBtn, infoBtn;
    Dialog loginErrorDialog, forgottenAccountDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLayout = findViewById(R.id.username_layout);
        passLayout = findViewById(R.id.password_layout);
        loginBtn = findViewById(R.id.login_btn);
        infoBtn = findViewById(R.id.info_btn);
        forgottenAccountBtn = findViewById(R.id.forget_pass_btn);

        loginErrorDialog = new Dialog(this);
        forgottenAccountDialog = new Dialog(this);

        // Field watchers
        Objects.requireNonNull(userLayout.getEditText()).addTextChangedListener(userFieldWatcher);
        Objects.requireNonNull(passLayout.getEditText()).addTextChangedListener(passFieldWatcher);

        // Login button action
        loginBtn.setOnClickListener(v -> {
            if (Validate.emptyFields(userLayout, passLayout)) loginAtempt();
        });

        // Info button action
        infoBtn.setOnClickListener(v -> startInfoActivity());

        // Forgotten account button action
        forgottenAccountBtn.setOnClickListener(v -> showForgottenAcountMessage());
    }

    // Login atempt
    public void loginAtempt() {
        if (Validate.dataFields(userLayout, passLayout)) startNextAtempt();
        else showLoginError();
    }

    // Start next activity
    public void startNextAtempt() {
        Intent load = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(load);
        finish();
    }

    // Start info activity{
    public void startInfoActivity(){
        Intent load = new Intent(LoginActivity.this, InfoSection.class);
        startActivity(load);
    }

    // Show dialog if login fails
    public void showLoginError(){
        loginErrorDialog.setContentView(R.layout.dialog_login_error);
        loginErrorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialButton confirmBtn = loginErrorDialog.findViewById(R.id.login_error_btn);

        confirmBtn.setOnClickListener(v -> loginErrorDialog.dismiss());

        loginErrorDialog.show();
    }

    // Show dialog for forgoten password
    public void showForgottenAcountMessage(){
        forgottenAccountDialog.setContentView(R.layout.dialog_forgotten_account);
        forgottenAccountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialButton confirmBtn = forgottenAccountDialog.findViewById(R.id.forgotten_account_btn);

         confirmBtn.setOnClickListener(v -> forgottenAccountDialog.dismiss());

         forgottenAccountDialog.show();
    }

    // Watcher for username field
    private final TextWatcher userFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Validate.clearEmptyErrorWatcher(userLayout);
        }
    };

    // Watcher for password field
    private final TextWatcher passFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Validate.clearEmptyErrorWatcher(passLayout);
        }
    };
}