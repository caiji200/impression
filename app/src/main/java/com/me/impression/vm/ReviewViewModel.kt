package com.me.impression.vm

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.base.BaseApplication
import com.me.impression.base.BaseViewModel
import com.me.impression.common.Constants
import com.me.impression.db.model.AnalysisRecord
import com.me.impression.db.model.NoteRecord
import com.me.impression.utils.DateUtils
import com.me.impression.utils.PreferencesUtils
import com.me.impression.utils.RxTools
import org.json.JSONObject
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
    private var mStartTime = 0L

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getNoteData()
        setReviewDayCount()
        mStartTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        mStartTime = System.currentTimeMillis()
    }

    override fun onPause() {
        super.onPause()
        val duration = (System.currentTimeMillis() - mStartTime) / 1000
        mAnalysisRecord?.let {
            if(duration >0){
                it.duration += duration
                val d = RxTools.observableOnIoMain {
                    mRepoManager.db.analysisDao().update(it)
                }.subscribe()
                addDisposable(d)
            }
        }
    }

    private fun getNoteData()
    {
        val d = RxTools.observableOnIoMain {

            val date = DateUtils.getAnalysisDate()
            mAnalysisRecord = mRepoManager.db.analysisDao()
                    .getRecord(Constants.AnalysisType.DAY_COUNT,date)
            if (mAnalysisRecord == null) {
                mAnalysisRecord = AnalysisRecord(0,Constants.AnalysisType.DAY_COUNT,
                        0,0,date,System.currentTimeMillis())
                val id = mRepoManager.db.analysisDao().insert(mAnalysisRecord!!)
                mAnalysisRecord!!.id = id
            }
            mRepoManager.db.noteBookDao().getAll()
        }.subscribe {
            mNotes.clear()
            mNotes.addAll(it)
            if (mNotes.isNotEmpty()) {
                mCurIndex = 0
                mCurNote.value = mNotes[0]
            }
        }
        addDisposable(d)
    }

    private fun setReviewDayCount()
    {
        val str = PreferencesUtils.getString(BaseApplication.getAppContext(),
                Constants.PrefKey.ReviewDayCount,"")
        if(TextUtils.isEmpty(str)){
            val json = JSONObject()
            json.put("time",DateUtils.getAnalysisDate())
            json.put("count",1)
            PreferencesUtils.putString(BaseApplication.getAppContext(),
                    Constants.PrefKey.ReviewDayCount,json.toString())
        }else{
            val json = JSONObject(str)
            val time = json.optLong("time")
            val count = json.optInt("count")
            val curDate = DateUtils.getAnalysisDate()
            if (curDate > time) {
                val json = JSONObject(str)
                json.put("time",curDate)
                json.put("count",count+1)
                PreferencesUtils.putString(BaseApplication.getAppContext(),
                        Constants.PrefKey.ReviewDayCount,json.toString())
            }
        }
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