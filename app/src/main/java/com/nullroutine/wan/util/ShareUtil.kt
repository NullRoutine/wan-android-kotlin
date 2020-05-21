package com.nullroutine.wan.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.nullroutine.wan.R

/**
 * Created by xiaojianjun on 2019-11-21.
 */
fun share(
    activity: Activity,
    title: String? = activity.getString(R.string.app_name),
    content: String?
) {
    ShareCompat.IntentBuilder.from(activity)
        .setType("text/plain")
        .setSubject(title)
        .setText(content)
        .setChooserTitle(title)
        .startChooser()
}

fun Fragment.openInExplorer(link: String?) {
    startActivity(Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(link)
    })
}