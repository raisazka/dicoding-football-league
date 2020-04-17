package com.rais.footballleague.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    @SerializedName("idEvent")
    val matchId : Int?,
    @SerializedName("idName")
    val leagueId : Int?,
    @SerializedName("strEvent")
    val name : String?,
    @SerializedName("strSport")
    val sport : String?,
    @SerializedName("strDate")
    val date: String?,
    @SerializedName("strTime")
    val time : String?,
    @SerializedName("intHomeScore")
    val scoreHome: Int?,
    @SerializedName("intAwayScore")
    val scoreAway: Int?,
    @SerializedName("strLeague")
    val leagueName : String?,
    @SerializedName("idHomeTeam")
    val homeTeamId: Int?,
    @SerializedName("idAwayTeam")
    val awayTeamId: Int?,
    @SerializedName("strHomeTeam")
    val homeTeamName: String?,
    @SerializedName("strAwayTeam")
    val awayTeamName: String?,
    @SerializedName("strHomeGoalDetails")
    val homeGoals : String?,
    @SerializedName("strAwayGoalDetails")
    val awayGoals : String?,
    @SerializedName("strHomeYellowCards")
    val homeYellow : String?,
    @SerializedName("strAwayYellowCards")
    val awayYellow : String?,
    @SerializedName("strHomeRedCards")
    val homeRed : String?,
    @SerializedName("strAwayRedCards")
    val awayRed : String?,
    @SerializedName("strHomeLineupGoalkeeper")
    val homeGoalie : String?,
    @SerializedName("strAwayLineupGoalkeeper")
    val awayGoalie : String?,
    @SerializedName("strHomeLineupDefense")
    val homeDefender : String?,
    @SerializedName("strAwayLineupDefense")
    val awayDefender : String?,
    @SerializedName("strHomeLineupMidfield")
    val homeMid : String?,
    @SerializedName("strAwayLineupMidfield")
    val awayMid : String?,
    @SerializedName("strHomeLineupForward")
    val homeForward : String?,
    @SerializedName("strAwayLineupForward")
    val awayForward : String?) : Parcelable