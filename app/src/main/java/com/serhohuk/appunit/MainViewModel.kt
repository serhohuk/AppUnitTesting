package com.serhohuk.appunit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhohuk.appunit.remote.response.CryptoData
import com.serhohuk.appunit.remote.response.CryptoResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

class MainViewModel(
    private val useCase : BaseCryptoUseCase
//    private val dispatcher : CoroutineDispatcher,
//    private val scope: CoroutineScope
) : ViewModel() {
    //

    private val stateFlow : MutableStateFlow<Resource<CryptoData>> = MutableStateFlow(Resource.Loading())
    val cryptoFlow = stateFlow.asStateFlow()

    fun getCryptoData(id : String) = viewModelScope.launch {
            val networkResponse = useCase.execute(id)
            stateFlow.value = if (networkResponse.isSuccessful){
                Resource.Succes(networkResponse.body()!!.data)
            } else {
                Resource.Error(networkResponse.errorBody()!!.string())
            }
    }

    suspend fun getDelayedData(): Int {
        val result = viewModelScope.async {
            delay(5000)
            20
        }
        return result.await()
    }

}

