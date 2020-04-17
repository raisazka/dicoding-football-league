package com.rais.footballleague.model

data class FavoriteMatch(val id : Long?,
                         val matchId : Int?,
                         val matchName : String,
                         val homeName : String?,
                         val awayName : String?,
                         val homeScore : Int?,
                         val awayScore : Int?,
                         val matchDate : String?) {

    companion object {
        const val TABLE_FAVORITE : String = "TABLE_FAVORITE"
        const val ID : String = "ID_"
        const val MATCH_NAME = "MATCH_NAME"
        const val MATCH_ID : String = "MATCH_ID"
        const val HOME_NAME : String = "HOME_NAME"
        const val AWAY_NAME : String = "AWAY_NAME"
        const val HOME_SCORE : String = "HOME_SCORE"
        const val AWAY_SCORE : String = "AWAY_SCORE"
        const val MATCH_DATE : String = "MATCH_DATE"
    }

}