package com.buap.stu.buapstu.models

data class Boleto(
    var ruta: String="",
    var horario: String="",
    var fecha: String="",
    val costo: Int=4
)