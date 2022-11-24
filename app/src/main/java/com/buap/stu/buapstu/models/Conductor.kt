package com.buap.stu.buapstu.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class Conductor(
    override var uid: String="",
    override val telefono: String="",
    override val estado: Boolean=true,
    override val contrasena: String="",
    override var nombre_completo: String="",
    override val correo: String="",
    override val type: String="",
    val numero_afiliacion: String=""
) : Serializable, User()