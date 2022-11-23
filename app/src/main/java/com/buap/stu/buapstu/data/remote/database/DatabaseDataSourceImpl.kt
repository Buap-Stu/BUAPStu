package com.buap.stu.buapstu.data.remote.database

import com.buap.stu.buapstu.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class DatabaseDataSourceImpl:DatabaseDataSource {
    private val auth=FirebaseAuth.getInstance()
    private val database=FirebaseDatabase.getInstance().reference
    private val uuidUser get() = auth.currentUser!!.uid


    companion object{
        private const val NAME_REF_USERS="Usuarios"
    }

    override suspend fun getUser(uuid:String): User {
        val nodeUser=database.child(NAME_REF_USERS).child(uuid).get().await()
        val type:String?= nodeUser.child("type").value as? String
        return when(type){
            "Alumno"->nodeUser.getValue(Alumno::class.java)!!
            "Conductor"->nodeUser.getValue(Conductor::class.java)!!
            else -> throw Exception("Error convert")
        }
    }

    override suspend fun changeStateUser(idUser: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(idUser: String, change: Map<String, Any>) {
        TODO("Not yet implemented")
    }

    override suspend fun addCredits():User {
        // * using incrementation atomic
        database.child(NAME_REF_USERS).child(uuidUser).updateChildren(
            mapOf("creditos" to ServerValue.increment(20))
        ).await()
        return getUser(uuidUser)
    }

    override suspend fun getListRouter(): List<Ruta> {
        val nodeRoute = database.child("Rutas").get().await()
        return nodeRoute.children.mapNotNull { it.getValue(Ruta::class.java) }
    }

    override suspend fun getListHours(): List<Horario> {
        val nodeRoute = database.child("Rutas").get().await()
        return nodeRoute.children.mapNotNull { it.getValue(Horario::class.java) }
    }


}