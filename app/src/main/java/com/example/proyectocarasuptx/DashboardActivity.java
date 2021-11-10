package com.example.proyectocarasuptx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    CardView scannBtn, howtoBtn, infoBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        scannBtn = findViewById(R.id.scann_btn);
        howtoBtn = findViewById(R.id.howto_btn);
        infoBtn = findViewById(R.id.info_btn);

        scannBtn.setOnClickListener(this);
        howtoBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());
    }


    @Override
    public void onClick(View v) {
        Intent i = null;
        int id = v.getId();

        if (id == R.id.scann_btn) i = new Intent(DashboardActivity.this, FaceScann.class);
        else if (id == R.id.howto_btn) i = new Intent(DashboardActivity.this, HowTo.class);
        else if (id == R.id.info_btn) i = new Intent(DashboardActivity.this, InfoSection.class);

        try {
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(DashboardActivity.this, "Algo salió mal: " + e, Toast.LENGTH_SHORT).show();
        }

    }
}