package com.me.impression

import com.iflytek.cloud.SpeechUtility
import com.me.impression.base.BaseApplication
import com.me.impression.db.AppDatabase.Companion.instance

/**
 * @author Jie Cai
 * @desc Impression Application
 * */
class ImpressionApplication : BaseApplication() {

    override fun onCreate() {

        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id))
        super.onCreate()
        instance(this)
    }

}