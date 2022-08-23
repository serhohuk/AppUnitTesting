package com.serhohuk.appunit

import com.serhohuk.appunit.remote.CryptoService
import com.serhohuk.appunit.remote.response.CryptoData
import com.serhohuk.appunit.remote.response.CryptoResponse
import com.serhohuk.appunit.repository.CryptoRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.HttpException
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {



    val testCoroutineDispatcher = StandardTestDispatcher()


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `response return 200 code`() = runBlockingTest{

        val service = mockk<CryptoService>()
        coEvery { service.getCoinData(any()) } returns Response.success(CryptoResponse(CryptoData("","","","100","","")))

        assertTrue(service.getCoinData("bitcoin").body()!!.data.priceUsd == "101")
    }

}