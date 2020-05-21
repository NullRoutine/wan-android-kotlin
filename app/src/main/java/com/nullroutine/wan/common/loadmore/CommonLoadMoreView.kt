package com.nullroutine.wan.common.loadmore

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.nullroutine.wan.R

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/18 15:50
 *
 */
class CommonLoadMoreView :LoadMoreView(){
    override fun getLayoutId() = R.layout.view_load_more_common

    override fun getLoadingViewId() = R.id.load_more_loading_view

    override fun getLoadFailViewId() = R.id.load_more_load_fail_view

    override fun getLoadEndViewId() = R.id.load_more_load_end_view

}