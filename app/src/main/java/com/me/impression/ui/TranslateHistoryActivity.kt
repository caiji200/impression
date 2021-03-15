package com.me.impression.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.base.BaseNormalAdapter
import com.me.impression.base.ViewHolder
import com.me.impression.db.model.HistoryRecord
import com.me.impression.vm.TranslateHistoryViewModel
import kotlinx.android.synthetic.main.activity_translate_history.*

class TranslateHistoryActivity : BaseActivity<TranslateHistoryViewModel>() {

    private lateinit var mHistoryAdapter: BaseNormalAdapter<HistoryRecord>

    override fun getLayoutId(): Int  = R.layout.activity_translate_history

    override fun initView() {
        title = "History"
        historyRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            mHistoryAdapter = object: BaseNormalAdapter<HistoryRecord>(R.layout.item_history_translate){
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
    }

    override fun setListener() {
        mViewModel.mHistoryRecord.observe(this, Observer {
            mHistoryAdapter.setData(it)
        })
    }
}