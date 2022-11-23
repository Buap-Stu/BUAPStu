package com.buap.stu.buapstu.domain.settings

import com.buap.stu.buapstu.data.local.settings.SettingsDataSource
import com.buap.stu.buapstu.models.User
import kotlinx.coroutines.flow.Flow

class SettingsRepoImpl(
    private val settingsDataSource: SettingsDataSource
):SettingsRepository {
    override suspend fun getUser(): Flow<User> =
        settingsDataSource.getUser()

    override suspend fun saveUser(user: User) =
        settingsDataSource.saveUser(user)

    override suspend fun clearData() =
        settingsDataSource.clearData()
}