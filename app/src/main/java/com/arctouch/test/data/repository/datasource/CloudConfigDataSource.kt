package com.arctouch.test.data.repository.datasource

import com.arctouch.test.data.entity.mapper.ConfigMapper
import com.arctouch.test.data.model.Config
import com.arctouch.test.di.Named
import com.arctouch.test.data.net.ConfigServices
import io.reactivex.Observable
import javax.inject.Inject


class CloudConfigDataSource @Inject constructor(
        @Named("api_key")
        private val apiKey: String,
        private val services: ConfigServices,
        private val mapper: ConfigMapper) : ConfigDataSource {


    override fun getConfig(): Observable<Config> {
        return services.getConfig(apiKey).map { mapper.transform(it.images) }
    }

}