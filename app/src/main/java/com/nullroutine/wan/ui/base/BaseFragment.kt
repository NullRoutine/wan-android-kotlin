package com.nullroutine.wan.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *@Author：created by tang.wangqiang
 *时间：2020/4/23 16:17
 *
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", this::class.java.simpleName)
        return inflater.inflate(getLayoutRes(), container, false)
    }

    open fun getLayoutRes() = 0
}