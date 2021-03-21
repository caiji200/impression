package com.me.impression.ui

import android.content.Intent
import android.os.Bundle
import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.common.Constants
import com.me.impression.entity.GuideBean
import com.me.impression.extensions.gone
import com.me.impression.extensions.visible
import com.me.impression.ui.adapter.GuideAdapter
import com.me.impression.utils.PreferencesUtils
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
        val firstLaunch = PreferencesUtils.getBoolean(this, Constants.PrefKey.FirstLaunch,true)
        if(firstLaunch){
            iconLayout.gone()
            banner.visible()
            goBtn.visible()
            banner.addBannerLifecycleObserver(this)
                .setAdapter(GuideAdapter(getGuideInfo()))
                .indicator = CircleIndicator(this)
        }else{
            banner.gone()
            goBtn.gone()
            iconLayout.visible()
            mViewModel.delayStart(1500) {
                goMain()
            }
        }

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
            PreferencesUtils.putBoolean(this, Constants.PrefKey.FirstLaunch,false)
            goMain()
        }
    }

    private fun goMain()
    {
        startActivity(Intent(this,MainActivity::class.java))
        closePage()
    }
}