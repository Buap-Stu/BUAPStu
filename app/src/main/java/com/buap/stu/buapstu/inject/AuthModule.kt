package com.buap.stu.buapstu.inject

import com.buap.stu.buapstu.data.local.settings.SettingsDataSource
import com.buap.stu.buapstu.data.remote.auth.AuthDataSource
import com.buap.stu.buapstu.data.remote.auth.AuthDataSourceImpl
import com.buap.stu.buapstu.data.remote.database.DatabaseDataSource
import com.buap.stu.buapstu.domain.AuthRepoImpl
import com.buap.stu.buapstu.domain.database.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthDataSource(): AuthDataSource =
        AuthDataSourceImpl()

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource,
        settDataSource: SettingsDataSource,
        databaseDataSource: DatabaseDataSource
    ):AuthRepoImpl= AuthRepoImpl(authDataSource,settDataSource,databaseDataSource)
}