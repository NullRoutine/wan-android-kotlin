package com.nullroutine.wan.ui.search.history

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.HotWord
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.search.SearchActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.item_hot_search.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/14 13:49
 *
 */
class SearchHistoryFragment : BaseVmFragment<SearchHistoryViewModel>() {

    companion object{
        fun newInstance()=SearchHistoryFragment()
    }
    override fun viewModelClass() = SearchHistoryViewModel::class.java

    override fun getLayoutRes(): Int {
        return R.layout.fragment_search_history
    }
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun initView() {
        super.initView()
        searchHistoryAdapter=SearchHistoryAdapter(activity!!).apply {
            rvSearchHistory.adapter=this
            onItemClickListener={
                (activity as? SearchActivity)?.fillSearchInput(data[it])
            }
            onDeleteClickListener = {
                mViewModel.deleteSearchHistory(searchHistoryAdapter.data[it])
            }
        }
    }
    override fun initData() {
        mViewModel.getHotSearch()
        mViewModel.getSearchHistory()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            hotWords.observe(viewLifecycleOwner, Observer {
                tvHotSearch.visibility = View.VISIBLE
                setHotwords(it)
            })
            searchHistory.observe(viewLifecycleOwner, Observer {
                tvSearchHistory.isGone = it.isEmpty()
                searchHistoryAdapter.submitList(it)
            })
        }
    }

    private fun setHotwords(hotwords: List<HotWord>) {
        tflHotSearch.run {
            adapter = object : TagAdapter<HotWord>(hotwords) {
                override fun getView(parent: FlowLayout?, position: Int, hotWord: HotWord?): View {
                    return LayoutInflater.from(parent?.context)
                        .inflate(R.layout.item_hot_search, parent, false)
                        .apply { this.tvTag.text = hotWord?.name }
                }
            }
            setOnTagClickListener { _, position, _ ->
                (activity as? SearchActivity)?.fillSearchInput(hotwords[position].name)
                false
            }
        }
    }

    fun addSearchHistory(keywords: String) {
        mViewModel.addSearchHistory(keywords)
    }

}