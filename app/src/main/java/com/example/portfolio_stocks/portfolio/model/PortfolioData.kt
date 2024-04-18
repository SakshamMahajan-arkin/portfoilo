package com.example.portfolio_stocks.portfolio.model

import com.google.gson.annotations.SerializedName

data class PortfolioData(
    @SerializedName("userHolding") val userHolding: List<StockData>? = null,
)
