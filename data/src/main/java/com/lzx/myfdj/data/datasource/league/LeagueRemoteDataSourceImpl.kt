package com.lzx.myfdj.data.datasource.league

import com.lzx.myfdj.common.di.IoDispatcher
import com.lzx.myfdj.data.api.TheSportsDbApi
import com.lzx.myfdj.data.api.model.LeaguesDataDto
import com.lzx.myfdj.data.datasource.BaseRemoteDataSource
import com.lzx.myfdj.data.mapper.LeagueMapper
import com.lzx.myfdj.domain.model.League
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LeagueRemoteDataSourceImpl @Inject constructor(
    private val theSportsDbApi: TheSportsDbApi,
    private val leagueMapper: LeagueMapper,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : LeagueRemoteDataSource, BaseRemoteDataSource<LeaguesDataDto, List<League>>(ioDispatcher) {

    override suspend fun getAllLeagues(): List<League> {
        return safeApiCall(
            apiCall = { theSportsDbApi.getAllLeagues() },
            onSuccess = { mapDtoToDomain(it) },
        )
    }

    private fun mapDtoToDomain(data: LeaguesDataDto): List<League> {
        return data.leagues.map { leagueMapper.map(it) }
    }
}
