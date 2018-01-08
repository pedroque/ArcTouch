package com.arctouch.test.data.entity.mapper

import com.arctouch.test.data.entity.ConfigEntity
import com.arctouch.test.data.model.Config
import io.realm.RealmList
import java.util.*
import javax.inject.Inject


class ConfigMapper @Inject constructor() : Mapper<Config, ConfigEntity> {
    override fun transform(entity: ConfigEntity): Config {
        val config = Config()
        config.date = Date()
        config.baseUrl = entity.baseUrl
        config.secureBaseUrl = entity.secureBaseUrl
        entity.backdropSizes?.let {
            val list = RealmList<String>()
            it.forEach {
                list.add(it)
            }
            config.backdropSizes = list
        }
        entity.posterSizes?.let {
            val list = RealmList<String>()
            it.forEach {
                list.add(it)
            }
            config.posterSizes = list
        }
        return config
    }
}