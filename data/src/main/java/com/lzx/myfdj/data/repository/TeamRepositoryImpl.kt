package com.lzx.myfdj.data.repository

import com.lzx.myfdj.common.di.DefaultDispatcher
import com.lzx.myfdj.data.datasource.team.TeamRemoteDataSource
import com.lzx.myfdj.domain.model.Team
import com.lzx.myfdj.domain.repository.TeamRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamRemoteDataSource: TeamRemoteDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : TeamRepository {

    // Display every other item
    override fun getTeamsByLeague(leagueName: String): Flow<List<Team>> = flow {
        emit(teamRemoteDataSource.getTeamsByLeague(leagueName).filterIndexed { index, _ -> index % 2 == 0 })
    }.flowOn(defaultDispatcher)
}
