package com.example.appsgrupallab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appsgrupallab4.Adapters.ComentariosAdapter;
import com.example.appsgrupallab4.entidades.Comentario;
import com.example.appsgrupallab4.entidades.Post;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private StorageReference storageReference;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Log.d("msgxd", "aea");

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
        crearRecyclerView((Post) post);


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
        //imageDetalles.setText("");

        
        String fechaParsed = new SimpleDateFormat("dd/MM/yyyy").format(p.getFechaSubida());
        dateDetalles.setText(fechaParsed);

        if (p.getComment() != null) {
            cantidadComentariosDetalles.setText("Cantidad de comentarios" + p.getComment().size());
        }else{
            cantidadComentariosDetalles.setText("No hay comentarios");

        }
        descripcionDetalles.setText(p.getDescripcion());

        String fileName = p.getPostId() + ".jpg";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(fileName);
        Glide.with(DetallesActivity.this)
                .load(storageReference)
                .into(imageDetalles);

    }

    public void crearRecyclerView(Post p) {
        ComentariosAdapter adapter = new ComentariosAdapter(p.getComment(), DetallesActivity.this);
        rv = findViewById(R.id.recyclerComentariosDetalles);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(DetallesActivity.this));

    }
}
/*
    public void getComentarios(final Post p, final Callback callback) {
        String postId = p.getPostId();

        //String pathPost = "posts/" + postId + "/comentarios/";


        fireStore.collection("post").document(postId).collection("comentarios").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Comentario> listaComentarios = p.getComment();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("msgxd", document.getId() + " => " + document.getData());
                                Comentario c = document.toObject(Comentario.class);
                                listaComentarios.add(c);
                            }
                            callback.onSuccess(listaComentarios);
                        } else {
                            Log.d("msgxd", "Error getting documents: ", task.getException());

                        }
                    }
                });
    }
*/
