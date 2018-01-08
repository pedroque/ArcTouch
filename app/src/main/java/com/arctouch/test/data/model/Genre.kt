package com.arctouch.test.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Genre : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String? = null
}