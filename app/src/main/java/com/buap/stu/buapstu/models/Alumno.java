package com.buap.stu.buapstu.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Alumno implements Serializable {
    public String uid, telefono, nombre_completo, correo_institucional, matricula, contrasena,type;
    public Integer creditos;
    public Boolean estado;

    public Alumno() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCorreo_institucional() {
        return correo_institucional;
    }

    public void setCorreo_institucional(String correo_institucional) {
        this.correo_institucional = correo_institucional;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
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
