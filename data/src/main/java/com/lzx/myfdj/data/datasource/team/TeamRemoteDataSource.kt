package com.lzx.myfdj.data.datasource.team

import com.lzx.myfdj.domain.model.Team

interface TeamRemoteDataSource {

    suspend fun getTeamsByLeague(leagueName: String): List<Team>
}
