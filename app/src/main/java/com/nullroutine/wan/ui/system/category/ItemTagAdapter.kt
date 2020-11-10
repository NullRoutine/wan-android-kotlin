package com.nullroutine.wan.ui.system.category

import android.view.LayoutInflater
import android.view.View
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.Category
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_nav_tag.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/10/29 17:37
 *
 */
class ItemTagAdapter(private val categoryList: List<Category>) :TagAdapter<Category>(categoryList){
    override fun getView(parent: FlowLayout?, position: Int, t: Category?): View {
       return LayoutInflater.from(parent?.context)
           .inflate(R.layout.item_system_category_tag, parent, false)
           .apply {
               tvTag.text = categoryList[position].name
           }
    }


}