package com.nullroutine.wan.ui.system.pager

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.common.loadmore.LoadMoreStatus
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.ui.base.BaseViewModel
import com.nullroutine.wan.ui.common.CollectRepository
import com.nullroutine.wan.util.bus.Bus
import com.nullroutine.wan.util.bus.USER_COLLECT_UPDATED
import kotlinx.coroutines.Job

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/11/9 9:26
 *
 */
class SystemPagerViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    private val systemPagerRepository by lazy { SystemPagerRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList = MutableLiveData<MutableList<Article>>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE
    private var id: Int = -1
    private var refreshJob: Job? = null

    fun refreshArticleList(cid: Int) {
        if (cid != id) {
            cancelJob(refreshJob)
            id = cid
            articleList.value = mutableListOf()
        }
        refreshStatus.value = true
        reloadStatus.value = false
        refreshJob = launch(
            block = {
                val pagination = systemPagerRepository.getArticleListByCid(INITIAL_PAGE, cid)
                page = pagination.curPage
                articleList.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = articleList.value?.isEmpty()
            }
        )
    }

    fun loadMoreArticleList(cid: Int) {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = systemPagerRepository.getArticleListByCid(page, cid)
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

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateListCollectState()
                Bus.post(USER_COLLECT_UPDATED, id to true)
            },
            error = {
                updateListCollectState()
            }
        )
    }

    fun uncollect(id: Int) {
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateListCollectState()
                Bus.post(USER_COLLECT_UPDATED, id to false)
            },
            error = {
                updateListCollectState()
            }
        )
    }

    /**
     * 更新列表收藏状态
     */
    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()) {
            val collectIds = userRepository.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    /**
     * 更新Item的收藏状态
     */
    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}
