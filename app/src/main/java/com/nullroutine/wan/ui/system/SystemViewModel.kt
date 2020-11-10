package com.nullroutine.wan.ui.system

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.model.bean.Category
import com.nullroutine.wan.ui.base.BaseViewModel

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/10/29 17:06
 *
 */
class SystemViewModel:BaseViewModel() {

    private val systemRepository by lazy { SystemRepository() }
    val categories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val loadingStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getArticleCategory() {
        loadingStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                categories.value = systemRepository.getArticleCategories()
                loadingStatus.value = false
            },
            error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }
}