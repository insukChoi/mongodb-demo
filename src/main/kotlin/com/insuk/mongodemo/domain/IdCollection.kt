package com.insuk.mongodemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType

abstract class IdCollection(
    @Id
    @Field(name = "_id", targetType = FieldType.STRING)
    open var id: String? = null
)