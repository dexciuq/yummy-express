package com.dexciuq.yummy_express.di

import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.data.data_source.local.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module

@InstallIn(SingletonComponent::class)
interface DataSourceModule {

//    @Binds
//    fun provideRemoteDataSource(): DataSource.Remote

    @Binds
    fun provideLocalDataSource(localDataSource: LocalDataSource): DataSource.Local
}