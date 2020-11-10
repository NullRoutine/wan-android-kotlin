package com.nullroutine.wan.ui.system.category

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.Category
import com.nullroutine.wan.util.htmlToSpanned
import kotlinx.android.synthetic.main.item_system_category.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/10/30 9:19
 *
 */
class SystemCategoryAdapter(
    layoutResId: Int = R.layout.item_system_category,
    categoryList: List<Category>,
    var checked: Pair<Int, Int>
) : BaseQuickAdapter<Category, BaseViewHolder>(layoutResId, categoryList) {
    var onCheckedListener: ((checked: Pair<Int, Int>) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Category?) {
        helper.itemView.run {
            if (item != null) {
                title.text = item.name.htmlToSpanned()
            }
            if (item != null) {
                tagFlowLayout.adapter = ItemTagAdapter(item.children)
            }
            if (checked.first == helper.adapterPosition) {
                tagFlowLayout.adapter.setSelectedList(checked.second)
            }
            tagFlowLayout.setOnTagClickListener { _, position, _ ->
                checked = helper.adapterPosition to position
                notifyDataSetChanged()
                tagFlowLayout.postDelayed({
                    onCheckedListener?.invoke(checked)
                }, 300)
                true
            }
        }

    }

}