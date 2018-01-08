package com.arctouch.test.dao

import io.realm.Realm
import io.realm.RealmObject

abstract class BaseDAO<T : RealmObject>(protected var mClass: Class<T>) {

    fun save(list: List<T>) {
        Realm.getDefaultInstance().use { realm ->
            realm!!.executeTransaction { it.insertOrUpdate(list) }
        }
    }

    fun save(data: T) {
        Realm.getDefaultInstance().use { realm ->
            realm!!.executeTransaction { it.insertOrUpdate(data) }
        }
    }

    fun findAll(): List<T>? {
        Realm.getDefaultInstance().use { realm ->
            val result = realm.where(mClass).findAll()
            return if (result == null) {
                null
            } else {
                realm.copyFromRealm(result)
            }
        }
    }

    fun find(): T? {
        Realm.getDefaultInstance().use { realm ->
            val result = realm!!.where(mClass).findFirst()
            return if (result == null) {
                null
            } else {
                realm.copyFromRealm(result)
            }
        }
    }

    fun delete(data: T) {
        data.deleteFromRealm()
    }

    fun delete() {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { it.delete(mClass) }
        }
    }
}
