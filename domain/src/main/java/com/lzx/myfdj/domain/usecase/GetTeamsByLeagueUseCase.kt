package com.lzx.myfdj.domain.usecase

import com.lzx.myfdj.domain.model.Team
import com.lzx.myfdj.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamsByLeagueUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {

    operator fun invoke(leagueName: String): Flow<List<Team>> {
        return teamRepository.getTeamsByLeague(leagueName)
    }
}
