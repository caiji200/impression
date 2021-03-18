package com.me.impression.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

/**
 * @version V1.0.0
 * @name BaseApplication
 */
open class BaseApplication : MultiDexApplication() {

    companion object {
        lateinit var instance: BaseApplication
            private set

        fun getAppContext(): Context
        {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
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
