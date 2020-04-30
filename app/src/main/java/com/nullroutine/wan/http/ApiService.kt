package com.nullroutine.wan.http

import com.nullroutine.wan.model.Article
import retrofit2.http.GET

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/27 18:11
 *
 */
interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/top/json")
    suspend fun getTopArticleList():ApiResult<List<Article>>
}