package com.me.impression.ui.fragment

import android.content.Intent
import com.me.impression.R
import com.me.impression.base.BaseFragment
import com.me.impression.ui.AudioRecognizeActivity
import com.me.impression.vm.NoteViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_translate_audio.*
import kotlinx.android.synthetic.main.fragment_translate_audio.leftLangTv
import kotlinx.android.synthetic.main.fragment_translate_audio.rightLangTv
import kotlinx.android.synthetic.main.fragment_translate_audio.switchIv


class TranslateAudioFragment : BaseFragment<NoteViewModel>() {

    private var bZhToEn = true

    companion object {
        @JvmStatic
        fun newInstance() = TranslateAudioFragment().apply {}
    }

    override fun getLayoutId(): Int = R.layout.fragment_translate_audio

    override fun initView() {

    }

    override fun setListener() {
        audioBtn.setOnClickListener {
            val rxPermissions = RxPermissions(activity!!)
            rxPermissions.request(android.Manifest.permission.RECORD_AUDIO)
                .subscribe {
                    if(it){
                       val intent = Intent(activity,AudioRecognizeActivity::class.java)
                       intent.putExtra("type",bZhToEn)
                       startActivityForResult(intent,1)
                    }
                }
        }

        switchIv.setOnClickListener {
            if(bZhToEn){
                leftLangTv.text = getString(R.string.translate_en)
                rightLangTv.text = getString(R.string.translate_zh)
            }else{
                leftLangTv.text = getString(R.string.translate_zh)
                rightLangTv.text = getString(R.string.translate_en)
            }
            bZhToEn = !bZhToEn
        }
    }
}