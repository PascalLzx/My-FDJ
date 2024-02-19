package com.lzx.myfdj.domain.repository

import com.lzx.myfdj.domain.model.League
import kotlinx.coroutines.flow.Flow

interface LeagueRepository {

    fun getAllLeagues(): Flow<List<League>>
}
