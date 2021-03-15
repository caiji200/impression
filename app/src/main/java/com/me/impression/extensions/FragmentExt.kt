package com.me.impression.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.me.impression.extensions.hideSoftInput
import com.me.impression.extensions.showSoftInput


fun Fragment.showSoftInput(view: View) {
    activity?.showSoftInput(view)
}

fun Fragment.hideSoftInput() {
    activity?.hideSoftInput()
}

fun Fragment.getString(@StringRes id:Int):String {
    return resources.getString(id)
}

fun Fragment.getColor(@ColorRes resId:Int):Int {
    return ContextCompat.getColor(activity!!,resId)
}

