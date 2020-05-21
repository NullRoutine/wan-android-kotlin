package com.nullroutine.wan.ui.search

import android.os.Bundle
import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseActivity

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/21 15:08
 *
 */
class SearchActivity : BaseActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {

    }
}