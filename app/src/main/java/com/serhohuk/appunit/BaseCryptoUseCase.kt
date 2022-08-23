package com.serhohuk.appunit

import com.serhohuk.appunit.remote.response.CryptoResponse
import retrofit2.Response

/**
 * Created by serhh on 21.08.2022
 */

interface BaseCryptoUseCase {
    suspend fun execute(id : String) : Response<CryptoResponse>
}