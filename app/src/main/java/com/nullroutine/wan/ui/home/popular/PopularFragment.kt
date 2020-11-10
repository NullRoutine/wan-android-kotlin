package com.nullroutine.wan.ui.home.popular

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
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_COLLECT_UPDATED
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/27 16:34
 *
 */
@Suppress("DEPRECATION")
class PopularFragment : BaseVmFragment<PopularViewModel>(), ScrollToTop {
    private lateinit var mAdapter: ArticleAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_popular
    }

    companion object {
        fun newInstance() = PopularFragment()
    }

    override fun viewModelClass(): Class<PopularViewModel> {
        return PopularViewModel::class.java
    }

    override fun initView() {
//        super.initView()
        swipeRefreshLayout.run {
            setOnRefreshListener {
                mViewModel.refreshArticleList()
            }
        }
        mAdapter = ArticleAdapter(R.layout.item_article).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener {
                mViewModel.loadMoreArticleList()
            }
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityManager.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAMS_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = mAdapter.data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
        }
        btnReload.setOnClickListener {
            mViewModel.refreshArticleList()
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
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner) {
            mViewModel.updateListCollectState()
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, viewLifecycleOwner) {
            mViewModel.updateItemCollectState(it)
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.refreshArticleList()
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}