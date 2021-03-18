package com.me.impression.ui.fragment

import android.content.Intent
import androidx.lifecycle.Observer
import com.me.impression.R
import com.me.impression.base.BaseFragment
import com.me.impression.common.AppConfig
import com.me.impression.common.Constants
import com.me.impression.ui.AnalysisActivity
import com.me.impression.ui.NoteBookActivity
import com.me.impression.ui.ReviewActivity
import com.me.impression.utils.PreferencesUtils
import com.me.impression.vm.NoteViewModel
import kotlinx.android.synthetic.main.fragment_overview.*

class OverViewFragment : BaseFragment<NoteViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = OverViewFragment().apply {}
    }

    override fun getLayoutId(): Int = R.layout.fragment_overview

    override fun initView() {
        titleTv.text = getString(R.string.title_overview)
        val targetCount = PreferencesUtils.getInt(activity,Constants.PrefKey.TargetCount,
            AppConfig.DEFAULT_TARGET_COUNT)
        targetCountTv.text = targetCount.toString()

        val reviewDaysCount = PreferencesUtils.getInt(activity,Constants.PrefKey.ReviewDayCount,0)
        reviewDayCountTv.text = reviewDaysCount.toString()
    }

    override fun setListener() {

        notebookLayout.setOnClickListener {
            startActivity(Intent(activity,NoteBookActivity::class.java))
        }

        analysisIv.setOnClickListener {
            startActivity(Intent(activity,AnalysisActivity::class.java))
        }

        studyBtn.setOnClickListener {
            startActivity(Intent(activity,ReviewActivity::class.java))
        }

        mViewModel.mAnalysisRecord.observe(this, Observer {
            it?.let {
                reviewCountTv.text = it.count.toString()
            }

        })

        mViewModel.mNoteCount.observe(this, Observer {
            noteCountTv.text = it.toString()
        })
    }
}