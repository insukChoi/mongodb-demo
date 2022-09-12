package com.insuk.mongodemo.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.*
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

const val MONGO_TRANSACTION_MANAGER = "mongoTransactionManager"
const val BASE_PACKAGES = "com.insuk.mongodemo.domain"

@Configuration
@EnableMongoRepositories(basePackages = [BASE_PACKAGES])
@EnableMongoAuditing
@Profile("!test")
class MongodbConfiguration(
    private val mongoDataSourceProperty: MongoDataSourceProperty
) : AbstractMongoClientConfiguration() {

    @Bean(MONGO_TRANSACTION_MANAGER)
    fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager =
        MongoTransactionManager(dbFactory)

    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(mongoDataSourceProperty.uri)
        return MongoClients.create(
            MongoClientSettings.builder()
                .apply {
                    applyConnectionString(connectionString)
                    credential(
                        MongoCredential.createCredential(
                            mongoDataSourceProperty.username,
                            mongoDataSourceProperty.authSource,
                            mongoDataSourceProperty.password.toCharArray()
                        )
                    )
                }
                .build()
        )
    }

    override fun getDatabaseName() =
        mongoDataSourceProperty.database


    override fun mongoMappingContext(customConversions: MongoCustomConversions): MongoMappingContext =
        super.mongoMappingContext(customConversions).apply {
            setFieldNamingStrategy(SnakeCaseFieldNamingStrategy())
        }

    @Configuration
    @ConfigurationProperties(prefix = "mongodb")
    @PropertySources(
        value = [
            PropertySource(
                value = ["classpath:mongo/mongo-datasource.yml"],
                factory = YamlPropertySourceFactory::class
            ),
            PropertySource(
                value = ["classpath:mongo/mongo-datasource-\${spring.profiles.active}.yml"],
                ignoreResourceNotFound = true,
                factory = YamlPropertySourceFactory::class
            )
        ]
    )
    @Lazy
    class MongoDataSourceProperty {
        lateinit var uri: String
        lateinit var database: String
        lateinit var authSource: String
        lateinit var username: String
        lateinit var password: String
    }
}