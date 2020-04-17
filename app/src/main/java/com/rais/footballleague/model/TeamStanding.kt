package com.rais.footballleague.model

import com.google.gson.annotations.SerializedName

data class TeamStanding(
    @SerializedName("name")
    val teamName : String?,
    val played : Int?,
    val goalsdifference : Int?,
    val win : Int?,
    val draw : Int?,
    val loss : Int?,
    val total : Int?
)