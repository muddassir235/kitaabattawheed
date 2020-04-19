package com.app.kitaabattawheed.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources

inline fun <reified T: Activity> Context.createIntent() =
    Intent(this, T::class.java)

inline fun <reified T: Activity> Activity.startActivity() {
    startActivity(createIntent<T>())
}

inline fun <reified T: Activity> Activity.startActivityNoAnimation() {
    val intent = createIntent<T>()
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()