package com.nullroutine.wan.util.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/20 13:42
 *
 */
object Bus {
    inline fun <reified T> post(channel: String, value: T) =
        LiveEventBus.get(channel, T::class.java).post(value)

    inline fun <reified T> observe(
        channel: String,
        owner: LifecycleOwner,
        crossinline observer: ((value: T) -> Unit)
    ) =
        LiveEventBus.get(channel, T::class.java).observe(owner, Observer { observer(it) })
}