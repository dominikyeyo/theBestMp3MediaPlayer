package com.diegonunez.thebestmp3mediaplayer.di

import com.diegonunez.thebestmp3mediaplayer.data.repositoy.Mp3PlayerRepositoryImpl
import com.diegonunez.thebestmp3mediaplayer.domain.repository.MP3PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindMp3PlayerRepository(
        repository: Mp3PlayerRepositoryImpl
    ): MP3PlayerRepository


}