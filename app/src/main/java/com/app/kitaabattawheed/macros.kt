package com.app.kitaabattawheed

import android.content.Context
import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.ColorInt


fun Context.attrColor(colorId: Int): Int {
    val typedValue = TypedValue()
    val theme: Theme = this.getTheme()
    theme.resolveAttribute(colorId, typedValue, true)
    @ColorInt val color = typedValue.data

    return color
}