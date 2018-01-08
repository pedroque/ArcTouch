package com.arctouch.test.data.entity.mapper


interface Mapper<out T, in Y> {
    fun transform(entity: Y) : T
}