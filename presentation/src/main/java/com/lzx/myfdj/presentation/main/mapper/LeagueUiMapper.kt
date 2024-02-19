package com.lzx.myfdj.presentation.main.mapper

import com.lzx.myfdj.domain.model.League
import com.lzx.myfdj.uicomponent.model.LeagueUi
import javax.inject.Inject

class LeagueUiMapper @Inject constructor() {

    fun map(league: League) = LeagueUi(
        id = league.id,
        name = league.name,
    )
}
