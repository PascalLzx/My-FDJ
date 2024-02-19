package com.lzx.myfdj.data.api.model

import com.squareup.moshi.Json

data class LeaguesDataDto(
    val leagues: List<LeagueDto>,
)

data class LeagueDto(
    @field:Json(name = "idLeague")
    val id: String,
    @field:Json(name = "strLeague")
    val name: String,
)
