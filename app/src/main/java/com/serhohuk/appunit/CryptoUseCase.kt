package com.serhohuk.appunit

import com.serhohuk.appunit.remote.response.CryptoResponse
import com.serhohuk.appunit.repository.CryptoRepository
import com.serhohuk.appunit.repository.CryptoRepositoryImpl
import retrofit2.Response

/**
 * Created by serhh on 21.08.2022
 */

class CryptoUseCase : BaseCryptoUseCase {

    val repository : CryptoRepository = CryptoRepositoryImpl()

    override suspend fun execute(id: String): Response<CryptoResponse> {
        return repository.getCryptoInfo(id)
    }
}