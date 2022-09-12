package com.insuk.mongodemo.config

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import java.io.FileNotFoundException
import java.util.Properties

class YamlPropertySourceFactory : PropertySourceFactory {
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        val properties: Properties =
            try {
                YamlPropertiesFactoryBean()
                    .apply {
                        setResources(resource.resource)
                        afterPropertiesSet()
                    }.`object`!!
            } catch (e: IllegalStateException) {
                if (e.cause is FileNotFoundException) {
                    throw e.cause as FileNotFoundException
                }

                throw e
            }

        val sourceName: String = (name ?: resource.resource.filename) as String

        return PropertiesPropertySource(sourceName, properties)
    }
}