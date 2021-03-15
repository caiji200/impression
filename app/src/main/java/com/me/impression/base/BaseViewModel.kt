package com.me.impression.base

import android.app.Application
import android.content.Intent
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.me.impression.exception.ApiException
import com.me.impression.repository.RepositoryManager
import com.me.impression.utils.RxTools
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

abstract class BaseViewModel(application: Application) :
    AndroidViewModel(application), IBaseViewModel, IBaseView {
    private var mCompositeDisposable: CompositeDisposable? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var mBaseView: BaseViewLiveData? = null
    var pageIntent: Intent? = null

    var mDefaultApiException: MutableLiveData<ApiException> = MutableLiveData()

    val mRepoManager: RepositoryManager = RepositoryManager

    fun<T> apiCall(observable: Observable<T>, @StringRes tipsRes:Int=0,
                   onSuccess:(T)->Unit={}, onFailed:(ApiException)->Boolean={false}, onComplete:()->Unit={}) {
        val d = observable.compose(RxTools.oIoMain())
            .doOnSubscribe {
                if(tipsRes != 0){
                    showLoading(tipsRes)
                }
            } .doFinally {
                if (tipsRes != 0) {
                    closeLoading()
                }
                onComplete()
            } .subscribe ({
                onSuccess(it)
            },{
                if (it is ApiException) {
                    if (!onFailed(it)) {
                        mDefaultApiException.value = it
                    }
                }
            })
        addDisposable(d)
    }

    fun delayStart(delay: Long = 0L, callback: () -> Unit) {
        addDisposable(Observable
            .timer(delay, TimeUnit.MILLISECONDS)
            .compose(RxTools.oIoMain())
            .doOnNext { callback.invoke() }
            .subscribe())
    }

    fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable!!)
    }

    override fun onCreate(owner: LifecycleOwner) {
        lifecycleOwner = owner
    }

    override fun onDestroy(owner: LifecycleOwner) {}

    override fun onStart() {}

    override fun onStop() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onCleared() {
        super.onCleared()
        if (mCompositeDisposable != null && !mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable!!.clear()
        }
    }

    val baseViewLiveData: BaseViewLiveData
        get() {
            if (mBaseView == null) {
                mBaseView = BaseViewLiveData()
            }
            return mBaseView!!
        }

    override fun showLoading(msgRes: Int) {
        baseViewLiveData.showLoading(msgRes)
    }

    override fun closeLoading() {
        baseViewLiveData.closeLoading()
    }

    override fun closePage() {
        baseViewLiveData.closePage()
    }

    override fun setLoadingMessage(message: String?) {
        baseViewLiveData.setLoadingMessage(message)
    }
}