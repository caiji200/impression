package com.me.impression.vm

import android.app.Application
import com.me.impression.base.BaseViewModel
import com.me.impression.common.Constants
import com.me.impression.db.model.HistoryRecord
import com.me.impression.utils.RxTools

class AudioRecognizeViewModel(application: Application) :
    BaseViewModel(application)
{

    //save translate result to database
    fun saveHistoryToDb(from:String,to:String,
                                srcText:String,destText:String?)
    {
        val item = HistoryRecord()
        item.type = Constants.HistoryRecordType.AUDIO
        item.from = from
        item.to = to
        item.srcText = srcText
        item.destText = destText ?: ""
        item.createTime = System.currentTimeMillis()
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.historyTranslateDao().insert(item)
        }.subscribe()
        addDisposable(d)
    }
}