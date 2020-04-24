package com.nullroutine.wan.util

import android.app.Activity
import android.content.Intent

/**
 *@Author：created by tang.wangqiang
 *时间：2020/4/23 14:35
 *对象声明单例
 */
object ActivityManager {
    val activityList = mutableListOf<Activity>()

    fun startActivity(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
        val currentActivity = activityList[activityList.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }


    /**
     * finish指定的一个或多个Activity
     */
    fun finish(vararg clazz: Class<out Activity>) {
        activityList.forEach { activiy ->
            if (clazz.contains(activiy::class.java)) {
                activiy.finish()
            }
        }
    }

}