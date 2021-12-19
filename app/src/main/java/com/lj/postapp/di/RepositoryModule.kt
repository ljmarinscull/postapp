package com.lj.postapp.di

import com.lj.postapp.data.datasource.IMainDataSource
import com.lj.postapp.data.datasource.MainDataSource
import com.lj.postapp.data.repo.IMainRepository
import com.lj.postapp.data.repo.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideIMainRepository(
        mainRepository: MainRepository
    ): IMainRepository

    @Singleton
    @Binds
    abstract fun provideIMainDataSource(
        mainDataSource: MainDataSource
    ): IMainDataSource
}