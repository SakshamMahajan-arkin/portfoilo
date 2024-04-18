package com.example.portfolio_stocks.portfolio.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfolio_stocks.BaseActivity
import com.example.portfolio_stocks.R
import com.example.portfolio_stocks.databinding.ActivityPortfolioBinding
import com.example.portfolio_stocks.portfolio.adapter.PortFolioAdapter
import com.example.portfolio_stocks.portfolio.model.PortfolioData
import com.example.portfolio_stocks.portfolio.network.PortfolioApiHelper
import com.example.portfolio_stocks.portfolio.observeNonNull
import com.example.portfolio_stocks.portfolio.retrofit.RetrofitBuilder
import com.example.portfolio_stocks.portfolio.view_models.PortfolioVM
import com.example.portfolio_stocks.portfolio.view_models.ViewModelFactory

class PortfolioActivity : BaseActivity() {

    private lateinit var binding: ActivityPortfolioBinding
    private lateinit var portfolioVM: PortfolioVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initObserver()

        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchPortfolioData()
        initAdapter()
    }

    private fun initAdapter() {
        val adapter = PortFolioAdapter(this, emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PortfolioActivity)
            this.adapter = adapter
        }
    }

    private fun initViewModel() {
        portfolioVM = ViewModelProvider(
            this,
            ViewModelFactory(PortfolioApiHelper(RetrofitBuilder.apiService))
        ).get(PortfolioVM::class.java)
    }


    private fun initObserver() {
        portfolioVM.PortFolioDetails.observeNonNull(this) { ApiResponse ->

            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.isVisible = false
            binding.recyclerView.isVisible = true

            ApiResponse.data.userHolding?.let {
                (binding.recyclerView.adapter as PortFolioAdapter).list = it
                binding.recyclerView.adapter?.notifyDataSetChanged()
                setBottomNavDate(ApiResponse.data)
            }
        }
    }

    private fun setBottomNavDate(portfolioData: PortfolioData) {
        binding.bottomNavView.isVisible = true
        binding.bottomNavView.apply {
            setBackgroundColor(ContextCompat.getColor(this@PortfolioActivity, R.color.grey_dark))
        }
        binding.bottomNavViewImage.apply {
            setImageResource(R.drawable.ic_launcher_background)
        }
        binding.bottomNavViewText.setText(R.string.Portfolio)

        binding.profitLoss.isVisible = true
        binding.profitLoss.apply {
            setBackgroundColor(ContextCompat.getColor(this@PortfolioActivity, R.color.grey))
        }
        binding.profitLossText.text = "Profit & Loss*"

        if (portfolioVM.getProfitLoss(portfolioData) > 0) {
            binding.profitLossValue.setTextColor(ContextCompat.getColor(this, R.color.dark_green))
        } else {
            binding.profitLossValue.setTextColor(ContextCompat.getColor(this, R.color.red))
        }

        binding.profitLossValue.text =
            "₹ " + portfolioVM.getProfitLoss(portfolioData).toFloat().toString()


        binding.profitLoss.setOnClickListener {
            handleExpandAndCollapse(portfolioData)
        }
    }

    private fun fetchPortfolioData() {

        binding.shimmerLayout.isVisible = true
        binding.shimmerLayout.startShimmer()
        binding.recyclerView.isVisible = false

        portfolioVM.fetchPortfolioDetails()
    }

    private fun handleExpandAndCollapse(portfolioData: PortfolioData? = null) {
        if (binding.expandedView.isVisible) {
            binding.expandedView.isVisible = false
            binding.divider.isVisible = false
        } else {
            binding.expandedView.isVisible = true
            binding.divider.isVisible = true
            setExpandedViewData(portfolioData)
            binding.expandedView.setOnClickListener {
                binding.expandedView.isVisible = false
                binding.divider.isVisible = false
            }
        }
    }

    private fun setExpandedViewData(portfolioData: PortfolioData? = null) {

        binding.expandedView.apply {
            setBackgroundColor(ContextCompat.getColor(this@PortfolioActivity, R.color.grey))
        }

        binding.expandedViewCurrentValue.setText(R.string.current_value)
        binding.expandedViewTotalInvestment.setText(R.string.total_investment)
        binding.expandedViewTodayProfitLoss.setText(R.string.today_p_l)

        if (portfolioVM.getTotalInvestment(portfolioData) > 0) {
            binding.expandedViewTotalInvestmentAmount.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.dark_green
                )
            )
        } else {
            binding.expandedViewTotalInvestmentAmount.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.red
                )
            )
        }

        if (portfolioVM.getCurrentValue(portfolioData) > 0) {
            binding.expandedViewCurrentValueAmount.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.dark_green
                )
            )
        } else {
            binding.expandedViewCurrentValueAmount.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.red
                )
            )
        }

        if (portfolioVM.getTodayProfitLoss(portfolioData) > 0) {
            binding.expandedViewTodayProfitLoss.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.dark_green
                )
            )
        } else {
            binding.expandedViewTodayProfitLossAmount.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.red
                )
            )
        }

        binding.expandedViewTotalInvestmentAmount.text =
            "₹ " + portfolioVM.getTotalInvestment(portfolioData).toFloat().toString()
        binding.expandedViewCurrentValueAmount.text =
            "₹ " + portfolioVM.getCurrentValue(portfolioData).toFloat().toString()
        binding.expandedViewTodayProfitLossAmount.text =
            "₹ " + portfolioVM.getTodayProfitLoss(portfolioData).toFloat().toString()
    }
}
