package com.example.portfolio_stocks.portfolio.model

import com.google.gson.annotations.SerializedName

data class StockData(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("ltp") val ltp: Double,
    @SerializedName("avgPrice") val avgPrice: Double,
    @SerializedName("close") val close: Double,
)
