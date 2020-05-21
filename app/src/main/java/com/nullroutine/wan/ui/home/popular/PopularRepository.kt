package com.nullroutine.wan.ui.home.popular

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/29 15:06
 *
 */
class PopularRepository {
    suspend fun getTopArticleList() = RetrofitClient.apiService.getTopArticleList().apiData()
    suspend fun getArticleList(page: Int) = RetrofitClient.apiService.getArticleList(page).apiData()
}