package com.koai.kingofenglish.ui.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.koai.kingofenglish.R
import com.koai.kingofenglish.network.DataTopRanks

class LeaderboardAdapter(private var dataList: List<DataTopRanks.Data?>?) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leader_board, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList!![position]
        if (data != null) {
            holder.bind(data)
        }
    }
    fun formatLargeNumber(number: Int): String {
        return when {
            number < 1000 -> number.toString()
            number < 10000 -> {
                val remainder = number % 1000
                val decimalPart = remainder / 100
                val formattedNumber = number / 1000
                "$formattedNumber,$decimalPart" + "k"
            }
            else -> {
                val formattedNumber = number / 1000
                "$formattedNumber" + "k"
            }
        }
    }
    override fun getItemCount(): Int {
        return dataList!!.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val levelTextView: TextView = itemView.findViewById(R.id.txt_level)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.img_avt)
        private val usernameTextView: TextView = itemView.findViewById(R.id.txt_username)
        private val scoreTextView: TextView = itemView.findViewById(R.id.txt_score)

        fun bind(data: DataTopRanks.Data) {
            levelTextView.text = data.currentLevel.toString()
            Glide.with(itemView.context).load(data.avatar).into(avatarImageView)
            usernameTextView.text = data.name
            val largeNumber = data.points
            val formattedNumber = formatLargeNumber(largeNumber!!)
            scoreTextView.text = formattedNumber
        }
    }
}
