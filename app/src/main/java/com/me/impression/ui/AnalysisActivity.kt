package com.me.impression.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.db.model.AnalysisRecord
import com.me.impression.utils.DateUtils
import com.me.impression.vm.AnalysisViewModel
import kotlinx.android.synthetic.main.activity_analysis.*


class AnalysisActivity : BaseActivity<AnalysisViewModel>() {

    override fun getLayoutId(): Int  = R.layout.activity_analysis

    override fun initView() {
        title = getString(R.string.title_analysis)
        initLineChart(reviewLineChart,true)
        initLineChart(durationLineChart,false)
    }

    override fun setListener() {
        mViewModel.mAnalysisRecord.observe(this, Observer {
            showReviewChart(it)
            showDurationChart(it)

            setChartFillDrawable(reviewLineChart,ContextCompat.getDrawable(this,
                R.drawable.fade_review_chart))
            setChartFillDrawable(durationLineChart,ContextCompat.getDrawable(this,
                R.drawable.fade_duration_chart))
        })
    }

    private fun initLineChart(lineChart:LineChart,bReview:Boolean)
    {
        lineChart.setDrawGridBackground(false)
        lineChart.setBackgroundColor(Color.WHITE)
        lineChart.setDrawBorders(false)
        lineChart.isDragEnabled = false
        lineChart.setTouchEnabled(false)
        lineChart.animateY(2000)
        lineChart.animateX(1000)

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = object:IndexAxisValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                val idx = value.toInt()
                val list = mViewModel.mAnalysisRecord.value!!
                if(idx>=0 && idx<list.size){
                    return DateUtils.format(list[idx].date,"dd/MM")
                }else{
                    return "--/--"
                }
            }
        }
        xAxis.setLabelCount(7,false)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        val leftYAxis = lineChart.axisLeft
        leftYAxis.axisMinimum = 0f
        leftYAxis.setDrawGridLines(true)
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);

        leftYAxis.valueFormatter = object:IndexAxisValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                if(bReview) {
                    return value.toInt().toString()
                }else{
                    return value.toInt().toString() +"s"
                }
            }
        }
        leftYAxis.labelCount = 8

        val rightYaxis = lineChart.axisRight
        rightYaxis.axisMinimum = 0f
        rightYaxis.setDrawGridLines(false)
        rightYaxis.isEnabled = false

        val legend = lineChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textSize = 13f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        val desc = Description()
        desc.text = "recent data"
        desc.textSize = 10f
        lineChart.description = desc
    }

    private fun initLineDataSet(lineDataSet: LineDataSet, color:Int, mode:LineDataSet.Mode) {
        lineDataSet.color = color
        lineDataSet.setCircleColor(color)
        lineDataSet.lineWidth = 1f
        lineDataSet.circleRadius = 3f
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.valueTextSize = 13f
        lineDataSet.setDrawFilled(true)
        lineDataSet.formLineWidth = 1f
        lineDataSet.formSize = 15.0f
        lineDataSet.valueFormatter = object:IndexAxisValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        lineDataSet.mode = mode
    }

    private fun showReviewChart(dataList:List<AnalysisRecord>) {
        //count of review
        val entriesOfCount:ArrayList<Entry> = ArrayList()
        for(i in dataList.indices){
            val record = dataList[i]
            val entry = Entry(i.toFloat(), record.count.toFloat())
            entriesOfCount.add(entry)
        }
        val lineDataSetOfCount = LineDataSet(entriesOfCount, "Number of words reviewed each time")
        initLineDataSet(lineDataSetOfCount, ContextCompat.getColor(this,R.color.chart_color),
            LineDataSet.Mode.LINEAR)
        val lineData = LineData(lineDataSetOfCount)
        reviewLineChart.data = lineData
    }

    private fun showDurationChart(dataList:List<AnalysisRecord>)
    {
        //duration of each time
        val entriesOfDuration:ArrayList<Entry> = ArrayList()
        for(i in dataList.indices){
            val record = dataList[i]
            val entry = Entry(i.toFloat(), record.duration.toFloat())
            entriesOfDuration.add(entry)
        }
        val lineDataSetOfDuration = LineDataSet(entriesOfDuration, "duration of each review")
        initLineDataSet(lineDataSetOfDuration, ContextCompat.getColor(this,R.color.orange),
            LineDataSet.Mode.LINEAR)
        val lineDataOfDuration = LineData(lineDataSetOfDuration)
        durationLineChart.data = lineDataOfDuration
    }

    private fun setChartFillDrawable(lineChart:LineChart, drawable: Drawable?) {
        if (lineChart.data != null && lineChart.data.dataSetCount > 0 && drawable != null) {
            val lineDataSet = lineChart.data.getDataSetByIndex(0) as LineDataSet
            lineDataSet.setDrawFilled(true)
            lineDataSet.fillDrawable = drawable
            lineChart.invalidate()
        }
    }
}