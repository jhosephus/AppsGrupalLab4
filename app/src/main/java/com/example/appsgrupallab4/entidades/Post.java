package com.example.appsgrupallab4.entidades;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Serializable {

    private String postId;
    private String nombreUser;
    private String userUID;
    private Date fechaSubida;
    private String stringDate;
    private String descripcion;
    private ArrayList<Comentario> comment;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date date) {
        this.fechaSubida = date;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Comentario> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comentario> comment) {
        this.comment = comment;
    }
}
