package com.serhohuk.appunit

/**
 * Created by serhh on 21.08.2022
 */

sealed class Resource<T>(
    val data : T? = null,
    val message : String? = null
) {
    class Succes<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}