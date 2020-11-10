package com.nullroutine.wan.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.nullroutine.wan.MainActivity
import com.nullroutine.wan.R
import com.nullroutine.wan.common.ScrollToTop
import com.nullroutine.wan.common.SimpleFragmentPagerAdapter
import com.nullroutine.wan.ui.base.BaseFragment
import com.nullroutine.wan.ui.home.latest.LatestFragment
import com.nullroutine.wan.ui.home.plaza.PlazaFragment
import com.nullroutine.wan.ui.home.popular.PopularFragment
import com.nullroutine.wan.ui.home.project.ProjectFragment
import com.nullroutine.wan.ui.home.wechat.WechatFragment
import com.nullroutine.wan.ui.search.SearchActivity
import com.nullroutine.wan.util.ActivityManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), ScrollToTop {


    private lateinit var fragments: List<Fragment>
    private var currentOffset = 0

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments = listOf(
            PopularFragment.newInstance(),
            LatestFragment.newInstance(),
            PlazaFragment.newInstance(),
            ProjectFragment.newInstance(),
            WechatFragment.newInstance()
        )
        val titles = listOf<CharSequence>(
            getString(R.string.popular_articles),
            getString(R.string.latest_project),
            getString(R.string.plaza),
            getString(R.string.project_category),
            getString(R.string.wechat_public)
        )
//        ViewPager_Home.adapter = object : FragmentStateAdapter(this) {
////            override fun getItemCount(): Int {
////                return fragments.size
////            }
////
////            override fun createFragment(position: Int): Fragment {
////                return fragments[position]
////            }
////
////        }
////        TabLayoutMediator(tab_layout, ViewPager_Home) { tab, position ->
////            tab.text = titles[position]
////        }.attach()
        viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager, fragments, titles)
        viewPager.offscreenPageLimit = fragments.size
        tab_layout.setupWithViewPager(viewPager)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
        ll_search.setOnClickListener { ActivityManager.startActivity(SearchActivity::class.java) }
    }

    override fun scrollToTop() {
        if (!this::fragments.isInitialized) return
        val currentFragment = fragments[viewPager.currentItem]
        if (currentFragment is ScrollToTop && currentFragment.isVisible) {
            currentFragment.scrollToTop()
        }
    }


}