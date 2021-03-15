package com.me.impression.base

import android.app.Application
import com.blankj.utilcode.util.Utils

/**
 * @version V1.0.0
 * @name BaseApplication
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
    }


    override fun onTerminate() {
        super.onTerminate()
    }


    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

}
