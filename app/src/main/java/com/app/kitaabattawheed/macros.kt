package com.app.kitaabattawheed

import android.content.Context
import android.content.res.Resources.Theme
import android.util.TypedValue

fun Context.attrColor(colorId: Int): Int {
    val typedValue = TypedValue()
    val theme: Theme = this.theme
    theme.resolveAttribute(colorId, typedValue, true)

    return typedValue.data
}