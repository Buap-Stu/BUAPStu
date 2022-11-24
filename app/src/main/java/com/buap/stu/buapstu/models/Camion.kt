package com.buap.stu.buapstu.models

class Camion(
    val matricula: String="",
    val numero_maximo_asientos: Int=0,
    val numero_asientos_disponibles: Int=0,
    val state: String="",
    val asientos_disponibles:List<List<Boolean>> = emptyList()
)