package com.nullroutine.wan.ui.home.latest

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/18 10:44
 *
 */
class LatestRepository {
    suspend fun getProjectList(page: Int) = RetrofitClient.apiService.getProjectList(page).apiData()
}