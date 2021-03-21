package com.me.impression.common

/**

 * @author: Jie Cai
 * @desc:
 */

object Constants
{
    object PrefKey
    {
        const val TargetCount = "target_count"
        const val ReviewDayCount = "review_day_count"
        const val TranslateDirection = "translate_direction"
        const val FirstLaunch = "first_launch"
    }

    object AnalysisType
    {
        const val DAY_COUNT = 1
        const val DAY_DURATION = 2
    }

    object HistoryRecordType
    {
        const val QUERY = 1
        const val AUDIO = 2
    }
}