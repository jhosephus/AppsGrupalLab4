package com.example.appsgrupallab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AgregarComentario extends AppCompatActivity {

    private String postUID;

    //Form
    private EditText comentario;
    private Button publicar;

    //Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_comentario);

        setActivity();
    }

    public void setActivity(){
        comentario = findViewById(R.id.AgregarComentario_Comentario);
        publicar = findViewById(R.id.AgregarComentario_Publicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicarComentario();
            }
        });
    }

    public void publicarComentario(){
        String nombre = currentUser.getDisplayName();
        //String nombre = "José";
        Date currentTime = Calendar.getInstance().getTime();
        String texto = comentario.getText().toString();

        Map<String, Object> comentarioData = new HashMap<String, Object>();
        comentarioData.put("fechaSubida", currentTime);
        comentarioData.put("nombreUser", nombre);
        comentarioData.put("contenido", texto);
        comentarioData.put("userUID", currentUser.getUid());

        postUID = "123456";

        db.collection("posts").document(postUID).collection("comentarios")
                .add(comentarioData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AgregarComentario.this,"Publicado",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK,new Intent());
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarComentario.this,"Ocurrió algo",Toast.LENGTH_SHORT).show();
            }
        });


    }

}