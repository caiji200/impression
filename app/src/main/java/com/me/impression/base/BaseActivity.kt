package com.me.impression.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.me.impression.R
import com.me.impression.ui.dialog.WaitingDialog
import java.lang.reflect.ParameterizedType

/**
 * MVVM Base Activity
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), IBaseView {
    lateinit var mViewModel: VM
    private var toolbarLayout: ViewGroup? = null
    private var frameTitleTv: TextView? = null
    private var frameBackView: ImageView? = null
    private var frameLineView: View? = null
    private var waitingDialog : WaitingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        super.onCreate(savedInstanceState)
        inject()
        val layoutId = getLayoutId()
        if (layoutId == 0) {
            setContentView()
        } else if (!showToolbar()) {
            setContentView(layoutId)
        } else {
            setContentView(R.layout.activity_base)
            val root = findViewById<ViewGroup>(R.id.activityRootView)
            View.inflate(this, layoutId, root)
        }
        toolbarLayout = findViewById(R.id.toolbarLayout)
        frameBackView = findViewById(R.id.toolbarBack)
        frameTitleTv = findViewById(R.id.toolbarTitle)
        frameLineView = findViewById(R.id.toolbarLine)
        if (frameBackView != null) {
            frameBackView!!.setOnClickListener { v: View? -> onBackPressed() }
        }
        if (isFixOri) {
            fixedOrientation()
        }
        setBackIcon(R.drawable.ic_arrow_back_white)
        setTitleColorRes(R.color.white)
        initViewModel()
        mViewModel!!.pageIntent = intent
        lifecycle.addObserver(mViewModel!!)
        setListener()
        initView()
        registerUIChangeLiveData()
        initData()
    }

    override fun setTitle(title: CharSequence) {
        if (frameTitleTv != null) {
            frameTitleTv!!.text = title.toString()
        }
    }

    override fun setTitle(titleId: Int) {
        if (frameTitleTv != null) {
            frameTitleTv!!.setText(titleId)
        }
    }

    private fun fixedOrientation() {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O && Build.VERSION.SDK_INT != Build.VERSION_CODES.O_MR1) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    open fun setTitleColorRes(@ColorRes colorRes: Int) {
        if (frameTitleTv != null) {
            frameTitleTv!!.setTextColor(resources.getColor(colorRes))
        }
    }

    fun showDownLine(bShow: Boolean) {
        if (frameLineView != null) {
            if (bShow) {
                frameLineView!!.visibility = View.VISIBLE
            } else {
                frameLineView!!.visibility = View.GONE
            }
        }
    }

    fun setToolbarBackGroundColor(@ColorRes color: Int) {
        if (toolbarLayout != null) {
            toolbarLayout!!.setBackgroundColor(resources.getColor(color))
        }
    }

    open fun setBackIcon(@DrawableRes iconRes: Int) {
        if (frameBackView != null) {
            frameBackView!!.setImageResource(iconRes)
        }
    }

    open fun showBack(bShow: Boolean) {
        if (frameBackView != null) {
            if (bShow) {
                frameBackView!!.visibility = View.VISIBLE
            } else {
                frameBackView!!.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel!!)
    }

    private fun initViewModel() {
        val modelClass: Class<BaseViewModel>?
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<BaseViewModel>
        } else {
            BaseViewModel::class.java
        }
        mViewModel = createViewModel<BaseViewModel>(this, modelClass) as VM
    }


    @LayoutRes abstract fun getLayoutId(): Int

    abstract fun initView()
    open fun initData() {}
    open fun inject() {}
    abstract fun setListener()
    open fun setContentView() {}

    override fun closeLoading() {
        if (waitingDialog != null) {
            waitingDialog?.dismiss()
        }
    }

    override fun showLoading(@StringRes msgRes: Int) {
        val isShowing = waitingDialog?.isShowing ?: false
        if (waitingDialog == null) {
            waitingDialog = WaitingDialog(this, resources.getString(msgRes))
        }

        if(!isShowing){
            waitingDialog?.setCancelable(false)
            waitingDialog?.show()
        }
    }

    override fun setLoadingMessage(message: String?) {
        waitingDialog?.setMessage(message)
    }


    open fun showToolbar(): Boolean {
        return true
    }

    open val isFullScreen: Boolean get() = false

    open val isFixOri: Boolean get() = true


    protected val isDarkTheme: Boolean get() = true

    private fun <T : BaseViewModel> createViewModel(activity: FragmentActivity?, cls: Class<T>): T {
        return ViewModelProviders.of(activity!!)[cls]
    }

    private fun registerUIChangeLiveData() {
        mViewModel.baseViewLiveData.showDialogEvent!!.observe(
            this,
            Observer { titleRes -> showLoading(titleRes!!) })
        mViewModel.baseViewLiveData.dismissDialogEvent!!.observe(
            this,
            Observer { closeLoading() })
        mViewModel.baseViewLiveData.finishEvent!!.observe(
            this,
            Observer { closePage() })
        mViewModel.baseViewLiveData.setMessageEvent!!.observe(
            this,
            Observer { s -> setLoadingMessage(s) })
    }

    override fun closePage() {
        finish()
    }

    fun hideSystemNavigationBar() {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.decorView.systemUiVisibility = uiOptions
    }
}