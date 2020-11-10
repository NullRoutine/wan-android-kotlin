package com.nullroutine.wan.ui.search.history

import com.nullroutine.wan.http.RetrofitClient
import com.nullroutine.wan.model.store.SearchHistoryStore

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/14 14:25
 *
 */
class SearchHistoryRepository {
    suspend fun getHotSearch() = RetrofitClient.apiService.getHotWords().apiData()
    fun saveSearchHistory(searchWords: String) {
        SearchHistoryStore.saveSearchHistory(searchWords)
    }

    fun deleteSearchHistory(searchWords: String) {
        SearchHistoryStore.deleteSearchHistory(searchWords)
    }

    fun getSearchHisory() = SearchHistoryStore.getSearchHistory()

}