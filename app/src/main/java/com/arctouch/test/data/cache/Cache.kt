package com.arctouch.test.data.cache


interface Cache<T> {
    fun save(t: T)
    fun delete()
    fun isExpired(): Boolean
    fun get(): T?
}