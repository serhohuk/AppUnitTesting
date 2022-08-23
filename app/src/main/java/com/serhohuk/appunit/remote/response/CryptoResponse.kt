package com.serhohuk.appunit.remote.response

data class CryptoResponse(
    var `data`: CryptoData
)

data class CryptoData(
    var changePercent24Hr: String?,
    var id: String?,
    var name: String?,
    var priceUsd: String?,
    var rank: String,
    var symbol: String,
)