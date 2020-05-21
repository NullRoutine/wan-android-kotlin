package com.nullroutine.wan.ui.search.result

import com.nullroutine.wan.R
import com.nullroutine.wan.common.loadmore.CommonLoadMoreView
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.detail.DetailActivity
import com.nullroutine.wan.ui.home.ArticleAdapter
import com.nullroutine.wan.util.ActivityManager
import kotlinx.android.synthetic.main.fragment_search_result.*

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
        super.initView()
        searchResultAdapter = ArticleAdapter(R.layout.item_article).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView_search)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAMS_ARTICLE to article)
                )
            }
        }
    }
}