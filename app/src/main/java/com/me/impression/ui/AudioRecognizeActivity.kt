package com.me.impression.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
    private var canRecognize = false

    private val TAG = "tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemNavigationBar()
        super.onCreate(savedInstanceState)
        tempResultTv.movementMethod = ScrollingMovementMethod.getInstance()
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
                setRecognizeResult(result.asrResult,result.transResult,false)
            } else if (resultType == OnRecognizeListener.TYPE_FINAL_RESULT) {
                if (result.error == 0) {
                    Log.d(TAG, "final：" + result.asrResult)
                    Log.d(TAG, "translate result：" + result.transResult)
                    setRecognizeResult(result.asrResult,result.transResult,true)
                    stopRecognize()
                    canRecognize = true
                } else {
                    Log.d(TAG, "translate error code：" + result.error + " errorMsg：" + result.errorMsg)
                }
            }
        })
        tipsTv.postDelayed({
            L.d(TAG,"=========start =====")
            startRecognize()
        },1000)
    }

    private fun startRecognize()
    {
        if (bZhToEn) {
            mClient?.startRecognize("zh", "en")
        } else {
            mClient?.startRecognize("en", "zh")
        }
        canRecognize = false
        lottieView.resumeAnimation()
        recognizeTipsTv.text = getString(R.string.stop_recognize_tips)
    }

    private fun stopRecognize()
    {
        mClient?.stopRecognize()
        recognizeTipsTv.text = getString(R.string.start_recognize_tips)
        lottieView.pauseAnimation()
    }

    override fun setListener() {
        recognitionView.setOnClickListener {
            L.d(TAG,"=========stop #=====")
            if(canRecognize){
                startRecognize()
            }else{
                stopRecognize()
            }
        }
    }

    private fun setRecognizeResult(src:String,dest:String?,bFinal:Boolean)
    {
        tipsTv.gone()
        srcTv.text = src
        destTv.text = dest
        if (bFinal) {
            val temp = tempResultTv.text.toString() + src +"\n"+dest+"\n\n"
            tempResultTv.text = temp
            if (bZhToEn) {
                mViewModel.saveHistoryToDb("zh", "en",src,dest)
            } else {
                mViewModel.saveHistoryToDb("en", "zh",src,dest)
            }
        }
    }


}