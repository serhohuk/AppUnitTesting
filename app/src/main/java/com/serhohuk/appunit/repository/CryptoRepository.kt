package com.serhohuk.appunit.repository

import com.serhohuk.appunit.remote.response.CryptoResponse
import retrofit2.Response

interface CryptoRepository {
    suspend fun getCryptoInfo(id : String) : Response<CryptoResponse>
}