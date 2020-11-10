package com.nullroutine.wan.ui.discovery

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/16 15:34
 *
 */
class DiscoveryRepository {
    suspend fun getBanners() = RetrofitClient.apiService.getBanners().apiData()
    suspend fun getHotWords() = RetrofitClient.apiService.getHotWords().apiData()
    suspend fun getFrequentlyWebsites() =
        RetrofitClient.apiService.getFrequentlyWebsites().apiData()
}