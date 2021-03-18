package com.me.impression.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.base.BaseViewModel
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
}