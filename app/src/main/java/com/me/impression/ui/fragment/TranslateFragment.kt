package com.me.impression.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.me.impression.R
import com.me.impression.base.BaseFragment
import com.me.impression.base.BaseNormalAdapter
import com.me.impression.base.ViewHolder
import com.me.impression.db.model.HistoryRecord
import com.me.impression.extensions.gone
import com.me.impression.extensions.hideSoftInput
import com.me.impression.extensions.setVisible
import com.me.impression.extensions.visible
import com.me.impression.interfaces.TextWatcherAdapter
import com.me.impression.ui.TranslateHistoryActivity
import com.me.impression.vm.TranslateViewModel
import kotlinx.android.synthetic.main.fragment_translate.*

class TranslateFragment : BaseFragment<TranslateViewModel>() {

    private var bZhToEn = true
    private lateinit var mHistoryAdapter: BaseNormalAdapter<HistoryRecord>

    companion object {
        @JvmStatic
        fun newInstance() = TranslateFragment().apply {}
    }

    override fun getLayoutId(): Int = R.layout.fragment_translate

    override fun initView() {
        resultTv.movementMethod = ScrollingMovementMethod.getInstance()
        resultLayout.gone()
        confirmBtn.gone()
        clearIv.gone()

        historyRv.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            mHistoryAdapter = object:BaseNormalAdapter<HistoryRecord>(R.layout.item_history_translate){
                override fun bindViewHolder(
                    holder: ViewHolder,
                    item: HistoryRecord,
                    position: Int
                ) {
                    holder.setText(R.id.srcTv,item.srcText)
                    holder.setText(R.id.destTv,item.destText)
                }
            }
            adapter = mHistoryAdapter
        }

        mViewModel.getHistoryData()
    }

    override fun setListener() {
        queryEt.addTextChangedListener(object: TextWatcherAdapter(){
            override fun afterTextChanged(editable: Editable?) {
                super.afterTextChanged(editable)
                val bEmpty = TextUtils.isEmpty(queryEt.text.toString())
                confirmBtn.setVisible(!bEmpty)
                clearIv.setVisible(!bEmpty)
            }
        })

        queryEt.setOnFocusChangeListener { _, b ->
            if(b) {
                if (queryEt.text.isNotEmpty()) {
                    clearIv.visible()
                } else {
                    clearIv.gone()
                }
            } else {
                clearIv.gone()
            }
        }

        clearIv.setOnClickListener {
            queryEt.setText("")
            resultLayout.gone()
            historyLayout.setVisible(!mViewModel.mHistoryRecord.value.isNullOrEmpty())
            mViewModel.getHistoryData()
        }

        confirmBtn.setOnClickListener {
            val q = queryEt.text.toString()
            hideSoftInput()
            if (bZhToEn) {
                mViewModel.translate(q, "zh", "en")
            } else {
                mViewModel.translate(q, "en", "zh")
            }
        }

        mViewModel.mResult.observe(this, Observer {
            resultTv.text = it
            resultLayout.visible()
            historyLayout.gone()
        })

        copyIv.setOnClickListener {
            val cm = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newPlainText("translate", resultTv.text.toString())
            cm.setPrimaryClip(mClipData)
            ToastUtils.showShort(R.string.already_copy)
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

        mHistoryAdapter.itemClickListener = { _,record,_ ->
            mViewModel.translate(record.srcText,record.from,record.to,false)
        }

        mViewModel.mHistoryRecord.observe(this, Observer {
            historyLayout.setVisible(it.isNotEmpty())
            mHistoryAdapter.setData(it)
        })

        moreLayout.setOnClickListener {
            startActivity(Intent(activity,TranslateHistoryActivity::class.java))
        }
    }
}