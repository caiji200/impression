package com.me.impression.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.base.BaseViewModel
import com.me.impression.common.Constants
import com.me.impression.db.model.HistoryRecord
import com.me.impression.utils.RxTools

class TranslateAudioViewModel(application: Application) :
    BaseViewModel(application)
{
    var mHistoryRecord: MutableLiveData<MutableList<HistoryRecord>> = MutableLiveData()

    override fun onResume() {
        super.onResume()
        getHistoryData()
    }

    private fun getHistoryData()
    {
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.historyTranslateDao().getAllByType(Constants.HistoryRecordType.AUDIO)
        }.subscribe {
            mHistoryRecord.value = ArrayList(it)
        }
        addDisposable(d)
    }



}