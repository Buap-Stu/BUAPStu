package com.buap.stu.buapstu.domain

import com.buap.stu.buapstu.data.local.settings.SettingsDataSource
import com.buap.stu.buapstu.data.remote.auth.AuthDataSource
import com.buap.stu.buapstu.data.remote.database.DatabaseDataSource
import com.buap.stu.buapstu.models.User
import kotlinx.coroutines.flow.Flow

class AuthRepoImpl(
    private val authDataSource: AuthDataSource,
    private val settingsDataSource: SettingsDataSource,
    private val databaseDataSource: DatabaseDataSource
):AuthRepository {

    override val user: Flow<User> = settingsDataSource.getUser()

    override suspend fun signInWithEmailAndPassword(email: String, password: String):String{
        val uuidUser=authDataSource.signInWithEmailAndPassword(email, password)
        val user=databaseDataSource.getUser(uuidUser)
        settingsDataSource.saveUser(user)
        return user.type
    }

    override suspend fun logOut(){
        authDataSource.logOut()
        settingsDataSource.clearData()
    }

    override suspend fun signUpUser(newUser: User) {
        newUser.uid=authDataSource.signUpUser(newUser)
        databaseDataSource.addUser(newUser)
        settingsDataSource.saveUser(newUser)
    }
}