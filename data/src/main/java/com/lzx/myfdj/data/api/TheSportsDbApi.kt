package com.lzx.myfdj.data.api

import com.lzx.myfdj.data.api.model.LeaguesDataDto
import com.lzx.myfdj.data.api.model.TeamsDataDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportsDbApi {

    @GET("all_leagues.php")
    suspend fun getAllLeagues(): Response<LeaguesDataDto>

    @GET("search_all_teams.php")
    suspend fun getTeamsByLeague(
        @Query("l") priceZoneId: String,
    ): Response<TeamsDataDto>
}
