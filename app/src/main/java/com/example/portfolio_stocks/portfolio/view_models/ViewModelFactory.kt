package com.example.portfolio_stocks.portfolio.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.portfolio_stocks.portfolio.network.PortfolioApiHelper
import com.example.portfolio_stocks.portfolio.repo.PortfolioRepo

class ViewModelFactory(private val portfolioApiHelper: PortfolioApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortfolioVM::class.java)) {
            return PortfolioVM(PortfolioRepo(portfolioApiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}