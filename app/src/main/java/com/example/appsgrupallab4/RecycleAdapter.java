package com.example.appsgrupallab4;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ImageViewHolder> {
    private ArrayList<String> comentario;
    private ArrayList<String> usuario;
    private ArrayList<Uri>  imagen;
    private Context context;

    public RecycleAdapter(ArrayList<String>  comentario, ArrayList<String>  usuario, ArrayList<Uri>  imagen,Context context){
    this.context=context;
    this.comentario=comentario;
    this.usuario=usuario;
    this.imagen=imagen;

    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.pantalla_principal, parent, false);
        ImageViewHolder imageViewHolder=new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(context)
                .load(imagen.get(position))
                .into(holder.imageView1);

        holder.textView1.setText(usuario.get(position));
        holder.textView2.setText(comentario.get(position));


    }



    @Override
    public int getItemCount() {
        return usuario.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        TextView textView1;
        TextView textView2;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imageView3);
            textView1 = itemView.findViewById(R.id.textView);
            textView2= itemView.findViewById(R.id.textView2);
        }
    }
}
