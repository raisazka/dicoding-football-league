package com.rais.footballleague.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.Player
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.PlayerDetailPresenter
import com.rais.footballleague.presenter.team.PlayerDetailView
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        val apiRepository = ApiRepository()
        val gson = Gson()
        val playerId = intent.getIntExtra("playerId", 0)
        val presenter = PlayerDetailPresenter(this, gson, apiRepository)
        Log.d("PlayerDetaiActivity", "PlayerId : ${playerId}")
        presenter.getPlayerDetails(playerId)
    }

    @SuppressLint("SetTextI18n")
    override fun getPlayerDetail(player: List<Player>) {
        player.map {
            Picasso.get().load(it.playerImage).into(player_image)
            player_name.text = it.playerName
            player_positon.text = it.playerPosition
            team_name.text = it.playerTeam
            content_number.text = it.playerNumber
            content_birth.text = it.playerBirthday
            content_nat.text = it.playerNationality
            if(!it.playerWage.equals("")) {
                content_wage.text = it.playerWage
            } else {
                content_wage.text = "Unknown"
            }
            content_desc.text = it.playerDescription
        }
    }

    override fun showLoading() {
        layout_player_detail.invisible()
        prg_layout_plyr.visible()
        prg_number.visible()
        prg_birth.visible()
        prg_wage.visible()
        prg_desc_plyr.visible()
        prg_nat.visible()
    }

    override fun hideLoading() {
        layout_player_detail.visible()
        prg_layout_plyr.invisible()
        prg_number.invisible()
        prg_birth.invisible()
        prg_wage.invisible()
        prg_desc_plyr.invisible()
        prg_nat.invisible()
    }

}
