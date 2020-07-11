package com.example.appsgrupallab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import com.example.appsgrupallab4.entidades.Comentario;
import com.example.appsgrupallab4.entidades.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetallesActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        fireStore = FirebaseFirestore.getInstance();
    }

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
}