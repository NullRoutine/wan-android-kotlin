package com.nullroutine.wan.ui.discovery

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.model.bean.Banner
import com.nullroutine.wan.model.bean.Frequently
import com.nullroutine.wan.model.bean.HotWord
import com.nullroutine.wan.ui.base.BaseViewModel

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/16 16:33
 *
 */
open class DiscoveryViewModel : BaseViewModel() {
    private val discoveryRepository by lazy { DiscoveryRepository() }
    val banners = MutableLiveData<List<Banner>>()
    val hotWords = MutableLiveData<List<HotWord>>()
    val frequentlyList = MutableLiveData<List<Frequently>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    fun getData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                banners.value = discoveryRepository.getBanners()
                hotWords.value = discoveryRepository.getHotWords()
                frequentlyList.value = discoveryRepository.getFrequentlyWebsites()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }
}