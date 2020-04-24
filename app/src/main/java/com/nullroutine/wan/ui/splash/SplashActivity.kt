package com.nullroutine.wan.ui.splash

import android.os.Bundle
import com.nullroutine.wan.MainActivity
import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseActivity
import com.nullroutine.wan.util.ActivityManager

/**
 *created by tang.wangqiang
 *时间:2020/4/23 14:04
 *
 */
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.postDelayed(Runnable {
            ActivityManager.startActivity(MainActivity::class.java)
            ActivityManager.finish(this::class.java)
        }, 1500)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }
}