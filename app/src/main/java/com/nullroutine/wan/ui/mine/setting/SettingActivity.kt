package com.nullroutine.wan.ui.mine.setting

import androidx.appcompat.app.AlertDialog
import com.nullroutine.wan.R
import com.nullroutine.wan.model.store.SettingsStore
import com.nullroutine.wan.ui.base.BaseVmActivity
import com.nullroutine.wan.ui.login.LoginActivity
import com.nullroutine.wan.util.ActivityManager
import com.nullroutine.wan.util.isNightMode
import com.nullroutine.wan.util.setNightMode
import kotlinx.android.synthetic.main.activity_settings.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/20 16:39
 *
 */
class SettingActivity : BaseVmActivity<SettingViewModel>() {
    override fun viewModelClass() = SettingViewModel::class.java

    override fun getLayoutRes(): Int {
        return R.layout.activity_settings
    }

    override fun initView() {
        super.initView()
        scDayNight.isChecked = isNightMode(this)
        scDayNight.setOnCheckedChangeListener { _, checked ->
            setNightMode(checked)
            SettingsStore.setNightMode(checked)
        }
        tvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    mViewModel.logout()
                    ActivityManager.startActivity(LoginActivity::class.java)
                    ActivityManager.finish(SettingActivity::class.java)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }
    }

    override fun initData() {
        super.initData()
    }

}