package com.me.impression.ui

import android.content.Intent
import android.os.Bundle
import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.entity.GuideBean
import com.me.impression.ui.adapter.GuideAdapter
import com.me.impression.vm.SplashViewModel
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * @author Jie Cai
 * @desc welcome page
 * */
class WelcomeActivity : BaseActivity<SplashViewModel>() {

    override fun getLayoutId(): Int  = R.layout.activity_welcome

    override fun showToolbar(): Boolean  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemNavigationBar()
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        banner.addBannerLifecycleObserver(this)
            .setAdapter(GuideAdapter(getGuideInfo()))
            .indicator = CircleIndicator(this)
    }

    private fun getGuideInfo():List<GuideBean>
    {
        val result = ArrayList<GuideBean>()
        result.add(GuideBean(R.raw.programming,"translate words or sentences"))
        result.add(GuideBean(R.raw.splash,"record your study process"))
        result.add(GuideBean(R.raw.phone,"analysis study result"))
        return result
    }

    override fun setListener() {
        goBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            closePage()
        }
    }

}