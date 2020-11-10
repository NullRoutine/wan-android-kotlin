package com.nullroutine.wan.ui.discovery

import android.content.Context
import android.widget.ImageView
import com.nullroutine.wan.R
import com.nullroutine.wan.model.bean.Banner
import com.nullroutine.wan.util.ImageOptions
import com.nullroutine.wan.util.loadImage
import com.youth.banner.loader.ImageLoader

/**
 *@Author：created by tang.wangqiang
 *@时间：2020/7/16 17:06
 *
 */
class BannerImageLoader: ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val imagePath = (path as? Banner)?.imagePath
        imageView?.loadImage(imagePath, ImageOptions().apply {
            placeholder = R.drawable.shape_bg_image_default
        })
    }

}