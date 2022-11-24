package com.buap.stu.buapstu.models

abstract class User {
    abstract var uid:String
    abstract val telefono:String
    abstract val contrasena: String
    abstract val estado:Boolean
    abstract val nombre_completo:String
    abstract val type:String
    abstract val correo:String
}