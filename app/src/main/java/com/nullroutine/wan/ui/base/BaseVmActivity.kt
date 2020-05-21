package com.nullroutine.wan.ui.base

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nullroutine.wan.ui.login.LoginActivity
import com.nullroutine.wan.util.ActivityManager
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/11 15:28
 *
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
        observe()
        // 因为Activity恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        if (savedInstanceState == null) {
            initData()
        }
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    protected abstract fun viewModelClass(): Class<VM>

    open fun initView() {
        // Override if need
    }

    open fun observe() {
        Log.e("TAG", "observe")
        // 登录失效，跳转登录页
        mViewModel.loginStatusInvalid.observe(this, Observer {
            if (it) {
                Bus.post(USER_LOGIN_STATE_CHANGED, false)
                ActivityManager.startActivity(LoginActivity::class.java)
            }
        })
    }

    open fun initData() {
        // Override if need
    }

    /**
     * 是否登录，如果登录了就执行then，没有登录就直接跳转登录界面
     * @return true-已登录，false-未登录
     */
    fun checkLogin(then: (() -> Unit)? = null): Boolean {
        return if (mViewModel.loginStatus()) {
            then?.invoke()
            true
        } else {
            ActivityManager.startActivity(LoginActivity::class.java)
            false
        }
    }
}