package com.lzx.myfdj.presentation.mapper

import com.lzx.myfdj.domain.model.League
import com.lzx.myfdj.presentation.main.mapper.LeagueUiMapper
import com.lzx.myfdj.uicomponent.model.LeagueUi
import org.junit.Before
import org.junit.Test

class LeagueUiMapperTest {

    private lateinit var tested: LeagueUiMapper

    @Before
    fun setUp() {
        tested = LeagueUiMapper()
    }

    @Test
    fun `test map with LeagueUiMapper`() {
        // given
        val league = League(
            id = "123",
            name = "Ligue 1",
        )

        // when
        val mappedLeague: LeagueUi = tested.map(league)

        // then
        assert(mappedLeague.id == league.id)
        assert(mappedLeague.name == league.name)
    }
}
