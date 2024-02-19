package com.lzx.myfdj.data.mapper

import com.lzx.myfdj.data.api.model.LeagueDto
import com.lzx.myfdj.domain.model.League
import org.junit.Before
import org.junit.Test

class LeagueMapperTest {

    private lateinit var tested: LeagueMapper

    @Before
    fun setUp() {
        tested = LeagueMapper()
    }

    @Test
    fun `test map with LeagueMapper`() {
        // given
        val leagueDto = LeagueDto(
            id = "123",
            name = "Ligue 1",
        )

        // when
        val mappedLeague: League = tested.map(leagueDto)

        // then
        assert(mappedLeague.id == leagueDto.id)
        assert(mappedLeague.name == leagueDto.name)
    }
}
