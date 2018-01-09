package com.arctouch.test.extensions

import android.view.View
import com.arctouch.test.ui.commons.ViewHelper


fun View.show() {
    ViewHelper.showViewAnimated(this)
}

fun View.hide() {
    ViewHelper.hideViewAnimated(this)
}

fun View.addPaddingTop(extraPaddingTop: Int) {
    setPadding(paddingLeft,
            paddingTop + extraPaddingTop,
            paddingRight,
            paddingBottom)
}