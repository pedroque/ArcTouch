package com.arctouch.test.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Genre : RealmObject {
    @PrimaryKey
    var id: Long = 0
    var name: String? = null

    constructor() : super()

    constructor(id: Long, name: String?) : super() {
        this.id = id
        this.name = name
    }
}