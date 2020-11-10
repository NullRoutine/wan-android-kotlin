package com.nullroutine.wan.ui.navigation

import android.view.LayoutInflater
import android.view.View
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.Article
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_nav_tag.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/27 16:33
 *
 */
class ItemTagAdapter(private val articles: List<Article>) : TagAdapter<Article>(articles) {
    override fun getView(parent: FlowLayout?, position: Int, article: Article?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_nav_tag, parent, false)
            .apply {
                tvTag.text = articles[position].title
            }
    }
}