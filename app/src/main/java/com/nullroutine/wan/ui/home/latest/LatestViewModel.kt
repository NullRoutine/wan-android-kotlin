package com.nullroutine.wan.ui.home.latest

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.common.loadmore.LoadMoreStatus
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.ui.base.BaseViewModel

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/18 10:31
 *
 */
class LatestViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    private val latestRepository by lazy { LatestRepository() }
    val articleList = MutableLiveData<MutableList<Article>>(mutableListOf())
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    fun refreshProjectList() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val pagination = latestRepository.getProjectList(INITIAL_PAGE)
                page = pagination.curPage
                articleList.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreProjectList() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = latestRepository.getProjectList(page)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }
}