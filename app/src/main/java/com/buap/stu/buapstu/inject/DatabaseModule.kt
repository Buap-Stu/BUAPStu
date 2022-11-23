package com.buap.stu.buapstu.inject

import com.buap.stu.buapstu.data.local.settings.SettingsDataSource
import com.buap.stu.buapstu.data.remote.database.DatabaseDataSource
import com.buap.stu.buapstu.data.remote.database.DatabaseDataSourceImpl
import com.buap.stu.buapstu.domain.database.DatabaseRepoImpl
import com.buap.stu.buapstu.domain.database.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabaseDataSource(): DatabaseDataSource =
        DatabaseDataSourceImpl()

    @Provides
    @Singleton
    fun provideDatabaseProvide(
        databaseDataSource: DatabaseDataSource,
        settingsDataSource: SettingsDataSource
    ): DatabaseRepoImpl = DatabaseRepoImpl(databaseDataSource,settingsDataSource)

}