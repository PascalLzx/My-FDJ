package com.lzx.myfdj.domain.repository

import com.lzx.myfdj.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {

    fun getTeamsByLeague(leagueName: String): Flow<List<Team>>
}
