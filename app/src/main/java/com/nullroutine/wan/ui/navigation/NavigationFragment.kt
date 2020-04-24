package com.nullroutine.wan.ui.navigation

import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseFragment

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/23 17:19
 *
 */
class NavigationFragment : BaseFragment() {
    companion object {
        fun newInstance() = NavigationFragment()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_send
    }
}