package com.lzx.myfdj.data.mapper

import com.lzx.myfdj.data.api.model.LeagueDto
import com.lzx.myfdj.domain.model.League
import javax.inject.Inject

class LeagueMapper @Inject internal constructor() {

    fun map(leagueDto: LeagueDto): League {
        return League(
            id = leagueDto.id,
            name = leagueDto.name,
        )
    }
}
