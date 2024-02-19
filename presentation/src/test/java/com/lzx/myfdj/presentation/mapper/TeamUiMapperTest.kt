package com.lzx.myfdj.presentation.mapper

import com.lzx.myfdj.domain.model.Team
import com.lzx.myfdj.presentation.main.mapper.TeamUiMapper
import com.lzx.myfdj.uicomponent.model.TeamUi
import org.junit.Before
import org.junit.Test

class TeamUiMapperTest {
    private lateinit var tested: TeamUiMapper

    @Before
    fun setUp() {
        tested = TeamUiMapper()
    }

    @Test
    fun `test map with TeamUiMapper`() {
        // given
        val team = Team(
            id = "123",
            name = "Psg",
            badgeUrl = "http://psg.com/psg",
        )

        // when
        val mappedTeam: TeamUi = tested.map(team)

        // then
        assert(mappedTeam.id == team.id)
        assert(mappedTeam.name == team.name)
        assert(mappedTeam.badgeUrl == team.badgeUrl)
    }
}
