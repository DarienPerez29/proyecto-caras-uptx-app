package com.example.proyectocarasuptx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DashboardActivity extends AppCompatActivity{

    ImageView backBtn;

    public static final int SCAN_CODE = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    // ========================= FUNCIONES PRINCIPALES ============================================
    // Función de escanear
    public void loadScaner(View v){
        decodePermission(SCAN_CODE);
    }

    // Función de tutorial
    public void loadHowTo(View v){
        Intent i = new Intent(this, HowToActivity.class);
        this.startActivity(i);
    }

    // Función de más información
    public void loadInfo(View v){
        Intent i = new Intent(this, InfoActivity.class);
        this.startActivity(i);
    }

    // ========================= PERMISOS =========================================================
    public void decodePermission(int requestCode){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == SCAN_CODE) {
            Intent i = new Intent(this, ScanActivity.class);
            this.startActivity(i);
        }
    }
}