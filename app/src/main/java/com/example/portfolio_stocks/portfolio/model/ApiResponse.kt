package com.example.portfolio_stocks.portfolio.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("data") val data: PortfolioData
)
