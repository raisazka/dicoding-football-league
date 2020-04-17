package com.rais.footballleague.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
    @SerializedName("idLeague")
     val leagueId : Int,
    @SerializedName("strLeague")
    val name: String?,
    @SerializedName("strDescriptionEN")
     val detail: String?,
    @SerializedName("strCountry")
    val leagueBase : String?,
    val logo: Int) : Parcelable