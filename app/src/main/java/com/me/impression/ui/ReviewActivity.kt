package com.me.impression.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.SynthesizerListener
import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.common.AppConfig
import com.me.impression.common.Constants
import com.me.impression.extensions.gone
import com.me.impression.extensions.invisible
import com.me.impression.extensions.visible
import com.me.impression.utils.L
import com.me.impression.utils.PreferencesUtils
import com.me.impression.utils.TtsHelper
import com.me.impression.vm.ReviewViewModel
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : BaseActivity<ReviewViewModel>() {

    override fun getLayoutId(): Int  = R.layout.activity_review

    override fun initView() {
        title = getString(R.string.title_review)
        TtsHelper.init(this)
    }

    override fun setListener() {

        mViewModel.mCurNote.observe(this, Observer {
            srcTextTv.text = it.srcText
            destTextTv.text = it.destText
        })

        nextBtn.setOnClickListener {
            if (mViewModel.isOver()) {
                mViewModel.finishFlow()
            } else {
                maskTv.visible()
                val result = mViewModel.showNext()
                if (result) {
                    nextBtn.text = resources.getString(R.string.btn_finish)
                }
            }
        }

        maskTv.setOnClickListener {
            maskTv.gone()
        }

        audioBtn.setOnClickListener {
            lottieView.visible()
            audioBtn.invisible()
            startSpeak()
        }

        lottieView.invisible()
    }

    private fun startSpeak()
    {
        val language = mViewModel.mCurNote?.value?.to ?: ""
        TtsHelper.setParam(language)
        TtsHelper.startSpeak(destTextTv.text.toString(),language,object:SynthesizerListener{
            override fun onBufferProgress(p0: Int, p1: Int, p2: Int, p3: String?) {

            }

            override fun onSpeakBegin() {
                L.d("tts","onSpeakBegin")
            }

            override fun onSpeakProgress(percent: Int, startPos: Int, endPos: Int) {
                L.d("tts","onSpeakProgress")
            }

            override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {

            }

            override fun onSpeakPaused() {

            }

            override fun onSpeakResumed() {

            }

            override fun onCompleted(p0: SpeechError?) {
                L.d("tts","onCompleted")
                lottieView.invisible()
                audioBtn.visible()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        TtsHelper.destory()
    }

}