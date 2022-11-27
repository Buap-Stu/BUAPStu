package com.buap.stu.buapstu.domain.database

import com.buap.stu.buapstu.data.local.settings.SettingsDataSource
import com.buap.stu.buapstu.data.remote.database.DatabaseDataSource
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.models.Boleto
import com.buap.stu.buapstu.models.Horario
import com.buap.stu.buapstu.models.User

class DatabaseRepoImpl(
    private val databaseDataSource: DatabaseDataSource,
    private val settingsDataSource: SettingsDataSource
) : DatabaseRepository {
    override suspend fun getUser(uuid: String): User =
        databaseDataSource.getUser(uuid)

    override suspend fun changeStateUser(idUser: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(idUser: String, change: Map<String, Any>) {
        TODO("Not yet implemented")
    }

    override suspend fun addCredits(){
       val newUser= databaseDataSource.addCredits()
        settingsDataSource.saveUser(newUser)
    }

    override suspend fun getListRouter()=
        databaseDataSource.getListRouter()

    override suspend fun getListHours(nameRoute: String): List<Horario> =
        databaseDataSource.getListHours(nameRoute)

    override suspend fun addNewBoleto(boleto: Boleto){
        try {
            val newUser=databaseDataSource.addNewBoleto(boleto)
            settingsDataSource.saveUser(newUser)
        }catch (e:Exception){
            val currentUser=databaseDataSource.getUser()
            settingsDataSource.saveUser(currentUser)
            throw e
        }

    }

    override suspend fun transferCredits(matricula: String, creditos: Int) {
        try {
            val newUser=databaseDataSource.transferCredits(matricula, creditos)
            settingsDataSource.saveUser(newUser)
        }catch (e:Exception){
            val currentUser=databaseDataSource.getUser()
            settingsDataSource.saveUser(currentUser)
            throw e
        }
    }

    override suspend fun searchStudent(matricula: String): Alumno =
        databaseDataSource.searchStudent(matricula)


}