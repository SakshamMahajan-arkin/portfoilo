package com.example.portfolio_stocks.portfolio.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.portfolio_stocks.portfolio.model.ApiResponse
import com.example.portfolio_stocks.portfolio.model.PortfolioData
import com.example.portfolio_stocks.portfolio.repo.PortfolioRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class PortfolioVM(private val portfolioRepo: PortfolioRepo) : ViewModel() {

    private val _portFolioDetails = MutableLiveData<ApiResponse>()
    val PortFolioDetails: LiveData<ApiResponse>
        get() = _portFolioDetails


    fun fetchPortfolioDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = portfolioRepo.getPortfolio()
            if (response.data != null) {
                _portFolioDetails.postValue(response)
            } else {
                // Handle error
            }
        }
    }

    fun getProfitLoss(portfolioData: PortfolioData): Double {
        var profitLoss = 0.0
        profitLoss = getCurrentValue(portfolioData) - getTotalInvestment(portfolioData)
        return profitLoss
    }

    fun getTotalInvestment(portfolioData: PortfolioData?=null): Double {
        var totalInvestment = 0.0
        portfolioData?.userHolding?.forEach {
            totalInvestment += it.quantity * it.avgPrice
        }
        return totalInvestment
    }

    fun getCurrentValue(portfolioData: PortfolioData?=null): Double {
        var totalCurrentValue = 0.0
        portfolioData?.userHolding?.forEach {
            totalCurrentValue += it.quantity * it.ltp
        }
        return totalCurrentValue
    }

    fun getTodayProfitLoss(portfolioData: PortfolioData?=null): Double {
        var todayProfitLoss = 0.0
        portfolioData?.userHolding?.forEach {
            todayProfitLoss += it.quantity * (it.close - it.ltp)
        }
        return todayProfitLoss
    }
}