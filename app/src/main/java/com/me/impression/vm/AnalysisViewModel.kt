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
        val list = ArrayList<AnalysisRecord>()

        val d1 = AnalysisRecord()
        d1.count = 10
        d1.date = DateUtils.getAnalysisDate()
        list.add(d1)

        val d2 = AnalysisRecord()
        d2.count = 20
        d2.date = DateUtils.getAnalysisDate()
        list.add(d2)

        val d3 = AnalysisRecord()
        d3.count = 40
        d3.date = DateUtils.getAnalysisDate()
        list.add(d3)

        val d4 = AnalysisRecord()
        d4.count = 35
        d4.date = DateUtils.getAnalysisDate()
        list.add(d4)

        val d5 = AnalysisRecord()
        d5.count = 50
        d5.date = DateUtils.getAnalysisDate()
        list.add(d5)

        mAnalysisRecord.value =list

//        val d = RxTools.observableOnIoMain {
//            mRepoManager.db.analysisDao().getAll()
//        }.subscribe {
//            mAnalysisRecord.value = ArrayList(it)
//        }
//        addDisposable(d)
    }

}