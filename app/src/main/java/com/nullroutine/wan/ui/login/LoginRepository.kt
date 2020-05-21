package com.nullroutine.wan.ui.login

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/11 16:33
 *
 */
class LoginRepository {
    suspend fun login(username: String, password: String) =
        RetrofitClient.apiService.login(username, password).apiData()
}