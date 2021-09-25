package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plcoding.cryptocurrencyappyt.R
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.databinding.FragmentCoinListItemsBinding
import com.plcoding.cryptocurrencyappyt.domain.model.Coin


class CoinsItemAdapter :
    ListAdapter<Coin, RecyclerView.ViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            FragmentCoinListItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val coin = getItem(position)
        (holder as CoinViewHolder)
        holder.idView.text = coin.name
        holder.contentView.text = coin.symbol
        holder.itemView.setOnClickListener {
            val args = Bundle().apply {
                putString(Constants.PARAM_COIN_ID, coin.id)
            }
            holder.itemView.findNavController()
                .navigate(R.id.action_coinListItemFragment_to_coinDetailsFragment, args)
        }
    }


}

class CoinViewHolder(binding: FragmentCoinListItemsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val idView: TextView = binding.itemNumber
    val contentView: TextView = binding.content
}

private class CoinDiffCallback : DiffUtil.ItemCallback<Coin>() {

    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }
}