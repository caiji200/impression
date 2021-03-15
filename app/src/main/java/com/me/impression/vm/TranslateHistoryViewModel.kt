package com.me.impression.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.base.BaseViewModel
import com.me.impression.db.model.HistoryRecord
import com.me.impression.utils.RxTools

class TranslateHistoryViewModel(application: Application) :
    BaseViewModel(application)
{
    var mHistoryRecord: MutableLiveData<MutableList<HistoryRecord>> = MutableLiveData()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getHistoryData()
    }

    fun getHistoryData()
    {
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.historyTranslateDao().getAll()
        }.subscribe {
            mHistoryRecord.value = ArrayList(it)
        }
        addDisposable(d)
    }

}