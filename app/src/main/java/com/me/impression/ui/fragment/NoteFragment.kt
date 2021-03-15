package com.me.impression.ui.fragment

import com.me.impression.R
import com.me.impression.base.BaseFragment
import com.me.impression.vm.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : BaseFragment<NoteViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment().apply {}
    }

    override fun getLayoutId(): Int = R.layout.fragment_note

    override fun initView() {
        titleTv.text = getString(R.string.note_book)
    }

    override fun setListener() {

    }
}