package com.me.impression.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.me.impression.ui.dialog.WaitingDialog
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), IBaseView {

    private var fragmentRootView: View? = null
    lateinit var mViewModel: VM
    private var isCreated = false
    private var isVisibleToUser = false
    private var waitingDialog : WaitingDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRootView = inflater.inflate(getLayoutId(), container, false)
        return fragmentRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        lifecycle.addObserver(mViewModel)
        inject()
        initView()
        setListener()
        registerUIChangeLiveData()
        loadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            this.isVisibleToUser = true
            lazyLoad()
        }
    }

    private fun loadData() {
        if (isLazy) {
            isCreated = true
            lazyLoad()
        } else {
            initData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(mViewModel)
    }

    private fun lazyLoad() {
        if (isCreated && isVisibleToUser) {
            initData()
            isCreated = false
        }
    }

    private fun initViewModel() {
        val modelClass: Class<BaseViewModel>?
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<BaseViewModel>
        } else {
            BaseViewModel::class.java
        }
        mViewModel = createViewModel<BaseViewModel>(activity, modelClass) as VM
    }

    private fun <T : BaseViewModel> createViewModel(activity: FragmentActivity?, cls: Class<T>): T {
        return ViewModelProviders.of(activity!!)[cls]
    }

    @LayoutRes abstract fun getLayoutId(): Int
    abstract fun initView()
    open fun initData() {}
    open fun inject() {}
    abstract fun setListener()

    override fun closeLoading() {
        if (waitingDialog != null) {
            waitingDialog?.dismiss()
        }
    }

    override fun showLoading(@StringRes msgRes: Int) {
        val isShowing = waitingDialog?.isShowing ?: false
        if (waitingDialog == null) {
            waitingDialog = WaitingDialog(activity, activity?.resources?.getString(msgRes))
        }

        if(!isShowing){
            waitingDialog?.setCancelable(false)
            waitingDialog?.show()
        }
    }

    override fun setLoadingMessage(message: String?) {
        waitingDialog?.setMessage(message)
    }

    override fun closePage() {
        activity?.finish()
    }

    open val isLazy: Boolean get() = true

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
}