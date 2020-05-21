package com.nullroutine.wan.model.store

import com.google.gson.Gson
import com.nullroutine.wan.BaseApp
import com.nullroutine.wan.model.bean.UserInfo
import com.nullroutine.wan.util.clearSpValue
import com.nullroutine.wan.util.getSpValue
import com.nullroutine.wan.util.putSpValue

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/20 14:30
 *
 */
object UserInfoStore {
    private const val SP_USER_INFO = "sp_user_info"
    private const val KEY_USER_INFO = "userInfo"
    private val mGson by lazy { Gson() }

    fun isLogin(): Boolean {
        val userInfoStr = getSpValue(SP_USER_INFO, BaseApp.instance, KEY_USER_INFO, "")
        return userInfoStr.isNotEmpty()
    }

    fun getUserInfo(): UserInfo? {
        val userInfoStr = getSpValue(SP_USER_INFO, BaseApp.instance, KEY_USER_INFO, "")
        return if (userInfoStr.isNotEmpty()) {
            mGson.fromJson(userInfoStr, UserInfo::class.java)
        } else {
            null
        }
    }

    fun setUserInfo(userInfo: UserInfo) =
        putSpValue(SP_USER_INFO, BaseApp.instance, KEY_USER_INFO, mGson.toJson(userInfo))

    fun clearUserInfo() {
        clearSpValue(SP_USER_INFO, BaseApp.instance)
    }
}