package com.nullroutine.wan.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseFragment
import com.nullroutine.wan.ui.home.popular.PopularFragment
import com.nullroutine.wan.ui.mine.MineFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    private lateinit var fragments: List<Fragment>

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
            MineFragment.newInstance(),
            MineFragment.newInstance(),
            MineFragment.newInstance(),
            MineFragment.newInstance()
        )
        val titles = listOf<CharSequence>(
            getString(R.string.popular_articles),
            getString(R.string.latest_project),
            getString(R.string.plaza),
            getString(R.string.project_category),
            getString(R.string.wechat_public)
        )
        ViewPager_Home.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }
        TabLayoutMediator(tab_layout, ViewPager_Home) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }


}