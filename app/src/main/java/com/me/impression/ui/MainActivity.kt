package com.me.impression.ui

import com.me.impression.R
import com.me.impression.base.BaseActivity
import com.me.impression.ui.adapter.FragmentAdapter
import com.me.impression.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var mAdapter: FragmentAdapter

    override fun getLayoutId(): Int  = R.layout.activity_main

    override fun showToolbar(): Boolean = false

    override fun initView() {
        mAdapter = FragmentAdapter(supportFragmentManager)
        viewPager.setPagerEnabled(false)
        viewPager.adapter = mAdapter
        viewPager.offscreenPageLimit = 3
    }

    override fun setListener() {
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    viewPager.currentItem = 0
                }
                R.id.navigation_audio -> {
                    viewPager.currentItem = 1
                }
                R.id.navigation_overview -> {
                    viewPager.currentItem = 2
                }
            }
            true
        }
    }

}