package com.rais.footballleague.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idPlayer")
    val playerId : Int?,

    @SerializedName("strTeam")
    val playerTeam: String?,

    @SerializedName("strNationality")
    val playerNationality : String?,

    @SerializedName("strPlayer")
    val playerName : String?,

    @SerializedName("dateBorn")
    val playerBirthday : String?,

    @SerializedName("strNumber")
    val playerNumber : String?,

    @SerializedName("strWage")
    val playerWage : String?,

    @SerializedName("strPosition")
    val playerPosition : String?,

    @SerializedName("strDescriptionEN")
    val playerDescription : String?,

    @SerializedName("strCutout")
    val playerImage : String?
)