package com.example.portfolio_stocks.portfolio.network

import com.example.portfolio_stocks.portfolio.retrofit.PortfolioApiService

class PortfolioApiHelper(private val apiService: PortfolioApiService) {
    suspend fun getPortfolio() = apiService.getPortfolio()
}
