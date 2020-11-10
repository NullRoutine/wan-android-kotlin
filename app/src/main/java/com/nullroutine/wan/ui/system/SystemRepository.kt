package com.nullroutine.wan.ui.system

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/10/29 17:19
 *
 */
class SystemRepository {
    suspend fun getArticleCategories() = RetrofitClient.apiService.getArticleCategories().apiData()
}