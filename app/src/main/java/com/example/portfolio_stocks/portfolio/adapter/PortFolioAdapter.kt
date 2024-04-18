package com.example.portfolio_stocks.portfolio.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolio_stocks.R
import com.example.portfolio_stocks.portfolio.model.StockData

class PortFolioAdapter(
    context: Context,
    listData: List<StockData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context: Context = context
    var list: List<StockData> = listData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = View.inflate(context, R.layout.stock_view, null)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stockName: TextView = itemView.findViewById(R.id.stock_name)
        var stockQuantity: TextView = itemView.findViewById(R.id.stock_quantity)
        var stockLTP: TextView = itemView.findViewById(R.id.ltp)
        var stockPAndL: TextView = itemView.findViewById(R.id.p_l)

        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            stockName.text = recyclerViewModel.symbol
            stockQuantity.text = "NET QTY : " + (recyclerViewModel.quantity.toString())
            stockLTP.text = "LTP: ₹ " + recyclerViewModel.ltp.toString()
            val stockPAndLValue =
                ((recyclerViewModel.ltp - recyclerViewModel.avgPrice) * recyclerViewModel.quantity)
            if (stockPAndLValue < 0) {
                stockPAndL.setTextColor(context.resources.getColor(R.color.red))
            } else {
                stockPAndL.setTextColor(context.resources.getColor(R.color.dark_green))
            }
            stockPAndL.text =
                "P&L: ₹ " + stockPAndLValue.toFloat().toString()
        }
    }
}