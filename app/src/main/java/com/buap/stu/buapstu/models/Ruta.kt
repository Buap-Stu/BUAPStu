package com.buap.stu.buapstu.models

data class Ruta(
    val punto_inicial: String="",
    val punto_final: String="",
    val paradas_autorizadas: List<String> = emptyList(),
    val horaio_entras: List<String> = emptyList(),
    val horario_salidas: List<String> = emptyList()
)