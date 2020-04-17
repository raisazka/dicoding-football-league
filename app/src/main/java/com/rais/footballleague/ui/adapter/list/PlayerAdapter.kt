package com.rais.footballleague.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rais.footballleague.R
import com.rais.footballleague.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_players.view.*

class PlayerAdapter(private val context : Context, private val listPlayer: List<Player>,
                    private val listener: (Player) -> Unit) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>()  {

    class PlayerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val playerImage = view.image_player
        private val playerName = view.name_player
        private val playerPostion = view.position_player

        fun bind(player: Player, listener : (Player) -> Unit) {
            Picasso.get().load(player.playerImage).into(playerImage)
            playerName.text = player.playerName
            playerPostion.text = player.playerPosition

            itemView.setOnClickListener {
                listener(player)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_players, parent, false)
        )
    }

    override fun getItemCount(): Int = listPlayer.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(listPlayer[position], listener)
    }

}