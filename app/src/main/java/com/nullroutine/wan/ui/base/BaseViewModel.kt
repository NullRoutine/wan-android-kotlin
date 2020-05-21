package com.nullroutine.wan.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.nullroutine.wan.BaseApp
import com.nullroutine.wan.R
import com.nullroutine.wan.http.ApiException
import com.nullroutine.wan.ui.common.UserRepository
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED
import com.nullroutine.wan.util.showToast
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.SocketTimeoutException

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/27 15:36
 *
 */
open class BaseViewModel : ViewModel() {

    protected val userRepository by lazy { UserRepository() }

    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @return Job
     */
    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        Log.e("TAG", loginStatusInvalid.value.toString())
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     */
    private fun onError(e: Exception) {
        Log.e("TAG", e.toString())
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效
                        userRepository.clearLoginState()
                        Bus.post(USER_LOGIN_STATE_CHANGED, false)
                        loginStatusInvalid.value = true
                    }
                    -1 -> {
                        // 其他api错误
                        BaseApp.instance.showToast(e.message)
                    }
                    else -> {
                        // 其他错误
                        BaseApp.instance.showToast(e.message)
                    }
                }
            }
            is ConnectException -> {
                // 连接失败
                BaseApp.instance.showToast(BaseApp.instance.getString(R.string.network_connection_failed))
            }
            is SocketTimeoutException -> {
                // 请求超时
                BaseApp.instance.showToast(BaseApp.instance.getString(R.string.network_request_timeout))
            }
            is JsonParseException -> {
                // 数据解析错误
                BaseApp.instance.showToast(BaseApp.instance.getString(R.string.api_data_parse_error))
            }
            else -> {
                // 其他错误
                e.message?.let { BaseApp.instance.showToast(it) }
            }
        }
    }

    /**
     * 登录状态
     */
    fun loginStatus() = userRepository.isLogin()
}