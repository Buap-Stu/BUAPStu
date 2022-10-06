package com.buap.stu.buapstu.models;

public class camion {
    public String matricula;
    public Integer numero_maximo_asientos,numero_asientos_disponibles;
    public String state;
    public Boolean[][] asientos_disponibles;

    public camion() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Integer getNumero_maximo_asientos() {
        return numero_maximo_asientos;
    }

    public void setNumero_maximo_asientos(Integer numero_maximo_asientos) {
        this.numero_maximo_asientos = numero_maximo_asientos;
    }

    public Integer getNumero_asientos_disponibles() {
        return numero_asientos_disponibles;
    }

    public void setNumero_asientos_disponibles(Integer numero_asientos_disponibles) {
        this.numero_asientos_disponibles = numero_asientos_disponibles;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean[][] getAsientos_disponibles() {
        return asientos_disponibles;
    }

    public void setAsientos_disponibles(Boolean[][] asientos_disponibles) {
        this.asientos_disponibles = asientos_disponibles;
    }
}
