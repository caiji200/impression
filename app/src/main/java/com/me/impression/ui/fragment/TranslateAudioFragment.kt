package com.me.impression.ui.fragment

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.impression.R
import com.me.impression.base.BaseFragment
import com.me.impression.base.BaseNormalAdapter
import com.me.impression.base.ViewHolder
import com.me.impression.db.model.NoteRecord
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

        const val REQ_RECOGNIZE = 100
    }
    private lateinit var mAdapter:BaseNormalAdapter<NoteRecord>

    override fun getLayoutId(): Int = R.layout.fragment_translate_audio

    override fun initView() {
        mAdapter = object:BaseNormalAdapter<NoteRecord>(R.layout.item_translate_record){
            override fun bindViewHolder(holder: ViewHolder, item: NoteRecord, position: Int) {
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
        }

        saveBtn.setOnClickListener {
            mViewModel.saveToNoteBook()
        }

        mViewModel.mRecords.observe(this, Observer {
            mAdapter.setData(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_RECOGNIZE && resultCode == Activity.RESULT_OK){
            data?.let {
                val src = data.getStringExtra("src")
                val dest = data.getStringExtra("dest")
                var from = "zh"
                var to = "en"
                if(!bZhToEn){
                    from = "en"
                    to = "zh"
                }
                mViewModel.addToRecord(from,to,src,dest)
            }
        }
    }
}