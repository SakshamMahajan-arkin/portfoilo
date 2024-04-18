package com.example.portfolio_stocks.portfolio.repo

import com.example.portfolio_stocks.portfolio.network.PortfolioApiHelper

class PortfolioRepo (
    private val portfolioApiHelper: PortfolioApiHelper
) {
    suspend fun getPortfolio() = portfolioApiHelper.getPortfolio()
}