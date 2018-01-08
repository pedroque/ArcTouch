package com.arctouch.test.ui.commons

import android.R
import android.animation.Animator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

object ViewHelper {

    fun hideViewAnimated(view: View, duration: Int, hideState: Int) {
        hideViewAnimated(view, duration, HideViewAnimatorListener(view, hideState))
    }

    fun hideViewAnimated(view: View, hideState: Int) {
        hideViewAnimated(view, HideViewAnimatorListener(view, hideState))
    }

    fun hideViewAnimated(view: View, listener: Animator.AnimatorListener) {
        toggleViewVisibilityAnimated(view, view.resources.getInteger(R.integer.config_shortAnimTime), listener, 0f)
    }

    fun hideViewAnimated(view: View, duration: Int, listener: Animator.AnimatorListener) {
        toggleViewVisibilityAnimated(view, duration, listener, 0f)
    }

    fun showViewAnimated(view: View) {
        showViewAnimated(view, ShowViewAnimatorListener(view))
    }

    fun showViewAnimated(view: View, duration: Int) {
        showViewAnimated(view, duration, ShowViewAnimatorListener(view))
    }

    fun showViewAnimated(view: View, duration: Int, listener: Animator.AnimatorListener) {
        toggleViewVisibilityAnimated(view, duration, listener, 1f)
    }

    fun showViewAnimated(view: View, listener: Animator.AnimatorListener) {
        toggleViewVisibilityAnimated(view, view.resources.getInteger(R.integer.config_shortAnimTime), listener, 1f)
    }

    fun hideViewAnimated(view: View) {
        hideViewAnimated(view, View.GONE)
    }


    private fun toggleViewVisibilityAnimated(view: View, duration: Int, listener: Animator.AnimatorListener, alpha: Float) {
        view.animate().alpha(alpha).translationY(0f).setDuration(duration.toLong()).setInterpolator(AccelerateDecelerateInterpolator()).setListener(listener)
    }
}