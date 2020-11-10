package com.nullroutine.wan.ui.home.wechat

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/10/28 15:38
 *
 */
class WechatRepository {
    suspend fun getWechatCategories() = RetrofitClient.apiService.getWechatCategories().apiData()
    suspend fun getWechatArticleList(page: Int, id: Int) =
        RetrofitClient.apiService.getWechatArticleList(page, id).apiData()
}