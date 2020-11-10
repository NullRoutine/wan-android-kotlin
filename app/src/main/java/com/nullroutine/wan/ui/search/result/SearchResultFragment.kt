package com.nullroutine.wan.ui.search.result

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.nullroutine.wan.R
import com.nullroutine.wan.common.loadmore.CommonLoadMoreView
import com.nullroutine.wan.common.loadmore.LoadMoreStatus
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.detail.DetailActivity
import com.nullroutine.wan.ui.home.ArticleAdapter
import com.nullroutine.wan.util.ActivityManager
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_COLLECT_UPDATED
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/21 15:26
 *
 */
class SearchResultFragment : BaseVmFragment<SearchResultViewModel>() {
    companion object {
        fun newInstance(): SearchResultFragment {
            return SearchResultFragment()
        }
    }

    override fun viewModelClass() = SearchResultViewModel::class.java

    override fun getLayoutRes() = R.layout.fragment_search_result

    private lateinit var searchResultAdapter: ArticleAdapter

    override fun initView() {
        searchResultAdapter = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView_search)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAMS_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
            setOnLoadMoreListener({ mViewModel.loadMore() }, recyclerView_search)
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.search() }
        }
        btnReload.setOnClickListener {
            mViewModel.search()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                searchResultAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            emptyStatus.observe(viewLifecycleOwner, Observer {
                emptyView.isVisible = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> searchResultAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> searchResultAdapter.loadMoreFail()
                    LoadMoreStatus.END -> searchResultAdapter.loadMoreEnd()
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

    fun doSearch(searchWords: String) {
        mViewModel.search(searchWords)
    }
}