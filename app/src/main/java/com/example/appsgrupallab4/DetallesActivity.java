package com.example.appsgrupallab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appsgrupallab4.Adapters.ComentariosAdapter;
import com.example.appsgrupallab4.entidades.Comentario;
import com.example.appsgrupallab4.entidades.Post;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DetallesActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;

    private ArrayList<Comentario> listaComentarios;
    private RecyclerView rv;
    //private ComentariosAdapter comentariosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        
        

        //https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
        // https://docs.google.com/presentation/d/1DxqH1h0RObwFGQYW4d2664IV-pE5GsgceXJps71-g6A/edit#slide=id.g73bffae9ff_0_88

        Intent intent = getIntent();
        post = (Post) ((Intent) getInent()).getSerializable("post"); 
        
        crearRecyclerView((Post) post);

        findViewById(R.id.agregarComentarioDetalles).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesActivity.this, AgregarComentario.class);
                intent.putExtra("post", post);
               
            }
        });


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
