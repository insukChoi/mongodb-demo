package com.insuk.mongodemo.util

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoTestExtension : BeforeEachCallback {

    companion object {
        private val mongoContainer = MongoDBContainer(
            DockerImageName.parse("mongo:4.0.10")
        )

        class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                if (!mongoContainer.isRunning) {
                    mongoContainer.start()
                }

                TestPropertyValues.of(
                    mapOf(
                        "spring.data.mongodb.uri" to mongoContainer.replicaSetUrl
                    )
                ).applyTo(applicationContext.environment)
            }

        }
    }

    override fun beforeEach(context: ExtensionContext?) {
        if (!mongoContainer.isRunning) {
            mongoContainer.start()
        }
    }
}