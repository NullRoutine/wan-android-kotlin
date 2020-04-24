package com.nullroutine.wan.ui.home

import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseFragment

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }
}