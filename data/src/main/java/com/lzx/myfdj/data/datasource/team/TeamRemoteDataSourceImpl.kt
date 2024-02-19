package com.lzx.myfdj.data.datasource.team

import com.lzx.myfdj.common.di.IoDispatcher
import com.lzx.myfdj.data.api.TheSportsDbApi
import com.lzx.myfdj.data.api.model.TeamsDataDto
import com.lzx.myfdj.data.datasource.BaseRemoteDataSource
import com.lzx.myfdj.data.mapper.TeamMapper
import com.lzx.myfdj.domain.model.Team
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TeamRemoteDataSourceImpl @Inject constructor(
    private val theSportsDbApi: TheSportsDbApi,
    private val teamMapper: TeamMapper,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : TeamRemoteDataSource, BaseRemoteDataSource<TeamsDataDto, List<Team>>(ioDispatcher) {

    override suspend fun getTeamsByLeague(leagueName: String): List<Team> {
        return safeApiCall(
            apiCall = { theSportsDbApi.getTeamsByLeague(leagueName) },
            onSuccess = { mapDtoToDomain(it) },
        )
    }

    private fun mapDtoToDomain(data: TeamsDataDto): List<Team> {
        return data.teams.map { teamMapper.map(it) }
    }
}
