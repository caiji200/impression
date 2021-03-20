package com.me.impression.vm

import android.app.Application
import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.me.impression.R
import com.me.impression.base.BaseViewModel
import com.me.impression.common.AppConfig
import com.me.impression.common.Constants
import com.me.impression.db.model.HistoryRecord
import com.me.impression.db.model.NoteRecord
import com.me.impression.utils.EncryptUtils
import com.me.impression.utils.L
import com.me.impression.utils.RxTools
import com.me.impression.utils.ToastUtils
import java.util.*
import kotlin.collections.ArrayList

class TranslateViewModel(application: Application) :
    BaseViewModel(application)
{
    var mResult: MutableLiveData<String> = MutableLiveData()
    var mHistoryRecord:MutableLiveData<MutableList<HistoryRecord>> = MutableLiveData()

    fun translate(query:String, from:String, to:String,bSaveHistory:Boolean = true)
    {
        val apiParams = ArrayMap<String, String>()
        apiParams["q"] = query
        apiParams["from"] = from
        apiParams["to"] = to
        apiParams["appid"] = AppConfig.TRANSLATE_APPID
        val salt = System.currentTimeMillis().toString()
        apiParams["salt"] = salt
        apiParams["sign"] = getSign(query,salt)
        showLoading(R.string.translate_now)
        val observable = mRepoManager.apiService.translate(apiParams)
            .doOnNext {
                if(it.trans_result != null && it.trans_result!!.isNotEmpty() && bSaveHistory){
                    val destText = it.trans_result!![0].dst
                    saveHistoryToDb(from,to,query,destText?:"")
                }
            }
        apiCall(observable, R.string.translate_now,onSuccess = {
            if(it.trans_result != null && it.trans_result!!.isNotEmpty()){
                L.d("http","result==>"+it.trans_result!![0].dst)
                mResult.value = it.trans_result!![0].dst
            }
        },onFailed = {
            L.d("http","exception==>"+it.errorMessage)
            false
        })
    }

    private fun getSign(query:String,salt:String):String
    {
        val str = "${AppConfig.TRANSLATE_APPID}$query$salt${AppConfig.TRANSLATE_KEY}"
        return EncryptUtils.encryptMD5ToString(str).toLowerCase(Locale.getDefault())
    }

    //save translate result to database
    private fun saveHistoryToDb(from:String,to:String,
                                srcText:String,destText:String)
    {
        val item = HistoryRecord()
        item.type = Constants.HistoryRecordType.QUERY
        item.from = from
        item.to = to
        item.srcText = srcText
        item.destText = destText
        item.createTime = System.currentTimeMillis()
        mRepoManager.db.historyTranslateDao().insert(item)
    }

    fun getHistoryData()
    {
        val d = RxTools.observableOnIoMain {
            mRepoManager.db.historyTranslateDao().getRecent()
        }.subscribe {
            mHistoryRecord.value = ArrayList(it)
        }
        addDisposable(d)
    }

    fun addToNotebook(from:String,to:String,srcText: String,destText: String)
    {
        val d = RxTools.observableOnIoMain {
            val record = NoteRecord()
            record.from = from
            record.to = to
            record.srcText = srcText
            record.destText = destText
            record.type = 0
            record.createTime = System.currentTimeMillis()
            mRepoManager.db.noteBookDao().insert(record)
        }.subscribe {
            if(it>=0){
                ToastUtils.showShort("add result to notebook")
            }
        }
        addDisposable(d)
    }

}