package com.me.impression.ui

import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.base.BaseNormalAdapter
import com.me.impression.base.ViewHolder
import com.me.impression.db.model.NoteRecord
import com.me.impression.vm.NoteBookViewModel
import kotlinx.android.synthetic.main.activity_note_book.*

/**
 * @author:Jie Cai
 * @desc: NoteBook Page
 * */
class NoteBookActivity : BaseActivity<NoteBookViewModel>() {

    private lateinit var mAdapter: BaseNormalAdapter<NoteRecord>

    override fun getLayoutId(): Int  = R.layout.activity_note_book

    override fun initView() {
        title = getString(R.string.note_book)

        val itemDividerDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        itemDividerDecoration.setDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gray_e0)))

        noteRv.addItemDecoration(itemDividerDecoration)
        noteRv.apply {
            layoutManager = LinearLayoutManager(this@NoteBookActivity,LinearLayoutManager.VERTICAL,
                    false)
            mAdapter = object: BaseNormalAdapter<NoteRecord>(R.layout.item_note_book){
                override fun bindViewHolder(
                        holder: ViewHolder,
                        item: NoteRecord,
                        position: Int
                ) {
                    holder.setText(R.id.srcTv,item.srcText)
                    holder.setText(R.id.destTv,item.destText)
                }
            }
            adapter = mAdapter
        }
    }

    override fun setListener() {

        mViewModel.mNotes.observe(this, Observer {
            mAdapter.setData(it)
        })

    }
}