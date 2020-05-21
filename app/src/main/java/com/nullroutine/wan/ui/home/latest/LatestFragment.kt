package com.nullroutine.wan.ui.home.latest

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.nullroutine.wan.R
import com.nullroutine.wan.common.ScrollToTop
import com.nullroutine.wan.common.loadmore.CommonLoadMoreView
import com.nullroutine.wan.common.loadmore.LoadMoreStatus
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.detail.DetailActivity
import com.nullroutine.wan.ui.home.ArticleAdapter
import com.nullroutine.wan.util.ActivityManager
import kotlinx.android.synthetic.main.fragment_popular.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/18 10:30
 *
 */
@Suppress("DEPRECATION")
class LatestFragment : BaseVmFragment<LatestViewModel>(), ScrollToTop {
    override fun viewModelClass() = LatestViewModel::class.java

    companion object {
        fun newInstance() = LatestFragment()
    }

    private lateinit var mAdapter: ArticleAdapter
    override fun getLayoutRes() = R.layout.fragment_popular

    override fun initView() {
        super.initView()
        swipeRefreshLayout.run {
            setOnRefreshListener {
                mViewModel.refreshProjectList()
            }
        }
        mAdapter = ArticleAdapter(R.layout.item_article).apply {
            bindToRecyclerView(recyclerView)
            setLoadMoreView(CommonLoadMoreView())
            setOnLoadMoreListener() {
                mViewModel.loadMoreProjectList()
            }
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityManager.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAMS_ARTICLE to article)
                )
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.refreshProjectList()
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}