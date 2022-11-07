package com.buap.stu.buapstu.models;

public class Ruta {
    public  String punto_inicial,punto_final;
    public String[] paradas_autorizadas,horaio_entras,horario_salidas;

    public Ruta() {
    }

    public String getPunto_inicial() {
        return punto_inicial;
    }

    public void setPunto_inicial(String punto_inicial) {
        this.punto_inicial = punto_inicial;
    }

    public String getPunto_final() {
        return punto_final;
    }

    public void setPunto_final(String punto_final) {
        this.punto_final = punto_final;
    }

    public String[] getParadas_autorizadas() {
        return paradas_autorizadas;
    }

    public void setParadas_autorizadas(String[] paradas_autorizadas) {
        this.paradas_autorizadas = paradas_autorizadas;
    }

    public String[] getHoraio_entras() {
        return horaio_entras;
    }

    public void setHoraio_entras(String[] horaio_entras) {
        this.horaio_entras = horaio_entras;
    }

    public String[] getHorario_salidas() {
        return horario_salidas;
    }

    public void setHorario_salidas(String[] horario_salidas) {
        this.horario_salidas = horario_salidas;
    }
}
