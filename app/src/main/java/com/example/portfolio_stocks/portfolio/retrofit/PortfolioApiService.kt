package com.example.portfolio_stocks.portfolio.retrofit

import com.example.portfolio_stocks.portfolio.model.ApiResponse
import com.example.portfolio_stocks.portfolio.model.PortfolioData
import retrofit2.http.GET

interface PortfolioApiService {
    @GET("portfolio")
    suspend fun getPortfolio(): ApiResponse
}