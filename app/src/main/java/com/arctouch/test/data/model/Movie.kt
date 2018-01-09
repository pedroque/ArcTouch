package com.arctouch.test.data.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import java.util.*


class Movie : BaseObservable, Parcelable {
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

    constructor() : super()

    constructor(source: Parcel) : super(){
        title = source.readString()
        posterPath = source.readString()
        backdropPath = source.readString()
        overview = source.readString()
        val date = source.readLong()
        if (date > 0) {
            releaseDate = Date(date)
        }
        genreIds = mutableListOf()
        source.readList(genreIds, Long::class.java.classLoader)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        dest.writeString(title)
        dest.writeString(posterPath)
        dest.writeString(backdropPath)
        dest.writeString(overview)
        dest.writeLong(if (releaseDate == null) -1 else releaseDate!!.time)
        dest.writeList(genreIds)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}