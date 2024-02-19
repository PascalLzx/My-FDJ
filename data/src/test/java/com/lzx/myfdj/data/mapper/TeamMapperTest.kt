package com.lzx.myfdj.data.mapper

import com.lzx.myfdj.data.api.model.TeamDto
import com.lzx.myfdj.domain.model.Team
import org.junit.Before
import org.junit.Test

class TeamMapperTest {

    private lateinit var tested: TeamMapper

    @Before
    fun setUp() {
        tested = TeamMapper()
    }

    @Test
    fun `test map with TeamMapper`() {
        // given
        val teamDto = TeamDto(
            id = "123",
            name = "Psg",
            badgeUrl = "http://psg.com/psg",
        )

        // when
        val mappedTeam: Team = tested.map(teamDto)

        // then
        assert(mappedTeam.id == teamDto.id)
        assert(mappedTeam.name == teamDto.name)
        assert(mappedTeam.badgeUrl == teamDto.badgeUrl)
    }
}
