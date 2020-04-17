package com.rais.footballleague.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.rais.footballleague.model.FavoriteMatch
import org.jetbrains.anko.db.*

class MatchDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 2) {

    companion object {
        private var instance : MatchDatabaseOpenHelper? = null

        fun getInstance(context: Context) : MatchDatabaseOpenHelper {
            if (instance == null) {
                instance = MatchDatabaseOpenHelper(context)
            }
            return instance as MatchDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FavoriteMatch.TABLE_FAVORITE, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.MATCH_ID to INTEGER + UNIQUE,
            FavoriteMatch.MATCH_NAME to TEXT,
            FavoriteMatch.HOME_NAME to TEXT,
            FavoriteMatch.AWAY_NAME to TEXT,
            FavoriteMatch.HOME_SCORE to INTEGER,
            FavoriteMatch.AWAY_SCORE to INTEGER,
            FavoriteMatch.MATCH_DATE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
    }

}