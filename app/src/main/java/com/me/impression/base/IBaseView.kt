package com.me.impression.base

import androidx.annotation.StringRes


interface IBaseView {
    fun showLoading(@StringRes msgRes: Int)
    fun closeLoading()
    fun setLoadingMessage(message: String?)
    fun closePage()
}