package com.nullroutine.wan.http

import com.nullroutine.wan.model.bean.*
import retrofit2.http.*

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
    suspend fun getTopArticleList(): ApiResult<List<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("/article/listproject/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResult<UserInfo>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResult<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): ApiResult<Any?>

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(
        @Field("k") keywords: String,
        @Path("page") page: Int
    ): ApiResult<Pagination<Article>>

    @GET("hotkey/json")
    suspend fun getHotWords(): ApiResult<List<HotWord>>

    @GET("banner/json")
    suspend fun getBanners(): ApiResult<List<Banner>>

    @GET("friend/json")
    suspend fun getFrequentlyWebsites(): ApiResult<List<Frequently>>

    @GET("navi/json")
    suspend fun getNavigations(): ApiResult<List<Navigation>>

    @GET("/user_article/list/{page}/json")
    suspend fun getUserArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("project/tree/json")
    suspend fun getProjectCategories(): ApiResult<MutableList<Category>>

    @GET("project/list/{page}/json")
    suspend fun getProjectListByCid(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>

    @GET("wxarticle/chapters/json")
    suspend fun getWechatCategories(): ApiResult<MutableList<Category>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWechatArticleList(
        @Path("page") page: Int,
        @Path("id") id: Int
    ): ApiResult<Pagination<Article>>

    @GET("tree/json")
    suspend fun getArticleCategories(): ApiResult<MutableList<Category>>

    @GET("article/list/{page}/json")
    suspend fun getArticleListByCid(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>
}