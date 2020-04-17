package com.rais.footballleague.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.rais.footballleague.model.FavoriteTeam
import org.jetbrains.anko.db.*

class TeamDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoritePlayer.db", null, 2) {

    companion object {
        private var instance : TeamDatabaseOpenHelper? = null

        fun getInstance(context: Context) : TeamDatabaseOpenHelper {
            if (instance == null) {
                instance = TeamDatabaseOpenHelper(context)
            }
            return instance as TeamDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FavoriteTeam.TABLE_FAVORITE, true,
                        FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                                FavoriteTeam.TEAM_ID to INTEGER + UNIQUE,
                                FavoriteTeam.TEAM_NAME to TEXT,
                                FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteTeam.TABLE_FAVORITE, true)
    }


}