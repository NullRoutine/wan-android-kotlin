package com.nullroutine.wan.ui.system

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.nullroutine.wan.MainActivity
import com.nullroutine.wan.R
import com.nullroutine.wan.common.ScrollToTop
import com.nullroutine.wan.common.SimpleFragmentPagerAdapter
import com.nullroutine.wan.model.bean.Category
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.system.category.SystemCategoryFragment
import com.nullroutine.wan.ui.system.pager.SystemPagerFragment
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.include_reload.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/23 17:17
 *
 */
class SystemFragment : BaseVmFragment<SystemViewModel>(), ScrollToTop {
    companion object {
        fun newInstance() = SystemFragment()
    }

    private var currentOffset = 0
    private val titles = mutableListOf<String>()
    private val fragments = mutableListOf<SystemPagerFragment>()
    private var categoryFragment: SystemCategoryFragment? = null

    override fun getLayoutRes() = R.layout.fragment_system

    override fun viewModelClass() = SystemViewModel::class.java

    override fun initView() {
        reloadView.btnReload.setOnClickListener {
            mViewModel.getArticleCategory()
        }
        ivFilter.setOnClickListener {
            categoryFragment?.show(childFragmentManager)
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            categories.observe(viewLifecycleOwner, Observer {
                ivFilter.visibility = View.VISIBLE
                tabLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                setup(it)
                categoryFragment = SystemCategoryFragment.newInstance(ArrayList(it))
            })
            loadingStatus.observe(viewLifecycleOwner, Observer {
                progressBar.isVisible = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
    }

    override fun initData() {
        mViewModel.getArticleCategory()
    }

    private fun setup(categories: MutableList<Category>) {
        titles.clear()
        fragments.clear()
        categories.forEach {
            titles.add(it.name)
            fragments.add(SystemPagerFragment.newInstance(it.children))
        }
        viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager, fragments, titles)
        viewPager.offscreenPageLimit = titles.size
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun scrollToTop() {
        if (fragments.isEmpty() || viewPager == null) return
        fragments[viewPager.currentItem].scrollToTop()
    }

    fun getCurrentChecked(): Pair<Int, Int> {
        if (fragments.isEmpty() || viewPager == null) return 0 to 0
        val first = viewPager.currentItem
        val second = fragments[viewPager.currentItem].checkedPosition
        return first to second
    }

    fun check(position: Pair<Int, Int>) {
        if (fragments.isEmpty() || viewPager == null) return
        viewPager.currentItem = position.first
        fragments[position.first].check(position.second)
    }

}