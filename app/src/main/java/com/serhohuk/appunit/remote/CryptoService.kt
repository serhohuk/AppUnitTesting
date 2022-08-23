package com.serhohuk.appunit.remote

import com.serhohuk.appunit.remote.response.CryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoService {

    @GET("assets/{id}")
    suspend fun getCoinData(
        @Path("id")
        cryptoId: String
    ) : Response<CryptoResponse>

}