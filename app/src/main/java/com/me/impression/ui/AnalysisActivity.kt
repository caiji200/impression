package com.me.impression.ui

import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.vm.AnalysisViewModel

class AnalysisActivity : BaseActivity<AnalysisViewModel>() {

    override fun getLayoutId(): Int  = R.layout.activity_analysis

    override fun initView() {
        title = getString(R.string.title_analysis)
    }

    override fun setListener() {

    }
}