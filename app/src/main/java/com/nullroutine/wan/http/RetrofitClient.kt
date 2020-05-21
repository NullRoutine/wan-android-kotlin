package com.nullroutine.wan.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.nullroutine.wan.BaseApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit实例
 *@Author：created by tang.wangqiang
 *@时间：2020/4/27 18:01
 *
 */
object RetrofitClient {
    private val cookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(BaseApp.instance)
    )

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .cookieJar(cookieJar)
        .build()
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun clearCookie() = cookieJar.clear()
}