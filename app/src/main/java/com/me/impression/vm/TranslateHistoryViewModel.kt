package com.me.impression.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.base.BaseViewModel
import com.me.impression.common.Constants
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

    private fun getHistoryData()
    {
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.historyTranslateDao().getAllByType(Constants.HistoryRecordType.QUERY)
        }.subscribe {
            mHistoryRecord.value = ArrayList(it)
        }
        addDisposable(d)
    }



}