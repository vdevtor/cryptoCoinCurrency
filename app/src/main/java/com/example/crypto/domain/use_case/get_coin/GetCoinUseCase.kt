package com.example.crypto.domain.use_case.get_coin

import com.example.crypto.commons.Resource
import com.example.crypto.data.remote.dto.toCoin
import com.example.crypto.data.remote.dto.toCoinDetail
import com.example.crypto.domain.model.Coin
import com.example.crypto.domain.model.CoinDetail
import com.example.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.Flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String) = flow<Resource<CoinDetail>>{
        try {
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success<CoinDetail>(coin))
        }catch (e: HttpException){
            emit(Resource.Error<CoinDetail>(e.localizedMessage?: "An unexpected error occurred "))
        }catch (e: IOException){
            emit(Resource.Error<CoinDetail>("Couldn't read the server. Check your internet connection"))
        }
    }
}