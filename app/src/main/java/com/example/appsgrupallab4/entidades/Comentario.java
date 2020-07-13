package com.example.appsgrupallab4.entidades;

import java.util.Date;

public class Comentario {
    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    private Date fechaSubida;
    private String nombreUser;
    private String contenido;
    private String userUID;

    public String getComentarioID() {
        return comentarioID;
    }

    public void setComentarioID(String comentarioID) {
        this.comentarioID = comentarioID;
    }

    private String comentarioID;
}
