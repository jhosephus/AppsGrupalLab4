package com.example.appsgrupallab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appsgrupallab4.Adapters.ComentariosAdapter;
import com.example.appsgrupallab4.entidades.Comentario;
import com.example.appsgrupallab4.entidades.Post;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        //stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
        // https://docs.google.com/presentation/d/1DxqH1h0RObwFGQYW4d2664IV-pE5GsgceXJps71-g6A/edit#slide=id.g73bffae9ff_0_88

        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");
        userId = intent.getStringExtra("userId");


        llenarCampos((Post) post);
        crearRecyclerView((Post) post);


        findViewById(R.id.agregarComentarioDetalles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesActivity.this, AgregarComentario.class);
                intent.putExtra("post", (Serializable) post);
                intent.putExtra("userID", userId);
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
        dateDetalles.setText(p.getDate().toString());
        cantidadComentariosDetalles.setText(p.getComment().size());
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
