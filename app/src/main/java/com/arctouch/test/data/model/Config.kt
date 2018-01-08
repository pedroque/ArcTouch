package com.arctouch.test.data.model

import io.realm.RealmList
import io.realm.RealmObject
import java.util.*


open class Config : RealmObject() {
    lateinit var date: Date
    var baseUrl: String? = null
    var secureBaseUrl: String? = null
    var backdropSizes: RealmList<String>? = null
    var posterSizes: RealmList<String>? = null

    fun getBackdropUrl(width: Int, path: String) =
            getUrl(backdropSizes, width, path)

    fun getPosterUrl(width: Int, path: String) =
            getUrl(posterSizes, width, path)

    private fun getUrl(list: RealmList<String>?, width: Int, path: String) =
            list?.let { baseUrl + getPreferredSize(it, width) + path }

    private fun getPreferredSize(list: RealmList<String>?, width: Int) = list?.filter { it.startsWith("w") }
            ?.first { it.substring(1).toInt() <= width }
}