package com.nullroutine.wan.ext

import com.nullroutine.wan.BaseApp
import com.nullroutine.wan.util.dpToPx
import com.nullroutine.wan.util.pxToDp

/**
 * Created by xiaojianjun on 2019-12-02.
 */
fun Float.toPx() = dpToPx(BaseApp.instance, this)

fun Float.toIntPx() = dpToPx(BaseApp.instance, this).toInt()

fun Float.toDp() = pxToDp(BaseApp.instance, this)

fun Float.toIntDp() = pxToDp(BaseApp.instance, this).toInt()