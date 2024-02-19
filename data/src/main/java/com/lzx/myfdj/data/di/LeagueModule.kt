package com.lzx.myfdj.data.di

import com.lzx.myfdj.data.datasource.league.LeagueRemoteDataSource
import com.lzx.myfdj.data.datasource.league.LeagueRemoteDataSourceImpl
import com.lzx.myfdj.data.repository.LeagueRepositoryImpl
import com.lzx.myfdj.domain.repository.LeagueRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LeagueModule {

    @Binds
    @Singleton
    abstract fun bindLeagueRepository(leagueRepositoryImpl: LeagueRepositoryImpl): LeagueRepository

    @Binds
    @Singleton
    abstract fun bindLeagueRemoteDataSource(leagueRemoteDataSourceImpl: LeagueRemoteDataSourceImpl): LeagueRemoteDataSource
}
