package com.nullroutine.wan.model.bean

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/27 15:16
 *
 */
data class Navigation(
    val cid: Int,
    val name: String,
    val articles: List<Article>
)