package com.nullroutine.wan.ui.home.popular

import androidx.lifecycle.MutableLiveData
import com.nullroutine.wan.model.Article
import com.nullroutine.wan.ui.base.BaseViewModel

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/4/29 14:11
 *
 */
class PopularViewModel:BaseViewModel() {
    private val popularRepository by lazy{PopularRepository()}
    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()

    fun refreshArticleList(){
        launch(
            block = {
                val topArticleListDefferd = async {
                    popularRepository.getTopArticleList()
                }
                val topArticleList = topArticleListDefferd.await()
                    .apply { forEach { it.top = true } }
                articleList.value= mutableListOf<Article>().apply {
                    addAll(topArticleList)
                }
            },
            error ={

            }
        )
    }

}