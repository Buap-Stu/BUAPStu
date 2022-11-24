package com.buap.stu.buapstu.models;

public class Boleto {
    private String Ruta,Horario,Fecha;
    private Integer Costo;

    public Boleto() {
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String ruta) {
        this.Ruta = ruta;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        this.Horario = horario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public Integer getCosto() {
        return Costo;
    }

    public void setCosto(Integer costo) {
        this.Costo = costo;
    }
}
