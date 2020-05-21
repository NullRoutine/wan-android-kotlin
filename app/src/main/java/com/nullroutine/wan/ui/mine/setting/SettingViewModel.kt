package com.nullroutine.wan.ui.mine.setting

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.ui.base.BaseViewModel
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/20 16:42
 *
 */
class SettingViewModel : BaseViewModel() {

    val isLogin = MutableLiveData<Boolean>()

    fun getLoginStatus() {
        isLogin.value = userRepository.isLogin()
    }

    fun logout() {
        userRepository.clearLoginState()
        Bus.post(USER_LOGIN_STATE_CHANGED, false)
    }
}