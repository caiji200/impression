package com.me.impression

import com.me.impression.base.BaseApplication
import com.me.impression.db.AppDatabase.Companion.instance

/**
 * @author Jie Cai
 * @desc Impression Application
 * */
class ImpressionApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        instance(this)
    }
}