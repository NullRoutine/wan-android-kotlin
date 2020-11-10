package com.nullroutine.wan.ui.home.project

import com.nullroutine.wan.http.RetrofitClient

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/10/27 14:50
 *
 */
class ProjectRepository {
    suspend fun getProjectCategories() = RetrofitClient.apiService.getProjectCategories().apiData()
    suspend fun getProjectListByCid(page: Int, cid: Int) =
        RetrofitClient.apiService.getProjectListByCid(page, cid).apiData()
}