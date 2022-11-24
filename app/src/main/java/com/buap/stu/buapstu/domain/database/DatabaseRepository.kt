package com.buap.stu.buapstu.domain.database

import com.buap.stu.buapstu.models.Horario
import com.buap.stu.buapstu.models.Ruta
import com.buap.stu.buapstu.models.User

interface DatabaseRepository {
    suspend fun getUser(uuid:String): User
    suspend fun changeStateUser(idUser: String)
    suspend fun updateUser(idUser:String,change:Map<String,Any>)
    suspend fun addCredits()
    suspend fun getListRouter():List<Ruta>
    suspend fun getListHours(nameRoute: String): List<Horario>
}