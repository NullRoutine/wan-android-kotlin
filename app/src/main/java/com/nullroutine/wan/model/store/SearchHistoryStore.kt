package com.nullroutine.wan.model.store

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nullroutine.wan.BaseApp
import com.nullroutine.wan.util.getSpValue
import com.nullroutine.wan.util.putSpValue

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/15 14:36
 *
 */
object SearchHistoryStore {
    private const val SP_SEARCH_HISTORY = "sp_search_history"
    private const val KEY_SEARCH_HISTORY = "searchHistory"
    private val mGson by lazy { Gson() }

    fun saveSearchHistory(words: String) {
        val history = getSearchHistory()
        if (history.contains(words)) {
            history.remove(words)
        }
        history.add(0, words)
        val listStr = mGson.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, BaseApp.instance, KEY_SEARCH_HISTORY, listStr)
    }

    fun deleteSearchHistory(words: String) {
        val history = getSearchHistory()
        history.remove(words)
        val listStr = mGson.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, BaseApp.instance, KEY_SEARCH_HISTORY, listStr)
    }

    fun getSearchHistory(): MutableList<String> {
        val listStr = getSpValue(SP_SEARCH_HISTORY, BaseApp.instance, KEY_SEARCH_HISTORY, "")
        return if (listStr.isEmpty()) {
            mutableListOf()
        } else {
            mGson.fromJson<MutableList<String>>(
                listStr,
                object : TypeToken<MutableList<String>>() {}.type
            )
        }
    }
}