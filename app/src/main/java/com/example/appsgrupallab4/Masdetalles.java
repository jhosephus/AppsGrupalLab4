package com.example.appsgrupallab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Masdetalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masdetalles);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}