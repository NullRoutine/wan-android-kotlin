package com.nullroutine.wan.model.bean

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/16 15:52
 *
 */
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)