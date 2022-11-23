package com.buap.stu.buapstu.inject

import com.buap.stu.buapstu.domain.AuthRepoImpl
import com.buap.stu.buapstu.domain.AuthRepository
import com.buap.stu.buapstu.domain.database.DatabaseRepoImpl
import com.buap.stu.buapstu.domain.database.DatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideAuthRepository(
        authRepository: AuthRepoImpl
    ):AuthRepository

    @Singleton
    @Binds
    abstract fun provideDatabaseRepository(
        databaseRepoImpl: DatabaseRepoImpl
    ):DatabaseRepository
}
