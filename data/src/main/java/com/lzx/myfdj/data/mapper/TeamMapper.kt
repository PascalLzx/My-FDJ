package com.lzx.myfdj.data.mapper

import com.lzx.myfdj.data.api.model.TeamDto
import com.lzx.myfdj.domain.model.Team
import javax.inject.Inject

class TeamMapper @Inject internal constructor() {

    fun map(teamDto: TeamDto): Team {
        return Team(
            id = teamDto.id,
            name = teamDto.name,
            badgeUrl = teamDto.badgeUrl,
        )
    }
}
