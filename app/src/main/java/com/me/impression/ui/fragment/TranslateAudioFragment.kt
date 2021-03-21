package com.me.impression.ui.fragment

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.impression.R
import com.me.impression.base.BaseFragment
import com.me.impression.base.BaseNormalAdapter
import com.me.impression.base.ViewHolder
import com.me.impression.common.Constants
import com.me.impression.db.model.HistoryRecord
import com.me.impression.db.model.NoteRecord
import com.me.impression.ui.AudioRecognizeActivity
import com.me.impression.utils.PreferencesUtils
import com.me.impression.vm.NoteViewModel
import com.me.impression.vm.TranslateAudioViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_translate_audio.*
import kotlinx.android.synthetic.main.fragment_translate_audio.leftLangTv
import kotlinx.android.synthetic.main.fragment_translate_audio.rightLangTv
import kotlinx.android.synthetic.main.fragment_translate_audio.switchIv

class TranslateAudioFragment : BaseFragment<TranslateAudioViewModel>() {

    private var bZhToEn = true

    companion object {
        @JvmStatic
        fun newInstance() = TranslateAudioFragment().apply {}

        const val REQ_RECOGNIZE = 100
    }
    private lateinit var mAdapter:BaseNormalAdapter<HistoryRecord>

    override fun getLayoutId(): Int = R.layout.fragment_translate_audio


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            bZhToEn = PreferencesUtils.getBoolean(activity, Constants.PrefKey.TranslateDirection,true)
            if(bZhToEn){
                leftLangTv.text = getString(R.string.translate_zh)
                rightLangTv.text = getString(R.string.translate_en)
            }else{
                leftLangTv.text = getString(R.string.translate_en)
                rightLangTv.text = getString(R.string.translate_zh)
            }
        }
    }

    override fun initView() {

        mAdapter = object:BaseNormalAdapter<HistoryRecord>(R.layout.item_translate_record){
            override fun bindViewHolder(holder: ViewHolder, item: HistoryRecord, position: Int) {
                holder.setText(R.id.srcTv,item.srcText)
                holder.setText(R.id.destTv,item.destText)
            }
        }

        recordRv.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            adapter = mAdapter
        }
    }

    override fun setListener() {
        audioBtn.setOnClickListener {
            val rxPermissions = RxPermissions(activity!!)
            rxPermissions.request(android.Manifest.permission.RECORD_AUDIO)
                .subscribe {
                    if(it){
                       val intent = Intent(activity,AudioRecognizeActivity::class.java)
                       intent.putExtra("type",bZhToEn)
                       startActivityForResult(intent,REQ_RECOGNIZE)
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
            PreferencesUtils.putBoolean(activity,Constants.PrefKey.TranslateDirection,bZhToEn)
        }

        mViewModel.mHistoryRecord.observe(this, Observer {
            mAdapter.setData(it)
        })
    }

}