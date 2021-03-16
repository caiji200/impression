package com.me.impression.ui

import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.vm.ReviewViewModel

class ReviewActivity : BaseActivity<ReviewViewModel>() {

    override fun getLayoutId(): Int  = R.layout.activity_review

    override fun initView() {
        title = getString(R.string.title_review)
    }

    override fun setListener() {

    }
}