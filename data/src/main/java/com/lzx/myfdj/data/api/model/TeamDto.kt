package com.lzx.myfdj.data.api.model

import com.squareup.moshi.Json

data class TeamsDataDto(
    val teams: List<TeamDto>,
)

data class TeamDto(
    @field:Json(name = "idTeam")
    val id: String,
    @field:Json(name = "strTeam")
    val name: String,
    @field:Json(name = "strTeamBadge")
    val badgeUrl: String,
)
