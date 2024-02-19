package com.lzx.myfdj.data.datasource.league

import com.lzx.myfdj.domain.model.League

interface LeagueRemoteDataSource {

    suspend fun getAllLeagues(): List<League>
}
