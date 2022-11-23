package com.buap.stu.buapstu.models

import java.io.Serializable

data class Alumno(
    override val uid: String="",
    override val telefono: String="",
    override val estado: Boolean=false,
    override var contrasena: String="",
    override val nombre_completo: String="",
    override val correo: String="",
    override val type: String="",
    var matricula: String="",
    var creditos:Int=0,
) : Serializable,User()