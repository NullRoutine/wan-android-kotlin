package com.nullroutine.wan.ui.system.pager

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nullroutine.wan.R
import com.nullroutine.wan.common.ScrollToTop
import com.nullroutine.wan.common.loadmore.CommonLoadMoreView
import com.nullroutine.wan.common.loadmore.LoadMoreStatus
import com.nullroutine.wan.ext.toIntPx
import com.nullroutine.wan.model.bean.Category
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.detail.DetailActivity
import com.nullroutine.wan.ui.home.CategoryAdapter
import com.nullroutine.wan.ui.home.SimpleArticleAdapter
import com.nullroutine.wan.util.ActivityManager
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_COLLECT_UPDATED
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_system_pager.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/11/9 9:25
 *
 */
class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>(), ScrollToTop {
    companion object {
        private const val CATEGORY_LIST = "CATEGORY_LIST"

        fun newInstance(categoryList: ArrayList<Category>): SystemPagerFragment {
            return SystemPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CATEGORY_LIST, categoryList)
                }
            }
        }
    }

    private lateinit var categoryList: List<Category>
    var checkedPosition = 0
    private lateinit var mAdapterSimple:SimpleArticleAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    override fun getLayoutRes()= R.layout.fragment_system_pager
    override fun viewModelClass(): Class<SystemPagerViewModel> {
        return SystemPagerViewModel::class.java
    }

    override fun initView() {
        super.initView()
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.refreshArticleList(categoryList[checkedPosition].id)
            }
        }
        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
        checkedPosition = 0
        categoryAdapter = CategoryAdapter(R.layout.item_category_sub).apply {
            bindToRecyclerView(rvCategory)
            setNewData(categoryList)
            onCheckedListener = {
                checkedPosition = it
                mViewModel.refreshArticleList(categoryList[checkedPosition].id)
            }
        }

        mAdapterSimple = SimpleArticleAdapter(R.layout.item_article_simple).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList(categoryList[checkedPosition].id)
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapterSimple.data[position]
                ActivityManager.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAMS_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = mAdapterSimple.data[position]
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
            mViewModel.refreshArticleList(categoryList[checkedPosition].id)
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapterSimple.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapterSimple.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapterSimple.loadMoreFail()
                    LoadMoreStatus.END -> mAdapterSimple.loadMoreEnd()
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
        mViewModel.refreshArticleList(categoryList[checkedPosition].id)
    }

    override fun scrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }

    fun check(position: Int) {
        if (position != checkedPosition) {
            checkedPosition = position
            categoryAdapter.check(position)
            (rvCategory.layoutManager as? LinearLayoutManager)
                ?.scrollToPositionWithOffset(position, 8f.toIntPx())
            mViewModel.refreshArticleList(categoryList[checkedPosition].id)
        }
    }
}