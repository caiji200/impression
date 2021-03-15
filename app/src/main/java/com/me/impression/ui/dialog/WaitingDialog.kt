package com.me.impression.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.me.impression.R
import kotlinx.android.synthetic.main.dialog_waiting.*

class WaitingDialog(context: Context?, private val mMsg: String?) :

    Dialog(context!!, R.style.waitingDialogStyle) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_waiting)
        msgTv.text = mMsg
        val linearLayout = findViewById<LinearLayout>(R.id.main_layout)
        linearLayout.background.alpha = 210
        setCanceledOnTouchOutside(false)
    }

    fun setMessage(msg:String?)
    {
        msgTv.text = msg
    }
}