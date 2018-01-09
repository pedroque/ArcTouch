package com.arctouch.test.data.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import java.util.*


class Movie : BaseObservable(){
    var title: String? = null
    var posterPath: String? = null
    var backdropPath: String? = null
    var releaseDate: Date? = null
    var overview: String? = null
    lateinit var genreIds: MutableList<Long>

    @Bindable
    fun getReleaseYear(): String {
        releaseDate?.let {
            val calendar = Calendar.getInstance()
            calendar.time = it
            return calendar.get(Calendar.YEAR).toString()
        }
        return ""
    }
}