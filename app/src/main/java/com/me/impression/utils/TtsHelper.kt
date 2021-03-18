package com.me.impression.utils

import android.content.Context
import com.iflytek.cloud.*

/**
 * @author: Jie Cai
 */
object TtsHelper
{
    private var mTts: SpeechSynthesizer? = null

    fun init(context: Context){
        mTts = SpeechSynthesizer.createSynthesizer(context , object:InitListener{
            override fun onInit(code: Int) {
                L.d("tts", "init code=>$code")
                if (code != ErrorCode.SUCCESS) {
                    L.d("tts", "init failed=>$code")
                }else{
                    L.d("tts", "init success")
                }
            }
        })
    }

    fun setParam(language: String) {
        mTts!!.setParameter(SpeechConstant.PARAMS, null)
        mTts!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        mTts!!.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1")
        if("zh" == language) {
            mTts!!.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan")
        }else{
            mTts!!.setParameter(SpeechConstant.VOICE_NAME, "vimary")
        }
        mTts!!.setParameter(SpeechConstant.SPEED, "50")
        mTts!!.setParameter(SpeechConstant.PITCH,"50")
        mTts!!.setParameter(SpeechConstant.VOLUME,"50")
        mTts!!.setParameter(SpeechConstant.STREAM_TYPE,"3")
        mTts!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false")
    }

    fun startSpeak(texts:String,language:String,listener: SynthesizerListener)
    {
        mTts?.startSpeaking(texts, listener)
    }

    fun destory()
    {
        mTts?.let {
            it.stopSpeaking()
            it.destroy()
        }
        mTts = null
    }
}