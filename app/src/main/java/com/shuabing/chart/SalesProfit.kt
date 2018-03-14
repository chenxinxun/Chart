package com.shuabing.chart

/**
 *
 * Created by shuabing on 2018/3/14.
 */
class SalesProfit(var sale: Double, var cost:Double, var profit:Double) {

    /**
     * 获取销售额，利润，和成本中的最大值，作为柱状图的最大值标准
     */
    fun getMaxValue(): Double{
        return Math.max(sale, Math.max(cost, profit))
    }
}