package com.nullroutine.wan.ui.login

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.ui.base.BaseViewModel
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/11 15:37
 *
 */
class LoginViewModel : BaseViewModel() {
    private val loginRepository by lazy { LoginRepository() }
    val submitting = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()

    fun login(account: String, password: String) {
        submitting.value = true
        launch(
            block = {
                val userInfo = loginRepository.login(account, password)
                userRepository.updateUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                submitting.value = false
                loginResult.value = false
            }
        )
    }
}