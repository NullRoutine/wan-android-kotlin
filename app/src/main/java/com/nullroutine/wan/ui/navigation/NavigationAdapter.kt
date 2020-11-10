package com.nullroutine.wan.ui.navigation

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.model.bean.Navigation
import kotlinx.android.synthetic.main.item_navigation.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/27 16:28
 *
 */
class NavigationAdapter(layoutResId: Int = R.layout.item_navigation) :
    BaseQuickAdapter<Navigation, BaseViewHolder>(layoutResId) {
    var onItemTagClickListener: ((article: Article) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Navigation) {
        helper.itemView.run {
            title.text = item.name
            tagFlawLayout.adapter = ItemTagAdapter(item.articles)
            tagFlawLayout.setOnTagClickListener { _, position, _ ->
                onItemTagClickListener?.invoke(item.articles[position])
                true
            }
        }
    }
}