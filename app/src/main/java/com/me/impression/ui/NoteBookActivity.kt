package com.me.impression.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.widget.CheckBox
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
import com.me.impression.extensions.gone
import com.me.impression.extensions.setVisible
import com.me.impression.extensions.visible
import com.me.impression.utils.ToastUtils
import com.me.impression.vm.NoteBookViewModel
import kotlinx.android.synthetic.main.activity_note_book.*

/**
 * @author:Jie Cai
 * @desc: NoteBook Page
 * */
class NoteBookActivity : BaseActivity<NoteBookViewModel>() {

    private lateinit var mAdapter: BaseNormalAdapter<NoteRecord>

    private var bSelectMode = false

    override fun getLayoutId(): Int  = R.layout.activity_note_book

    override fun showToolbar(): Boolean  = false

    override fun initView() {
        toolbarTitle.text = getString(R.string.note_book)
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
                    val checkCb = holder.getView<CheckBox>(R.id.checkCb)
                    checkCb.setVisible(bSelectMode)
                    if(bSelectMode){
                        checkCb.isChecked = mViewModel.isSelected(item)
                    }
                }
            }

            mAdapter.itemClickListener = { _,data,_ ->
                mViewModel.selectOrUnSelect(data)
                mAdapter.notifyDataSetChanged()
            }

            adapter = mAdapter
        }
        deleteBtn.gone()
    }

    override fun setListener() {

        toolbarBack.setOnClickListener {
            closePage()
        }

        mViewModel.mNotes.observe(this, Observer {
            mAdapter.setData(it)
        })

        deleteBtn.setOnClickListener {
            if(mViewModel.getSelectItems().isNullOrEmpty()){
                ToastUtils.showShort(R.string.please_choose_items)
                return@setOnClickListener
            }
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.prompt)
            builder.setMessage(R.string.delete_note_tips)
            builder.setPositiveButton(R.string.btn_confirm) { dialog, _ ->
                dialog.dismiss()
                mViewModel.deleteTask()
            }
            builder.setNegativeButton(R.string.btn_cancel){ dialog,_->
                dialog.dismiss()
            }
            builder.setCancelable(true)
            builder.create().show()
        }

        editTv.setOnClickListener {
            switchEditMode()
        }

        mViewModel.mTaskOver.observe(this, Observer {
            switchEditMode()
        })
    }

    private fun switchEditMode()
    {
        bSelectMode = !bSelectMode
        if(bSelectMode){
            mViewModel.clearSelect()
            editTv.text = getString(R.string.cancel)
        }else{
            editTv.text = getString(R.string.edit)
        }
        deleteBtn.setVisible(bSelectMode)
        mAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if(bSelectMode){
            switchEditMode()
        }else {
            super.onBackPressed()
        }
    }
}