package com.nullroutine.wan.ui.home.plaza

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/9/24 11:41
 *
 */
class PlazaRepository {
    suspend fun getUserArticleList(page: Int) =
        RetrofitClient.apiService.getUserArticleList(page).apiData()
}