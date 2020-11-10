package com.nullroutine.wan.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nullroutine.wan.MainActivity
import com.nullroutine.wan.R
import com.nullroutine.wan.common.ScrollToTop
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.detail.DetailActivity
import com.nullroutine.wan.util.ActivityManager
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.include_reload.*


/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/23 17:19
 *
 */
class NavigationFragment : BaseVmFragment<NavigationViewModel>(), ScrollToTop {
    private lateinit var mAdapter: NavigationAdapter
    private var currentPosition = 0

    companion object {
        fun newInstance() = NavigationFragment()
    }

    override fun getLayoutRes() = R.layout.fragment_navigation

    override fun viewModelClass() = NavigationViewModel::class.java

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        swipeRefreshLayout_nav.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.getNavigations() }
        }
        mAdapter = NavigationAdapter(R.layout.item_navigation).apply {
            bindToRecyclerView(recyclerView)
            onItemTagClickListener = {
                ActivityManager.startActivity(
                    DetailActivity::class.java,
                    mapOf(
                        DetailActivity.PARAMS_ARTICLE to Article(
                            title = it.title,
                            link = it.link
                        )
                    )
                )
            }
        }
        btnReload.setOnClickListener {
            mViewModel.getNavigations()
        }
        recyclerView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (activity is MainActivity && scrollY != oldScrollY) {
                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
            }
            if (scrollY < oldScrollY) {
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }
            val lm = recyclerView.layoutManager as LinearLayoutManager
            val nextView = lm.findViewByPosition(currentPosition + 1)
            if (nextView != null) {
                tvFloatTitle.y = if (nextView.top < tvFloatTitle.measuredHeight) {
                    (nextView.top - tvFloatTitle.measuredHeight).toFloat()
                } else {
                    0f
                }
            }
            currentPosition = lm.findFirstVisibleItemPosition()
            if (scrollY > oldScrollY) {
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }
        }
    }


    override fun observe() {
        super.observe()
        mViewModel.run {
            navigations.observe(viewLifecycleOwner, Observer {
                tvFloatTitle.isGone = it.isEmpty()
                tvFloatTitle.text = it[0].name
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout_nav.isRefreshing = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
    }

    override fun initData() {
        mViewModel.getNavigations()
    }

    override fun scrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }
}