package com.shuabing.chart.graph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.shuabing.chart.R

/**
 *
 * Created by shuabing
 */
class BarChart : View {
    /**柱状图的高度*/
    private var mRectangleHeight: Float = 0f
    /**柱状图的宽度*/
    private var mRectangleWidth: Float = 0f
    /**柱状图的最大高度*/
    private var mRectangleMaxHeight: Double = 500.0
    /**柱状图高度方向的渐变开始颜色*/
    private var mBeginColor: Int = 0
    /**柱状图高度方向的渐变结束颜色*/
    private var mEndColor: Int = 0
    /**柱状图代表的实际值*/
    private var mHeightValue: Double = 0.0
    /**柱状图的最大值*/
    private var mMaxValue: Double = 0.0
    /**图的大小 */
    private var mChartPaint = Paint()


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BarChart)
        val n = a.indexCount
        for (i in 0 until n) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.BarChart_r_height) {
                mRectangleHeight = a.getDimension(attr, 5f)
            }
            if (attr == R.styleable.BarChart_r_max_height) {
                mRectangleMaxHeight = a.getDimension(attr, 210f).toDouble()
            }

            if (attr == R.styleable.BarChart_r_width) {
                mRectangleWidth = a.getDimension(attr, 70f)
            }
            if (attr == R.styleable.BarChart_r_begin_color) {
                mBeginColor = a.getColor(attr, Color.RED)
            }

            if (attr == R.styleable.BarChart_r_end_color) {
                mEndColor = a.getColor(attr, Color.RED)
            }
        }

        mChartPaint.style = Paint.Style.FILL_AND_STROKE
        mChartPaint.color = Color.RED
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun init(value: Double, maxValue: Double) {
        mHeightValue = value
        mMaxValue = maxValue
        //滚动屏幕实时，强制
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec))
        forceLayout()

    }

    private fun measureHeight(measureSpec: Int): Int {
        var result: Int
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        val height: Double
        //测绘高度
        height = if(mHeightValue < 0) {
            val negativeHeight = (mRectangleMaxHeight / mMaxValue) * Math.abs(mHeightValue)
            mRectangleMaxHeight + negativeHeight
        } else {
            mRectangleMaxHeight
        }

        if (mode == View.MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = height.toInt()
            if (mode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, size)
            }
        }
        return result

    }

    private fun measureWidth(measureSpec: Int): Int {
        var result: Int
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)

        if (mode == View.MeasureSpec.EXACTLY) {
            result = size
        } else {

            result = 75//根据自己的需要更改
            if (mode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, size)
            }
        }
        return result

    }


    override fun onDraw(canvas: Canvas) {
        val mChartRect = Rect()

        val heightValue = (mRectangleMaxHeight / mMaxValue) * Math.abs(mHeightValue)
        if(mHeightValue < 0) {
            mChartRect.top = mRectangleMaxHeight.toInt()
            mChartRect.bottom = mRectangleMaxHeight.toInt() + heightValue.toInt()
            mChartPaint.shader = LinearGradient(mChartRect.left.toFloat(), mChartRect.top.toFloat(), mChartRect.left.toFloat(), mChartRect.bottom.toFloat(), Color.rgb(0xff, 0x55, 0x65),  Color.rgb(0xff, 0xa7, 0xaf),Shader.TileMode.MIRROR)

        } else {
            mChartRect.top = mRectangleMaxHeight.toInt() - heightValue.toInt()
            if(heightValue.toInt() < dpToPx(1)) {
                mChartRect.top = mRectangleMaxHeight.toInt() - dpToPx(1).toInt()
            }
            mChartRect.bottom = mRectangleMaxHeight.toInt()
            mChartPaint.shader = LinearGradient(mChartRect.left.toFloat(), mChartRect.top.toFloat(), mChartRect.left.toFloat(), mChartRect.bottom.toFloat(), Color.rgb(0xa0, 0xbf, 0xff), Color.rgb(0x3e, 0x76, 0xeb), Shader.TileMode.MIRROR)
        }

        mChartRect.left = 0
        mChartRect.right = width

        canvas.drawRect(mChartRect, mChartPaint)
    }

    private fun dpToPx(dp: Int): Float {
        val scale = resources.displayMetrics.density
        val px = (dp * scale + 0.5f).toInt()
        return px.toFloat()
    }

}