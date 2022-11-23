package com.buap.stu.buapstu.inject

import android.content.Context
import com.buap.stu.buapstu.data.local.settings.SettingsDataSource
import com.buap.stu.buapstu.data.local.settings.SettingsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsDataSource(
        @ApplicationContext context: Context
    ): SettingsDataSource = SettingsDataSourceImpl(context)



}