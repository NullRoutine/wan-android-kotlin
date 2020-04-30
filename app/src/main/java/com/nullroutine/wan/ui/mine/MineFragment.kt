package com.nullroutine.wan.ui.mine

import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseFragment

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/23 17:21
 *
 */
class MineFragment : BaseFragment() {

    companion object {
        fun newInstance() = MineFragment()
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }
}