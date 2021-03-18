package com.me.impression.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.base.BaseViewModel
import com.me.impression.common.Constants
import com.me.impression.db.model.AnalysisRecord
import com.me.impression.db.model.NoteRecord
import com.me.impression.utils.DateUtils
import com.me.impression.utils.RxTools
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author: Jie Cai
 * @desc:
 */
class ReviewViewModel(application: Application) :
    BaseViewModel(application)
{
    var mCurNote: MutableLiveData<NoteRecord> = MutableLiveData()
    private var mCurIndex = 0
    private var mNotes:ArrayList<NoteRecord> = ArrayList()

    private var mAnalysisRecord:AnalysisRecord? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getNoteData()
    }

    private fun getNoteData()
    {
        val d = RxTools.observableOnIoMain {

            val date = DateUtils.getAnalysisDate()
            mAnalysisRecord = mRepoManager.db.analysisDao().getRecord(
                Constants.AnalysisType.DAY_COUNT,date)
            if(mAnalysisRecord == null){
                mAnalysisRecord = AnalysisRecord(0,Constants.AnalysisType.DAY_COUNT,
                        0,0,date,System.currentTimeMillis())
                val id = mRepoManager.db.analysisDao().insert(mAnalysisRecord!!)
                mAnalysisRecord!!.id = id
            }

            mRepoManager.db.noteBookDao().getAll()
        }.subscribe {
            mNotes.clear()
            mNotes.addAll(it)
            if(mNotes.isNotEmpty()){
                mCurIndex = 0
                mCurNote.value = mNotes[0]
            }
        }
        addDisposable(d)
    }

    fun showNext():Boolean
    {
        mCurIndex+=1
        if(mCurIndex>=0&&mCurIndex<mNotes.size) {
            mCurNote.value = mNotes[mCurIndex]
            mAnalysisRecord?.let {
                it.count += 1
                addDisposable(RxTools.singleTaskOnIo { mRepoManager.db.analysisDao().update(it) })
            }
        }else{
            mCurIndex = mNotes.size - 1
        }
        return mCurIndex >= mNotes.size-1
    }

    fun finishFlow()
    {
        val d = RxTools.observableOnIoMain {
            mAnalysisRecord?.let {
                it.count += 1
                mRepoManager.db.analysisDao().update(it)
            }
        }.subscribe {
            closePage()
        }
        addDisposable(d)
    }

    fun isOver():Boolean
    {
        return mCurIndex >= mNotes.size-1
    }
}