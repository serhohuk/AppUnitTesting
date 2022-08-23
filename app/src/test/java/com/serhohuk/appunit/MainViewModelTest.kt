package com.serhohuk.appunit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.serhohuk.appunit.remote.response.CryptoData
import com.serhohuk.appunit.remote.response.CryptoResponse
import io.mockk.*
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement
import retrofit2.Response
import kotlin.coroutines.ContinuationInterceptor

/**
 * Created by serhh on 22.08.2022
 */

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `does viewModel return success`() = testCoroutineRule.runBlockingTest{
        val errorBody = ResponseBody.create(MediaType.parse("application/json"),"{\"errorMessage\": \"Invalid Request\"")
        val useCase = mockk<BaseCryptoUseCase>()
        coEvery { useCase.execute(any()) } returns Response.error(400, errorBody)


        val viewModel = MainViewModel(useCase)

        viewModel.getCryptoData("")

        coVerify { useCase.execute(any()) }

        assertEquals(400,useCase.execute("").code())

        assertEquals(Resource.Error<CryptoData>("{\"errorMessage\": \"Invalid Request\"").message,viewModel.cryptoFlow.value.message)
    }


    @Test
    fun `viewModel get crypto data`() = testCoroutineRule.runBlockingTest{
        val useCase = mockk<BaseCryptoUseCase>()
        val cryptoData = CryptoData("7", "bitcoin","Bitcoin", "21000.046", "1", "BTC")
        val cryptoResponse = CryptoResponse(cryptoData)
        coEvery { useCase.execute(any()) } returns Response.success(cryptoResponse)
        val viewModel = MainViewModel(useCase)

        viewModel.getCryptoData("bitcoin")
        coVerify { useCase.execute("bitcoin") }
        assertEquals("7", viewModel.cryptoFlow.value.data!!.changePercent24Hr)

        assertTrue(viewModel.cryptoFlow.value is Resource.Succes)
    }


    @Test
    fun `delayed data test`() = testCoroutineRule.runBlockingTest{
        val useCase = mockk<BaseCryptoUseCase>()
        val viewModel = MainViewModel(useCase)

        assertEquals(20, viewModel.getDelayedData())
    }


}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(),
    TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(
            this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        )
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)

            base.evaluate()

            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }

}