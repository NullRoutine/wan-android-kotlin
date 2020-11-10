package com.nullroutine.wan.ui.discovery

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.HotWord
import kotlinx.android.synthetic.main.item_hot_word.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/16 17:01
 *
 */
class HotWordsAdapter(layouResId: Int = R.layout.item_hot_word) :
    BaseQuickAdapter<HotWord, BaseViewHolder>(layouResId) {
    override fun convert(helper: BaseViewHolder, item: HotWord) {
        helper.itemView.tvName.text = item.name
    }


}