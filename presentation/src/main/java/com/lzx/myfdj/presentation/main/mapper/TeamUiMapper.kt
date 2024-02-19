package com.lzx.myfdj.presentation.main.mapper

import com.lzx.myfdj.domain.model.Team
import com.lzx.myfdj.uicomponent.model.TeamUi
import javax.inject.Inject

class TeamUiMapper @Inject constructor() {

    fun map(team: Team) = TeamUi(
        id = team.id,
        name = team.name,
        badgeUrl = team.badgeUrl,
    )
}
