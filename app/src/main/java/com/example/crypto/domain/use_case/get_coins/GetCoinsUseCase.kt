package com.example.crypto.domain.use_case.get_coins

import com.example.crypto.commons.Resource
import com.example.crypto.data.remote.dto.toCoin
import com.example.crypto.domain.model.Coin
import com.example.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke() = flow<Resource<List<Coin>>>{
        try {
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success<List<Coin>>(coins))
        }catch (e: HttpException){
            emit(Resource.Error<List<Coin>>(e.localizedMessage?: "An unexpected error occurred "))
        }catch (e: IOException){
            emit(Resource.Error<List<Coin>>("Couldn't read the server. Check your internet connection"))
        }
    }
}