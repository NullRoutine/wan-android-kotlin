package com.nullroutine.wan.ui.home.project

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.nullroutine.wan.R
import com.nullroutine.wan.common.ScrollToTop
import com.nullroutine.wan.common.loadmore.CommonLoadMoreView
import com.nullroutine.wan.common.loadmore.LoadMoreStatus
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.detail.DetailActivity
import com.nullroutine.wan.ui.home.ArticleAdapter
import com.nullroutine.wan.ui.home.CategoryAdapter
import com.nullroutine.wan.util.ActivityManager
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_COLLECT_UPDATED
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.include_reload.view.*


/**
 *@Author：created by tang.wangqiang
 *@时间：2020/10/27 14:34
 *
 */
class ProjectFragment : BaseVmFragment<ProjectViewModel>(), ScrollToTop {


    companion object {
        fun newInstance() = ProjectFragment()
    }

    private lateinit var mAdapter: ArticleAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter

    override fun getLayoutRes() = R.layout.fragment_project

    override fun viewModelClass() = ProjectViewModel::class.java

    override fun initView() {
        super.initView()
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshProjectList() }
        }
        mCategoryAdapter = CategoryAdapter(R.layout.item_category_sub)
            .apply {
                bindToRecyclerView(rvCategory)
                onCheckedListener = {
                    mViewModel.refreshProjectList(it)
                }
            }
        mAdapter = ArticleAdapter(R.layout.item_article).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreProjectList()
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityManager.startActivity(
                    DetailActivity::class.java, mapOf(DetailActivity.PARAMS_ARTICLE to article)
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
        reloadListView.btnReload.setOnClickListener {
            mViewModel.refreshProjectList()
        }
        reloadView.btnReload.setOnClickListener {
            mViewModel.getProjectCategory()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            categories.observe(viewLifecycleOwner, Observer {
                rvCategory.isGone = it.isEmpty()
                mCategoryAdapter.setNewData(it)
            })
            checkedCategory.observe(viewLifecycleOwner, Observer {
                mCategoryAdapter.check(it)
            })
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
            reloadListStatus.observe(viewLifecycleOwner, Observer {
                reloadListView.isVisible = it
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
        mViewModel.getProjectCategory()
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
}