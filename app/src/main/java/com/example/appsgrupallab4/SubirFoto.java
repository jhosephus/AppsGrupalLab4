package com.example.appsgrupallab4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SubirFoto extends AppCompatActivity {

    static final int GALLERY_REQUEST_CODE = 1;

    private boolean isFoto = false;
    private boolean isTexto = false;

    //Form
    private EditText descripcion;
    private Button galeria;
    private ImageView foto;
    private TextView publicar;

    //Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private StorageReference mountainsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);

        setActivity();
    }

    public void setActivity() {

        descripcion = findViewById(R.id.SubirFoto_Descripcion);
        galeria = findViewById(R.id.SubirFoto_Galeria);
        foto = findViewById(R.id.SubirFoto_Foto);
        publicar = findViewById(R.id.SubirFoto_Tag1);

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarGaleria();
            }
        });

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicarPost();
            }
        });

    }

    public void cargarGaleria () {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    public void publicarPost () {

        isTexto = !descripcion.getText().toString().isEmpty();

        if (isTexto && isFoto){
            uploadInfo();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    foto.setImageURI(selectedImage);
                    isFoto = true;
                    break;
                default:
                    break;
            }
        }

    }

    private void uploadInfo() {

        //String nombre = currentUser.getDisplayName();
        String nombre = "Jose";
        Date currentTime = Calendar.getInstance().getTime();
        String texto = descripcion.getText().toString();

        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("fechaSubida", currentTime);
        postData.put("nombreUser", nombre);
        postData.put("descripcion", texto);
        //postData.put("userUID", currentUser.getUid());
        postData.put("userUID", "1452");

        db.collection("posts").add(postData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SubirFoto.this,"Publicado",Toast.LENGTH_SHORT).show();
                        String fotoUID;
                        //fotoUID = currentUser.getUid() +"-"+documentReference.getId();
                        fotoUID = "1452" +"-"+documentReference.getId();
                        uploadImage(fotoUID);
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SubirFoto.this,"Ocurrio algo",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadImage(final String idFoto){
        storageRef = storage.getReference();
        mountainsRef = storageRef.child(idFoto + ".jpg");
        // Get the data from an ImageView as bytes
        foto.setDrawingCacheEnabled(true);
        foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(SubirFoto.this,"Fail",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Map<String,Object> infoFoto = new HashMap<>();
                infoFoto.put("idFoto", idFoto);
                db.collection("incidencias").document(idFoto)
                        .update(infoFoto)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SubirFoto.this,"Success",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                setResult(RESULT_OK,intent);
                                //finish();
                            }
                        });
            }
        });
    }

}