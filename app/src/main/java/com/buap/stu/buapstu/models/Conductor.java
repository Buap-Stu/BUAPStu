package com.buap.stu.buapstu.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Conductor implements Serializable {

    public String uid, telefono, nombre_completo, correo, numero_afiliacion, contrasena;
    public Boolean estado;
    public Conductor(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumero_afiliacion() {
        return numero_afiliacion;
    }

    public void setNumero_afiliacion(String numero_afiliacion) {
        this.numero_afiliacion = numero_afiliacion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @NonNull
    @Override
    public String toString() {
        return  nombre_completo;
    }
}