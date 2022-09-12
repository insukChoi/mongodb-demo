package com.insuk.mongodemo.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL

@Deprecated("실제 로컬DB 테스트")
@SpringBootTest
@ActiveProfiles("local")
@TestConstructor(autowireMode = ALL)
class TornadoCollectorIntegrationTest(
    private val tornadoCollectorRepository: TornadoCollectorRepository
) {

    @Test
    fun devRepositoryTest() {
        val findAll = tornadoCollectorRepository.findAll()
        findAll.size shouldBe 2
    }
}