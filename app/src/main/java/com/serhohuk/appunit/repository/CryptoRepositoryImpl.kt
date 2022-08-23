package com.serhohuk.appunit.repository

import com.serhohuk.appunit.remote.CryptoService
import com.serhohuk.appunit.remote.RetrofitInstance
import com.serhohuk.appunit.remote.response.CryptoResponse
import retrofit2.Response
import retrofit2.create

class CryptoRepositoryImpl : CryptoRepository {

    override suspend fun getCryptoInfo(id: String): Response<CryptoResponse> {
        return RetrofitInstance.api.getCoinData(id)
    }
}