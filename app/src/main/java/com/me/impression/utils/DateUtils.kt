package com.me.impression.utils

import java.text.SimpleDateFormat
import java.util.*

/**

 * @author: lisc
 * @date: 2021-03-18 下午 05:06
 * @desc:
 */
object DateUtils {

    fun getAnalysisDate():Long
    {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY,23)
        cal.set(Calendar.MINUTE,59)
        cal.set(Calendar.SECOND,59)
        cal.set(Calendar.MILLISECOND,0)
        return cal.timeInMillis
    }

    fun format(dateTime:Long,pattern:String):String
    {
        val cal = Calendar.getInstance()
        cal.timeInMillis = dateTime
        val df = SimpleDateFormat(pattern)
        return df.format(cal.time)
    }
}