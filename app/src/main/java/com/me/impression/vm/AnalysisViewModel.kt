package com.me.impression.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.base.BaseViewModel
import com.me.impression.db.model.AnalysisRecord
import com.me.impression.db.model.NoteRecord
import com.me.impression.utils.DateUtils
import com.me.impression.utils.RxTools

/**
 * @author: Jie Cai
 * @desc:
 */
class AnalysisViewModel(application: Application) :
    BaseViewModel(application)
{
    var mAnalysisRecord: MutableLiveData<MutableList<AnalysisRecord>> = MutableLiveData()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        loadData()
    }

    private fun loadData()
    {
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.analysisDao().getRecent()
        }.subscribe {
            if(it.isNotEmpty()) {
                mAnalysisRecord.value = ArrayList(it)
            }
        }
        addDisposable(d)
    }

}