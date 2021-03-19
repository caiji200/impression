package com.me.impression.ui

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
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
        initLineChart()
    }

    override fun setListener() {
        mViewModel.mAnalysisRecord.observe(this, Observer {
            showLineChart(it,"Review Count of Day",ContextCompat.getColor(this,R.color.chart_color))
        })
    }

    private fun initLineChart()
    {
        reviewLineChart.setDrawGridBackground(false)
        reviewLineChart.setBackgroundColor(Color.WHITE)
        reviewLineChart.setDrawBorders(false)
        reviewLineChart.isDragEnabled = false
        reviewLineChart.setTouchEnabled(false)
        reviewLineChart.animateY(2000)
        reviewLineChart.animateX(1000)

        val xAxis = reviewLineChart.xAxis
        xAxis.valueFormatter = object:IndexAxisValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                val date = mViewModel.mAnalysisRecord.value!![value.toInt()].date
                return DateUtils.format(date,"dd/MM")
            }
        }
        xAxis.setLabelCount(7,false)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        val leftYAxis = reviewLineChart.axisLeft
        leftYAxis.axisMinimum = 0f
        leftYAxis.setDrawGridLines(true)
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);

        leftYAxis.valueFormatter = object:IndexAxisValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        leftYAxis.labelCount = 8

        val rightYaxis = reviewLineChart.axisRight
        rightYaxis.axisMinimum = 0f
        rightYaxis.setDrawGridLines(false)
        rightYaxis.isEnabled = false

        val legend = reviewLineChart.legend
        legend.form = Legend.LegendForm.LINE

        legend.textSize = 13f

        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL

        legend.setDrawInside(false)

        val desc = Description()
        desc.text = "recent data"
        desc.textSize = 10f
        reviewLineChart.description = desc

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

    private fun showLineChart(dataList:List<AnalysisRecord>, name:String, color:Int) {
        val entries:ArrayList<Entry> = ArrayList()
        for(i in dataList.indices){
            val record = dataList[i]
            val entry = Entry(i.toFloat(), record.count.toFloat())
            entries.add(entry)
        }
        val lineDataSet = LineDataSet(entries, name)
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR)
        val lineData = LineData(lineDataSet)
        reviewLineChart.data = lineData
    }


}