package com.plcoding.cryptocurrencyappyt.data.repository

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.CoinPaprikaAPI
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinPaprikaAPI) : CoinRepository {
    override suspend fun getCoins(): Flow<Resource<List<Coin>>> {
        return flow {
            try {
                emit(Resource.Loading<List<Coin>>())
                val coins = api.getCoins().map { it.toCoin() }
                emit(Resource.Success(coins))

            } catch (e: HttpException) {
                emit(
                    Resource.Error<List<Coin>>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )

            } catch (e: IOException) {
                emit(Resource.Error<List<Coin>>("Could not reach the server"))
            }
        }
    }

    override suspend fun getCoinId(coinId: String): Flow<Resource<CoinDetail>> {
        return flow {
            try {
                emit(Resource.Loading<CoinDetail>())
                val coinDetail = api.getCoinById(coinId).toCoinDetail()
                emit(Resource.Success<CoinDetail>(coinDetail))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<CoinDetail>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )

            } catch (e: IOException) {
                emit(Resource.Error<CoinDetail>("Could not reach the server"))
            }
        }
    }
}