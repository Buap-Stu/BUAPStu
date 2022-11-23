package com.buap.stu.buapstu.data.local.settings

import com.buap.stu.buapstu.models.User
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
     fun getUser(): Flow<User>
    suspend fun saveUser(user: User)
    suspend fun clearData()
}