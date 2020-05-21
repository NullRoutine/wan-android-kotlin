package com.nullroutine.wan.ui.mine

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.model.bean.UserInfo
import com.nullroutine.wan.ui.base.BaseViewModel

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/20 15:23
 *
 */
class MineViewModel : BaseViewModel() {
    val userInfo = MutableLiveData<UserInfo?>()
    val isLogin = MutableLiveData<Boolean>()

    fun getUserInfo() {
        isLogin.value = userRepository.isLogin()
        userInfo.value = userRepository.getUserInfo()
    }
}