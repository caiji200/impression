package com.me.impression.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.me.impression.R
import com.me.impression.base.BaseViewModel
import com.me.impression.db.model.NoteRecord
import com.me.impression.utils.RxTools
import io.reactivex.Observable

class NoteViewModel(application: Application) :
    BaseViewModel(application)
{
    var mRecords:MutableLiveData<MutableList<NoteRecord>> = MutableLiveData()
    var mNoteCount:MutableLiveData<Long> = MutableLiveData()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        mRecords.value = ArrayList()
        initData()
    }

    private fun initData()
    {
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.noteBookDao().getCount()
        }.subscribe {
            mNoteCount.value = it
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