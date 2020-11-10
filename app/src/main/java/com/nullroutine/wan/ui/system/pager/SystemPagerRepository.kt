package com.nullroutine.wan.ui.system.pager

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/11/9 9:44
 *
 */
class SystemPagerRepository {

    suspend fun getArticleListByCid(page:Int,cid:Int)=
        RetrofitClient.apiService.getArticleListByCid(page, cid).apiData()

}