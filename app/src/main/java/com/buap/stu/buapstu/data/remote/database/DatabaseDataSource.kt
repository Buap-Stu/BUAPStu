package com.buap.stu.buapstu.data.remote.database

import com.buap.stu.buapstu.models.Boleto
import com.buap.stu.buapstu.models.Horario
import com.buap.stu.buapstu.models.Ruta
import com.buap.stu.buapstu.models.User

interface DatabaseDataSource {
    suspend fun getUser(uuid:String?=null): User
    suspend fun changeStateUser(idUser: String)
    suspend fun updateUser(idUser:String,change:Map<String,Any>)
    suspend fun addCredits():User
    suspend fun getListRouter(): List<Ruta>
    suspend fun getListHours(nameRoute: String): List<Horario>
    suspend fun addNewBoleto(boleto: Boleto):User
    suspend fun transferCredits(matricula: String, creditos: Int): User
}