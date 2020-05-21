package com.nullroutine.wan.ui.common

import com.nullroutine.wan.http.RetrofitClient
import com.nullroutine.wan.model.bean.UserInfo
import com.nullroutine.wan.model.store.UserInfoStore

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/20 14:27
 *
 */
open class UserRepository {
    fun updateUserInfo(userInfo: UserInfo) = UserInfoStore.setUserInfo(userInfo)

    fun isLogin() = UserInfoStore.isLogin()

    fun getUserInfo() = UserInfoStore.getUserInfo()

    fun clearLoginState() {
        UserInfoStore.clearUserInfo()
        RetrofitClient.clearCookie()
    }
}