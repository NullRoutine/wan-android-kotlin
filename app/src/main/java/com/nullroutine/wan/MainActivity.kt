package com.nullroutine.wan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.nullroutine.wan.ui.base.BaseActivity
import com.nullroutine.wan.ui.discovery.DiscoveryFragment
import com.nullroutine.wan.ui.home.HomeFragment
import com.nullroutine.wan.ui.mine.MineFragment
import com.nullroutine.wan.ui.navigation.NavigationFragment
import com.nullroutine.wan.ui.system.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var fragmentsList: Map<Int, Fragment>

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentsList = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.discovery to createFragment(DiscoveryFragment::class.java),
            R.id.navigation to createFragment(NavigationFragment::class.java),
            R.id.mine to createFragment(MineFragment::class.java)
        )
        bottomNavigationView.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                Log.e("TAG", "============>>${menuItem.itemId}")
                true
            }
        }

        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            showFragment(initialItemId)
//            bottomNavigationView.selectedItemId = initialItemId//这里会触发监听切换
            Log.e("TAG", "============>>savedInstanceState")
        }
    }

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance()
                SystemFragment::class.java -> SystemFragment.newInstance()
                DiscoveryFragment::class.java -> DiscoveryFragment.newInstance()
                NavigationFragment::class.java -> NavigationFragment.newInstance()
                MineFragment::class.java -> MineFragment.newInstance()
                else -> throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(menuId: Int) {
        val currentFragment =
            supportFragmentManager.fragments.find { it.isVisible && it in fragmentsList.values }
        val targetFragment = fragmentsList.entries.find { it.key == menuId }?.value
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) {
                    show(it)
                    Log.e("TAG", "============>>isAdded")
                } else {
                    Log.e("TAG", "============>>Added")
                    add(R.id.fl, it)
                }
            }
        }.commitAllowingStateLoss()
    }
}
