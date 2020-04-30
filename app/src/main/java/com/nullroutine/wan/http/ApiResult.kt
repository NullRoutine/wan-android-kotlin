package com.nullroutine.wan.http

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/29 14:27
 *
 */
data class ApiResult<T>(val errorCode: Int, val errorMsg: String, private val data: T) {
    fun apiData(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(errorCode, errorMsg)
        }
    }
}