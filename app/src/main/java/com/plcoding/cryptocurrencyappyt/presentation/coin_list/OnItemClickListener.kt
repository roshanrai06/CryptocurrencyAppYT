package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import com.plcoding.cryptocurrencyappyt.domain.model.Coin

interface OnItemClickListener {
    fun onItemClick(item: Coin)
}