package com.me.impression.vm

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.R
import com.me.impression.base.BaseApplication
import com.me.impression.base.BaseViewModel
import com.me.impression.common.Constants
import com.me.impression.db.model.AnalysisRecord
import com.me.impression.db.model.NoteRecord
import com.me.impression.utils.DateUtils
import com.me.impression.utils.PreferencesUtils
import com.me.impression.utils.RxTools
import com.me.impression.utils.ToastUtils
import io.reactivex.Observable
import org.json.JSONObject

class NoteViewModel(application: Application) :
    BaseViewModel(application)
{
    var mRecords:MutableLiveData<MutableList<NoteRecord>> = MutableLiveData()
    var mNoteCount:MutableLiveData<Long> = MutableLiveData()
    var mReviewDayCount:MutableLiveData<Int> = MutableLiveData()
    var mAnalysisRecord:MutableLiveData<AnalysisRecord?> = MutableLiveData()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        mRecords.value = ArrayList()
    }


    fun initData()
    {
        val d = RxTools.observableOnIoMain {
            val date = DateUtils.getAnalysisDate()
            val analysisRecord = mRepoManager.db.analysisDao().getRecord(
                Constants.AnalysisType.DAY_COUNT,date)
            mAnalysisRecord.postValue(analysisRecord)
            val count = mRepoManager.db.noteBookDao().getCount()
            mNoteCount.postValue(count)

            val str = PreferencesUtils.getString(BaseApplication.getAppContext(),
                    Constants.PrefKey.ReviewDayCount,"")
            if(!TextUtils.isEmpty(str)){
                val json = JSONObject(str)
                val count = json.optInt("count")
                mReviewDayCount.postValue(count)
            }else{
                mReviewDayCount.postValue(0)
            }
        }.subscribe {

        }
        addDisposable(d)
    }

    fun addToRecord(from:String,to:String,srcText: String,destText: String)
    {
        val record = NoteRecord()
        record.from = from
        record.to = to
        record.srcText = srcText
        record.destText = destText
        record.type = 0
        record.createTime = System.currentTimeMillis()
        mRecords.value?.add(record)
    }

    fun saveToNoteBook()
    {
        showLoading(R.string.loading)
        val d = RxTools.observableOnIoMain {
            val list = mRecords.value
            var count = 0
            list?.let {
                 it.forEach {
                     mRepoManager.db.noteBookDao().insert(it)
                     count+=1
                 }
            }
            count
        }.subscribe({
            closeLoading()
            if(it>=0){
                ToastUtils.showShort("add result to notebook")
            }
        },{
            closeLoading()
        })
        addDisposable(d)
    }
}