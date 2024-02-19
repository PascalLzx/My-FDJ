package com.lzx.myfdj.data.repository

import com.lzx.myfdj.data.datasource.league.LeagueRemoteDataSource
import com.lzx.myfdj.domain.model.League
import com.lzx.myfdj.domain.repository.LeagueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leagueRemoteDataSource: LeagueRemoteDataSource,
) : LeagueRepository {

    override fun getAllLeagues(): Flow<List<League>> = flow {
        emit(leagueRemoteDataSource.getAllLeagues())
    }
}
