package com.buap.stu.buapstu.models;

import androidx.annotation.NonNull;

public class Horario {
    public String horaSalida,horaAproxLlegada,hid;

    public Horario() {
    }

    @NonNull
    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(@NonNull String horaSalida) {
        this.horaSalida = horaSalida;
    }

    @NonNull
    public String getHoraAproxLlegada() {
        return horaAproxLlegada;
    }

    public void setHoraAproxLlegada(@NonNull String horaAproxLlegada) {
        this.horaAproxLlegada = horaAproxLlegada;
    }

    @NonNull
    public String getHid() {
        return hid;
    }

    public void setHid(@NonNull String hid) {
        this.hid = hid;
    }
}
