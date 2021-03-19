package com.me.impression.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.R
import com.me.impression.base.BaseViewModel
import com.me.impression.base.SingleLiveEvent
import com.me.impression.db.model.HistoryRecord
import com.me.impression.db.model.NoteRecord
import com.me.impression.utils.RxTools

/**
 * @author: Jie Cai
 * @desc:
 */
class NoteBookViewModel(application: Application) :
    BaseViewModel(application)
{
    var mNotes: MutableLiveData<MutableList<NoteRecord>> = MutableLiveData()
    private var mSelectList:MutableList<NoteRecord> = ArrayList()
    var mTaskOver:SingleLiveEvent<Void> = SingleLiveEvent()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getNoteData()
    }

    private fun getNoteData()
    {
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.noteBookDao().getAll()
        }.subscribe {
            mNotes.value = ArrayList(it)
        }
        addDisposable(d)
    }

    fun isSelected(file:NoteRecord):Boolean
    {
        return mSelectList.contains(file)
    }

    fun selectOrUnSelect(file:NoteRecord)
    {
        if (mSelectList.contains(file)) {
            mSelectList.remove(file)
        } else {
            mSelectList.add(file)
        }
    }

    fun getSelectItems():MutableList<NoteRecord>
    {
        return mSelectList
    }

    fun clearSelect()
    {
        mSelectList.clear()
    }


    fun deleteTask()
    {
        showLoading(R.string.wait_now)
        val d = RxTools.observableOnIoMain {
            val list = getSelectItems()
            val newList = mNotes.value
            if(!list.isNullOrEmpty() && newList != null){
                list.forEach {
                    newList.remove(it)
                    mRepoManager.db.noteBookDao().delete(it)
                }
            }
            mNotes.postValue(newList)
        }.subscribe {
            closeLoading()
            mTaskOver.call()
        }
        addDisposable(d)
    }
}