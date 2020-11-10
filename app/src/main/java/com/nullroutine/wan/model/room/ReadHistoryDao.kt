package com.nullroutine.wan.model.room

import androidx.room.*
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.model.bean.Tag

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/15 10:45
 *
 */
@Dao
interface ReadHistoryDao {
    @Transaction
    @Insert(entity = Article::class)
    suspend fun insert(article: Article): Long

    @Transaction
    @Insert(entity = Tag::class)
    suspend fun insertArticleTag(tag: Tag): Long

    @Transaction
    @Query("SELECT * FROM article")
    suspend fun queryAll(): List<ReadHistory>

    @Transaction
    @Query("SELECT * FROM article WHERE id = (:id)")
    suspend fun queryArticle(id: Int): Article?

    @Transaction
    @Delete(entity = Article::class)
    suspend fun deleteArticle(article: Article)

    @Transaction
    @Update(entity = Article::class)
    suspend fun updateArticle(article: Article)
}