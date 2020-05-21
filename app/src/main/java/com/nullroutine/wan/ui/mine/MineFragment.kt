package com.nullroutine.wan.ui.mine

import android.annotation.SuppressLint
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseVmFragment
import com.nullroutine.wan.ui.mine.setting.SettingActivity
import com.nullroutine.wan.util.ActivityManager
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/23 17:21
 *
 */
class MineFragment : BaseVmFragment<MineViewModel>() {

    companion object {
        fun newInstance() = MineFragment()
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        super.initView()
        clHeader.setOnClickListener {
            checkLogin { /*上传头像，暂不支持*/ }
        }

        llSetting.setOnClickListener {
            ActivityManager.startActivity(SettingActivity::class.java)
        }
    }

    override fun viewModelClass(): Class<MineViewModel> {
        return MineViewModel::class.java
    }

    @SuppressLint("SetTextI18n")
    override fun observe() {
        super.observe()
        mViewModel.run {
            isLogin.observe(viewLifecycleOwner, Observer {
                tvLoginRegister.isGone = it
                tvNickName.isVisible = it
                tvId.isVisible = it
            })
            userInfo.observe(viewLifecycleOwner, Observer {
                it?.let {
                    tvNickName.text = it.nickname
                    tvId.text = "ID: ${it.id}"
                }
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner) {
            mViewModel.getUserInfo()
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getUserInfo()
    }
}