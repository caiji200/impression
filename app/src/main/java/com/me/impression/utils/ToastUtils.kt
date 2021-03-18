package com.me.impression.utils

import android.R
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.me.impression.base.BaseApplication.Companion.getAppContext
import com.me.impression.base.BaseApplication.Companion.instance

class ToastUtils private constructor() {
    companion object {
        private var sToast: Toast? = null
        private val sHandler = Handler(Looper.getMainLooper())
        private const val isJumpWhenMore = false
        fun init(isJumpWhenMore: Boolean) {
            var isJumpWhenMore = isJumpWhenMore
            isJumpWhenMore = isJumpWhenMore
        }

        fun showShort(text: CharSequence) {
            showToast(text, Toast.LENGTH_SHORT)
        }

        fun showShort(@StringRes resId: Int) {
            showToast(resId, Toast.LENGTH_SHORT)
        }

        fun showLong(text: CharSequence) {
            showToast(text, Toast.LENGTH_LONG)
        }

        fun showLong(@StringRes resId: Int) {
            showToast(resId, Toast.LENGTH_LONG)
        }

        private fun showToast(@StringRes resId: Int, duration: Int) {
            showToast(
                getAppContext().resources.getText(resId).toString(),
                duration
            )
        }

        private fun showToast(
            @StringRes resId: Int,
            duration: Int,
            vararg args: Any
        ) {
            showToast(
                String.format(
                    getAppContext().resources.getString(resId), *args
                ), duration
            )
        }

        private fun showToast(
            format: String,
            duration: Int,
            vararg args: Any
        ) {
            showToast(String.format(format, *args), duration)
        }

        private fun showToast(text: CharSequence, duration: Int) {
            if (isJumpWhenMore) cancelToast()
            if (sToast == null) {
                sToast = Toast.makeText(instance, null, duration)
                val tv = sToast!!.getView()
                    .findViewById<TextView>(R.id.message)
                sToast!!.setText(text)
                tv.textSize = 18f
                sToast!!.setGravity(Gravity.BOTTOM, 0, 80)
            } else {
                sToast!!.setText(text)
                sToast!!.duration = duration
            }
            sToast!!.show()
        }

        fun cancelToast() {
            if (sToast != null) {
                sToast!!.cancel()
                sToast = null
            }
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}