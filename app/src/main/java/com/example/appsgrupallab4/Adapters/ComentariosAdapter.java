package com.example.appsgrupallab4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsgrupallab4.DetallesActivity;
import com.example.appsgrupallab4.R;
import com.example.appsgrupallab4.entidades.Comentario;

import java.util.ArrayList;

public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.ComentariosViewHolder> {

    private ArrayList<Comentario> listaComentarios;
    private Context contexto;

    public ComentariosAdapter( ArrayList<Comentario> listaComentarios, Context c) {
        this.listaComentarios = listaComentarios;
        this.contexto = c;
    }


    public static class ComentariosViewHolder extends RecyclerView.ViewHolder {
        public TextView comentarioDate;
        public TextView comentarioBody;

        public ComentariosViewHolder(View itemView) {
            super(itemView);

            this.comentarioBody = itemView.findViewById(R.id.textComentarioDetalles);
            this.comentarioDate = itemView.findViewById(R.id.dateComentarioDetalles);
        }
    }


    @NonNull
    @Override
    public ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.item_comentarios_rv, parent, false);
        ComentariosViewHolder comentariosViewHolder = new ComentariosViewHolder(itemView);
        return comentariosViewHolder;
    }


    public void onBindViewHolder(ComentariosViewHolder holder, int position) {
        Comentario c = listaComentarios.get(position);
        holder.comentarioDate.setText(c.getContenido());
        holder.comentarioDate.setText(c.getCurrentTime().toString());

    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }
}
