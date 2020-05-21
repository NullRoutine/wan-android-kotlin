package com.nullroutine.wan.ui.login

import androidx.lifecycle.Observer
import com.nullroutine.wan.R
import com.nullroutine.wan.ui.base.BaseVmActivity
import com.nullroutine.wan.util.ActivityManager
import kotlinx.android.synthetic.main.activity_login.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/11 15:27
 *
 */
class LoginActivity : BaseVmActivity<LoginViewModel>() {
    override fun viewModelClass() = LoginViewModel::class.java
    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        super.initView()
        ivClose.setOnClickListener {
            ActivityManager.finish(LoginActivity::class.java)
        }
        btnLogin.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            val account = tietAccount.text.toString()
            val password = tietPassword.text.toString()
            when {
                account.isEmpty() -> tilAccount.error = getString(R.string.account_can_not_be_empty)
                password.isEmpty() -> tilPassword.error =
                    getString(R.string.password_can_not_be_empty)
                else -> mViewModel.login(account, password)
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(this@LoginActivity, Observer {
                if (it) showProgressDialog(R.string.logging_in) else hideProgressDialog()
            })
            loginResult.observe(this@LoginActivity, Observer {
                if (it) {
                    ActivityManager.finish(LoginActivity::class.java)
                }
            })
        }
    }
}