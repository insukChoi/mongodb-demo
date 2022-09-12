package com.insuk.mongodemo.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TornadoCollectorRepository : MongoRepository<TornadoCollector, Long>