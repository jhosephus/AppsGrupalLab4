package com.example.appsgrupallab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appsgrupallab4.Adapters.ComentariosAdapter;
import com.example.appsgrupallab4.entidades.Comentario;
import com.example.appsgrupallab4.entidades.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetallesActivity extends AppCompatActivity {

    final int START_ACTIVITY_CODE = 2;
    private FirebaseFirestore fireStore;
    FirebaseFirestore db;
    private StorageReference storageReference;
    FirebaseStorage storage;


    private ArrayList<Comentario> listaComentarios;
    private RecyclerView rv;
    //private ComentariosAdapter comentariosAdapter;

    private Post post;
    private String userId;

    private TextView userNameDetalles;
    private ImageView imageDetalles;
    private TextView dateDetalles;
    private TextView cantidadComentariosDetalles;
    private TextView descripcionDetalles;
    private Button agregarComentarioDetalles;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private ActionBar actionBar;

    Comentario comentario = new Comentario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        Log.d("msgxd", "aea");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        //stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
        // https://docs.google.com/presentation/d/1DxqH1h0RObwFGQYW4d2664IV-pE5GsgceXJps71-g6A/edit#slide=id.g73bffae9ff_0_88

        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();
        //userId = intent.getStringExtra("userId");

        Log.d("msgxd", post.getDescripcion());
        llenarCampos((Post) post);
        setActivity((Post) post);


        findViewById(R.id.agregarComentarioDetalles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesActivity.this, AgregarComentario.class);
                intent.putExtra("post", (Serializable) post);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, START_ACTIVITY_CODE); // javapoint.com/android-startactivityforresult-example;
            }
        });


    }

    public void llenarCampos(Post p) {
/*
    private TextView userNameDetalles;
    private ImageView imageDetalles;
    private TextView dateDetalles;
    private TextView cantidadComentariosDetalles;
    private TextView descripcionDetalles;
 */
        userNameDetalles = findViewById(R.id.userNameDetalles);
        imageDetalles = findViewById(R.id.imageDetalles);
        dateDetalles = findViewById(R.id.dateDetalles);
        cantidadComentariosDetalles = findViewById(R.id.cantidadComentariosDetalles);
        descripcionDetalles = findViewById(R.id.descripcionDetalles);

        userNameDetalles.setText(p.getNombreUser());


        String fechaParsed = new SimpleDateFormat("dd/MM/yyyy").format(p.getFechaSubida());
        dateDetalles.setText(fechaParsed);

        Log.d("msgxd", "estoyaca");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        String collectionComentariosPath = "posts/" + p.getPostId() + "/comentarios";

        Log.d("msgxd", "estoyaca2");
        db.collection(collectionComentariosPath).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("msgxd", "estoyaca3");
                if (task.isSuccessful()) {
                    ArrayList<Comentario> listaComentarios = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        comentario = documentSnapshot.toObject(Comentario.class);
                        comentario.setComentarioID(documentSnapshot.getId());
                        listaComentarios.add(comentario);

                        Log.d("msgxd", "estoyaca4");
                    }
                    if (listaComentarios.size() > 0) {
                        Log.d("msgxd", "estoyaca5");
                        cantidadComentariosDetalles.setText("Cantidad de comentarios: " + listaComentarios.size());
                        crearRecyclerView(listaComentarios);
                    } else {
                        cantidadComentariosDetalles.setText("No hay comentarios");
                    }
                }
            }
        });


        descripcionDetalles.setText(p.getDescripcion());


        String fileName = p.getUserUID() + "-" + p.getPostId() + ".jpg";
        Log.d("msgxd", fileName);
        Log.d("msgxd", "0910");

        StorageReference idk = storageReference.child(fileName);
        idk.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetallesActivity.this)
                        .load(uri)
                        .into(imageDetalles);
            }
        });
    }

    public void setActivity(Post p) {
        mAuth = FirebaseAuth.getInstance();
        actionBar = getSupportActionBar();
        actionBar.setTitle(p.getNombreUser());

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    public void crearRecyclerView(ArrayList<Comentario> listaComentarios) {
        ComentariosAdapter adapter = new ComentariosAdapter(listaComentarios, DetallesActivity.this);
        rv = findViewById(R.id.recyclerComentariosDetalles);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(DetallesActivity.this));

    }
}