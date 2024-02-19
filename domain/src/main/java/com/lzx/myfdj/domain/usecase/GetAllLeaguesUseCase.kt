package com.lzx.myfdj.domain.usecase

import com.lzx.myfdj.domain.model.League
import com.lzx.myfdj.domain.repository.LeagueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLeaguesUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository,
) {

    operator fun invoke(): Flow<List<League>> {
        return leagueRepository.getAllLeagues()
    }
}
