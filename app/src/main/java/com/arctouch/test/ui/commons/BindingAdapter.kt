package com.arctouch.test.ui.commons

import android.databinding.BindingAdapter
import android.view.View
import android.widget.TextView
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.model.Movie
import com.arctouch.test.extensions.hide
import com.arctouch.test.extensions.show
import com.arctouch.test.vm.Resource
import com.facebook.drawee.view.SimpleDraweeView


object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:item", "app:config")
    fun loadImage(view: SimpleDraweeView, item: Movie?, config: Config?) {
        config?.let {
            item?.let {
                val width = if (view.width > 0) view.width else 500
                val url : String? = item.posterPath?.let { config.getPosterUrl(width, it) } ?:
                        item.backdropPath?.let { config.getBackdropUrl(width, it) }
                url?.let {
                    view.setImageURI(it)
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:genres", "app:genres_ids")
    fun setGenre(view: TextView, genres: List<Genre>?, genreIds: List<Long>) {
        genres?.let {
            val names = genreIds.map { id ->
                genres.find { it.id == id }?.name
            }
            view.text = names.filterNotNull().joinToString(", ")
        }
    }

    @JvmStatic
    @BindingAdapter("app:showIf")
    fun showIf(view: View, show: Boolean) {
        if (show) view.show() else view.hide()
    }

    @JvmStatic
    @BindingAdapter("app:resourceText")
    fun resourceText(view: TextView, resource: Resource<Any>?) {
        resource?.let { view.setText(resource.message) }
    }
}