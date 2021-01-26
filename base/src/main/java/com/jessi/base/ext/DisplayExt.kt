package com.jessi.base.ext

import android.content.res.Resources
import android.util.TypedValue

/**
 *  屏幕 & 显示 扩展类
 */


/**
 * dp 转 px
 */
val Float.dp2px get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics
)

/**
 * px 转 dp
 */
val Float.px2dp get() = this * Resources.getSystem().displayMetrics.density + 0.5f

/**
 * sp 转 px
 */
val Float.sp2px get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)