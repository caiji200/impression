package com.me.impression.ui

import android.os.Bundle
import android.util.Log
import com.baidu.translate.asr.OnRecognizeListener
import com.baidu.translate.asr.TransAsrClient
import com.baidu.translate.asr.TransAsrConfig
import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.common.AppConfig
import com.me.impression.extensions.gone
import com.me.impression.utils.L
import com.me.impression.vm.AudioRecognizeViewModel
import kotlinx.android.synthetic.main.activity_audio_recognize.*

class AudioRecognizeActivity : BaseActivity<AudioRecognizeViewModel>() {

    private var mClient: TransAsrClient? = null
    private var bZhToEn = true
    private val TAG = "tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemNavigationBar()
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int  = R.layout.activity_audio_recognize

    override fun initView() {
        showBack(false)
        showDownLine(false)
        setToolbarBackGroundColor(R.color.audio_bg_color)
        bZhToEn = intent.getBooleanExtra("type",true)
        val config = TransAsrConfig(AppConfig.TRANSLATE_APPID, AppConfig.TRANSLATE_KEY)
        config.setAutoPlayTts(true)
        config.setPartialCallbackEnabled(true)
        mClient = TransAsrClient(this, config)
        mClient?.setRecognizeListener(OnRecognizeListener { resultType, result ->
            if (resultType == OnRecognizeListener.TYPE_PARTIAL_RESULT) {
                Log.d(TAG, "result ：" + result.asrResult)
                setRecognizeView(result.asrResult,result.transResult)
            } else if (resultType == OnRecognizeListener.TYPE_FINAL_RESULT) {
                if (result.error == 0) {
                    Log.d(TAG, "final：" + result.asrResult)
                    Log.d(TAG, "translate result：" + result.transResult)
                    setRecognizeView(result.asrResult,result.transResult)
                    tipsTv.postDelayed({closePage() },1000)
                } else {
                    Log.d(TAG, "translate error code：" + result.error + " errorMsg：" + result.errorMsg)
                }
            }
        })
        tipsTv.postDelayed({
            L.d(TAG,"=========start =====")
            if (bZhToEn) {
                mClient?.startRecognize("zh", "en")
            } else {
                mClient?.startRecognize("en", "zh")
            }
        },1000)
    }

    override fun setListener() {

        recognitionView.setOnClickListener {
            L.d(TAG,"=========stop #=====")
            mClient?.stopRecognize()
        }
    }

    private fun setRecognizeView(src:String,dest:String?)
    {
        tipsTv.gone()
        srcTv.text = src
        destTv.text = dest
    }


}