package com.arctouch.test.ui.adapter

import com.arctouch.test.R


abstract class EndlessAdapter : ArcTouchAdapter() {
    var loading: Boolean = false
        set (value) {
            if (field != value) {
                field = value
                if (value) notifyItemInserted(itemCount - 1) else notifyItemRemoved(itemCount)
            }
        }

    override fun getLayoutIdForPosition(position: Int) =
            if (position == itemCount - 1 && loading) R.layout.item_progress else getItemIdForPosition(position)

    abstract fun getItemIdForPosition(position: Int): Int

    override fun getItemCount() =
            if (loading) items?.size ?: 0 + 1 else super.getItemCount()

}