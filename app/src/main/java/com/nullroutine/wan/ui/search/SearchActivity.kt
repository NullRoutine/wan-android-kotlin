package com.nullroutine.wan.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.nullroutine.wan.R
import com.nullroutine.wan.ext.hideSoftInput
import com.nullroutine.wan.ui.base.BaseActivity
import com.nullroutine.wan.ui.search.history.SearchHistoryFragment
import com.nullroutine.wan.ui.search.result.SearchResultFragment
import com.nullroutine.wan.util.ActivityManager
import kotlinx.android.synthetic.main.activity_search.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/21 15:08
 *
 */
class SearchActivity : BaseActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        var historyFragment = SearchHistoryFragment.newInstance()
        val resultFragment = SearchResultFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, historyFragment)
            .add(R.id.flContainer, resultFragment)
            .show(historyFragment)
            .hide(resultFragment)
            .commit()

        ivBack.setOnClickListener {
            if (resultFragment.isVisible) {
                supportFragmentManager
                    .beginTransaction()
                    .hide(resultFragment)
                    .commit()
            } else {
                ActivityManager.finish(SearchActivity::class.java)
            }
        }
        ivDone.setOnClickListener {
            val searchWords = acetInput.text.toString()
            if (searchWords.isEmpty()) return@setOnClickListener
            it.hideSoftInput()
            historyFragment.addSearchHistory(searchWords)
            resultFragment.doSearch(searchWords)
            supportFragmentManager
                .beginTransaction()
                .show(resultFragment)
                .commit()
        }
        acetInput.run {
            addTextChangedListener(afterTextChanged = {
                ivClearSearch.isGone = it.isNullOrEmpty()
            })
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ivDone.performClick()
                    true
                } else {
                    false
                }
            }
        }
        ivClearSearch.setOnClickListener { acetInput.setText("") }
    }

    fun fillSearchInput(keywords: String) {
        acetInput.setText(keywords)
        acetInput.setSelection(keywords.length)
    }

    override fun onBackPressed() {
        ivBack.performClick()

    }
}