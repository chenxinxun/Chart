package com.shuabing.chart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_bar_chart.*

class BarChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_bar_chart)
        val optimism = SalesProfit(500.0, 400.0, 100.0)
        val negative = SalesProfit(500.0, 600.0, -100.0)
        val max = optimism.getMaxValue()
        salesChart.init(optimism.sale, max)
        costChart.init(optimism.cost, max)
        profitChart.init(optimism.profit, max)
    }
}
