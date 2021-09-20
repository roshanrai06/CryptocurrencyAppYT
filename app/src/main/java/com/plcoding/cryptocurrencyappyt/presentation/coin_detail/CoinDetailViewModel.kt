package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin_detail.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    val coinDetailUseCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _state = mutableStateOf(CoinDetailState())
    val state = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoinDetail(coinId)
        }

    }

    private fun getCoinDetail(coinId: String) {
        viewModelScope.launch {
            coinDetailUseCase(coinId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CoinDetailState(coinDetail = result.data)

                    }
                    is Resource.Error -> {
                        _state.value =
                            CoinDetailState(error = result.message ?: "An Unexpected Error")

                    }
                    is Resource.Loading -> {
                        _state.value = CoinDetailState(isLoading = true)
                    }
                }
            }
        }

    }
}