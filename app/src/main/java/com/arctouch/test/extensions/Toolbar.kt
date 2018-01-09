package com.arctouch.test.extensions

import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.ViewTreeObserver
import android.view.Window
import com.arctouch.test.ui.commons.ViewHelper


fun Toolbar.setTransparentStatusBarEnabled(window: Window){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                viewTreeObserver.removeOnPreDrawListener(this)
                val statusTopPadding = ViewHelper.getStatusBarHeight(window)
                layoutParams.height = layoutParams.height + statusTopPadding
                addPaddingTop(statusTopPadding)
                return true
            }
        })
    }
}