package com.me.impression.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.me.impression.R
import com.me.impression.base.BaseFragment
import com.me.impression.vm.NoteViewModel

class NoteFragment : BaseFragment<NoteViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment().apply {}
    }

    override fun getLayoutId(): Int = R.layout.fragment_note

    override fun initView() {

    }

    override fun setListener() {

    }
}