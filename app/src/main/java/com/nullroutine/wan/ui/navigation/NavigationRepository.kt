package com.nullroutine.wan.ui.navigation

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/27 15:08
 *
 */
class NavigationRepository {
    suspend fun getNavigations() = RetrofitClient.apiService.getNavigations().apiData()
}