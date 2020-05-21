package com.nullroutine.wan.ui.home

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.util.htmlToSpanned
import kotlinx.android.synthetic.main.item_article.view.*

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/5/15 14:17
 *
 */
class ArticleAdapter(layoutResId: Int = R.layout.item_article) :
    BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: Article?) {
        helper.run {
            itemView.run {
                if (item != null) {
                    tv_author.text = when {
                        !item.author.isNullOrEmpty() -> {
                            item.author
                        }
                        !item.shareUser.isNullOrEmpty() -> {
                            item.shareUser
                        }
                        else -> context.getString(R.string.anonymous)
                    }
                    tv_top.isVisible = item.top
                    tv_fresh.isVisible = item.fresh && !item.top
                    tv_tag.visibility = if (item.tags.isNotEmpty()) {
                        tv_tag.text = item.tags[0].name
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                    tv_chapter.text = when {
                        !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                            "${item.superChapterName.htmlToSpanned()}/${item.chapterName.htmlToSpanned()}"
                        item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                            item.chapterName.htmlToSpanned()
                        !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                            item.superChapterName.htmlToSpanned()
                        else -> ""

                    }
                    tv_title.text = item.title.htmlToSpanned()
                    tv_desc.text = item.desc.htmlToSpanned()
                    tv_desc.isGone = item.desc.isNullOrEmpty()
                    tv_time.text = item.niceDate
                    iv_collect.isSelected = item.collect
                }
            }
            addOnClickListener(R.id.iv_collect)
        }
    }

}