package com.rais.footballleague.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    val teamId: Int,

    @SerializedName("strTeam")
    val teamName: String,

    @SerializedName("strTeamBadge")
    val teamBadge: String,

    @SerializedName("strDescriptionEN")
    val teamDescription : String?,

    @SerializedName("strStadium")
    val teamStadium : String,

    @SerializedName("strInstagram")
    val teamInstagram : String,

    @SerializedName("strYoutube")
    val teamYoutube : String,

    @SerializedName("strTwitter")
    val teamTwitter : String,

    @SerializedName("strFacebook")
    val teamFacebook : String,

    @SerializedName("strSport")
    val sport : String)
