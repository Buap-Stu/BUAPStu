package com.buap.stu.buapstu.domain.database

import com.buap.stu.buapstu.data.local.settings.SettingsDataSource
import com.buap.stu.buapstu.data.remote.database.DatabaseDataSource
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

    override suspend fun getListHours(): List<Horario> =
        databaseDataSource.getListHours()
}