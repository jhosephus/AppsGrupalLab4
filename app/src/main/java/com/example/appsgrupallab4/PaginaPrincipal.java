package com.example.appsgrupallab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

public class PaginaPrincipal extends AppCompatActivity {

    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
     ArrayList<String> comentario;
     ArrayList<String> usuario;
     ArrayList<Uri>  imagen;
     Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        recyclerView=findViewById(R.id.recycle);
        recycleAdapter= new RecycleAdapter(comentario,usuario,imagen,PaginaPrincipal.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recycleAdapter);


    }
}