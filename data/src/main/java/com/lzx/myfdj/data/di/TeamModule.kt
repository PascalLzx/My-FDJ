package com.lzx.myfdj.data.di

import com.lzx.myfdj.data.datasource.team.TeamRemoteDataSource
import com.lzx.myfdj.data.datasource.team.TeamRemoteDataSourceImpl
import com.lzx.myfdj.data.repository.TeamRepositoryImpl
import com.lzx.myfdj.domain.repository.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class TeamModule {

    @Binds
    @Singleton
    abstract fun bindTeamRepository(teamRepositoryImpl: TeamRepositoryImpl): TeamRepository

    @Binds
    @Singleton
    abstract fun bindTeamRemoteDataSource(teamRemoteDataSourceImpl: TeamRemoteDataSourceImpl): TeamRemoteDataSource
}
