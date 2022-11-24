package com.buap.stu.buapstu.models

data class Boleto(
    var ruta: String="",
    var horario: String="",
    var fecha: String="",
    var costo: Int=0
)