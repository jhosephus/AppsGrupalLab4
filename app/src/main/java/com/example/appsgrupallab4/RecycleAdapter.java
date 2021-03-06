package com.example.appsgrupallab4;

import android.content.Context;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
    private ArrayList<String> nombreUsuario;
    private Context context;


    private OnNoteListener onNoteListener;
    StorageReference storageReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public RecycleAdapter(ArrayList<String> comentario, ArrayList<String> usuario, ArrayList<String> imagen, ArrayList<String> nombreUsuario, Context context, OnNoteListener onNoteListener) {
        this.context = context;
        this.comentario = comentario;
        this.usuario = usuario;
        this.nombreUsuario = nombreUsuario;
        this.imagen = imagen;
        this.onNoteListener = onNoteListener;

    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantalla_principal, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view, onNoteListener);
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


        holder.textView1.setText(nombreUsuario.get(position));
        holder.textView2.setText(comentario.get(position));


    }

    public interface OnNoteListener {
        void onNoteClick(int position);


    }

    @Override
    public int getItemCount() {
        return imagen.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView1, verdetalle;
        Button verdetalle2;
        TextView textView1;
        TextView textView2;
        OnNoteListener onNoteListener1;



        public ImageViewHolder(@NonNull View itemView, final OnNoteListener onNoteListener) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imageView3);
            textView1 = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

            verdetalle2=itemView.findViewById(R.id.button2);
            this.onNoteListener1 = onNoteListener;
            verdetalle2.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onNoteListener1.onNoteClick(getAdapterPosition());
        }

    }
}
