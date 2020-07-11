package com.example.appsgrupallab4;

import android.content.Context;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ImageViewHolder> {
    private ArrayList<String> comentario;
    private ArrayList<String> usuario;
    private ArrayList<String> imagen;
    private Context context;
    StorageReference storageReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public RecycleAdapter(ArrayList<String> comentario, ArrayList<String> usuario, ArrayList<String> imagen, Context context) {
        this.context = context;
        this.comentario = comentario;
        this.usuario = usuario;
        this.imagen = imagen;

    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantalla_principal, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        storageReference = storage.getReference();

        final StorageReference nose = storageReference.child(imagen.get(position) + ".jpg");
        nose.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.imageView1);

            }
        });


        holder.textView1.setText(usuario.get(position));
        holder.textView2.setText(comentario.get(position));


    }


    @Override
    public int getItemCount() {
        return imagen.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        TextView textView1;
        TextView textView2;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imageView3);
            textView1 = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
