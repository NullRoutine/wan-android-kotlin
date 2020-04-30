package com.nullroutine.wan.ui.home.popular

import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseVmFragment

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/27 16:34
 *
 */
class PopularFragment : BaseVmFragment<PopularViewModel>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_tools
    }
    companion object {
        fun newInstance() = PopularFragment()
    }

    override fun viewModelClass(): Class<PopularViewModel> {
        return PopularViewModel::class.java
    }

    override fun initView() {
        super.initView()

    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.refreshArticleList()
    }
}