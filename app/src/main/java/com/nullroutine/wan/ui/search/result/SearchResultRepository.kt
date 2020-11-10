package com.nullroutine.wan.ui.search.result

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/21 17:17
 *
 */
class SearchResultRepository {
    suspend fun search(keywords: String, page: Int) =
        RetrofitClient.apiService.search(keywords, page).apiData()
}