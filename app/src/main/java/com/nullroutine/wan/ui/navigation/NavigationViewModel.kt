package com.nullroutine.wan.ui.navigation

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.model.bean.Navigation
import com.nullroutine.wan.ui.base.BaseViewModel

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/27 15:08
 *
 */
class NavigationViewModel : BaseViewModel() {
    private val navigationRepository by lazy { NavigationRepository() }
    val navigations = MutableLiveData<List<Navigation>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getNavigations() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                navigations.value = navigationRepository.getNavigations()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = navigations.value.isNullOrEmpty()
            }
        )
    }
}