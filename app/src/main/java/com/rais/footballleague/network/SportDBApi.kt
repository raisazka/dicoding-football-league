package com.rais.footballleague.network

import com.rais.footballleague.BuildConfig

object SportDBApi {

    private val TSDB_API_KEY = 4013017

    fun getLeagueDetail(leagueId : Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/lookupleague.php?id=${leagueId}"
    }

    fun getPastMatch(leagueId: Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/eventspastleague.php?id=${leagueId}"
    }

    fun getFutureMatch(leagueId: Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/eventsnextleague.php?id=${leagueId}"
    }

    fun getTeamDetail(teamId : Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/lookupteam.php?id=${teamId}"
    }

    fun getEventDetail(eventId : Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/lookupevent.php?id=${eventId}"
    }

    fun searchEvent(eventName : String?) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/searchevents.php?e=${eventName}"
    }

    fun getTeams(leagueId: Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/lookup_all_teams.php?id=${leagueId}"
    }

    fun getTeamPlayers(teamId: Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/lookup_all_players.php?id=${teamId}"
    }

    fun getPlayerDetail(playerId: Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/lookupplayer.php?id=${playerId}"
    }

    fun getTeamStanding(leagueId: Int) : String {
        return BuildConfig.BASE_URL + "api/v1/json/1/lookuptable.php?l=${leagueId}"
    }

    fun searchTeams(teamName : String?) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${TSDB_API_KEY}/searchteams.php?t=${teamName}"
    }

}