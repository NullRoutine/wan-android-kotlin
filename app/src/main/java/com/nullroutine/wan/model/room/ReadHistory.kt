package com.nullroutine.wan.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.model.bean.Tag

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/15 10:31
 *
 */
data class ReadHistory(
    @Embedded
    var article: Article,
    @Relation(parentColumn = "id", entityColumn = "article_id")
    var tags: List<Tag>
)