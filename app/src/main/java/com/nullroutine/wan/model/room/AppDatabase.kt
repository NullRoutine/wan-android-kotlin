package com.nullroutine.wan.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullroutine.wan.model.bean.Article
import com.nullroutine.wan.model.bean.Tag

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/15 10:44
 *
 */
@Database(entities = [Article::class, Tag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readHistoryDao(): ReadHistoryDao
}