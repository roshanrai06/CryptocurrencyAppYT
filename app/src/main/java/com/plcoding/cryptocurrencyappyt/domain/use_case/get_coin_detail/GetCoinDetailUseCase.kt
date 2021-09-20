package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin_detail

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> =
        coinRepository.getCoinId(coinId)

}