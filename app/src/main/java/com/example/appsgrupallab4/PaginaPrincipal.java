package com.example.appsgrupallab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.appsgrupallab4.entidades.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class PaginaPrincipal extends AppCompatActivity implements RecycleAdapter.OnNoteListener {
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    ArrayList<String> comentario = new ArrayList<String>();
    ArrayList<String> usuario = new ArrayList<String>();
    ArrayList<String> imagen = new ArrayList<String>();
    File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    ArrayList<Post> posts = new ArrayList<Post>();
    Post postAux = new Post();
    int sais;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        recyclerView = findViewById(R.id.recycle);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        postAux = documentSnapshot.toObject(Post.class);
                        postAux.setPostId(documentSnapshot.getId());
                        posts.add(postAux);
                    }
                    comoseteantoje();
                }
            }
        });


    }

    public void comoseteantoje() {
        sais = posts.size();
        for (int i = 0; i < sais; i++) {
            imagen.add(posts.get(i).getUserUID() + "-" + posts.get(i).getPostId());
            usuario.add(posts.get(i).getUserUID());
            comentario.add(posts.get(i).getDescripcion());


        }

        recycleAdapter = new RecycleAdapter(comentario, usuario, imagen, PaginaPrincipal.this, (RecycleAdapter.OnNoteListener) PaginaPrincipal.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        recyclerView.setAdapter(recycleAdapter);
        Log.d("sais", String.valueOf(sais));
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(PaginaPrincipal.this, Masdetalles.class);
        intent.putExtra("usuario", usuario.get(position));
        intent.putExtra("imagen",imagen.get(position));
        intent.putExtra("descripcion",comentario.get(position));
        startActivityForResult(intent, 1);
    }


}