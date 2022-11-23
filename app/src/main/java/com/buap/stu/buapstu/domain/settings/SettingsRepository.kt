package com.buap.stu.buapstu.domain.settings

import com.buap.stu.buapstu.models.User
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getUser(): Flow<User>
    suspend fun saveUser(user: User)
    suspend fun clearData()
}