package com.arctouch.test.ui.adapter

import android.databinding.ViewDataBinding
import com.arctouch.test.BR
import com.arctouch.test.R
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter : EndlessAdapter() {

    var config: Config? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var genres: List<Genre>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemIdForPosition(position: Int) = R.layout.item_movie

    override fun getViewHolderForLayout(layout: Int, binding: ViewDataBinding) = MovieViewHolder(binding)

    inner class MovieViewHolder(private val binding: ViewDataBinding) : ViewHolder(binding) {

        init {
            itemView?.item?.setOnClickListener {
                if (adapterPosition != -1) clickSubject.onNext(adapterPosition)
            }
            itemView?.item?.setOnLongClickListener {
                if (adapterPosition != -1) longClickSubject.onNext(adapterPosition)
                longClickSubject.hasObservers()
            }
        }

        override fun bind(o: Any) {
            binding.setVariable(BR.config, config)
            binding.setVariable(BR.genres, genres)
            super.bind(o)
        }
    }
}