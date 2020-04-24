package com.nullroutine.wan.ui.system

import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseFragment

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/23 17:17
 *
 */
class SystemFragment : BaseFragment() {
    companion object {
        fun newInstance() = SystemFragment()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_gallery
    }
}