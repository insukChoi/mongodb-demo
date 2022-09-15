package com.insuk.mongodemo.domain

import com.insuk.mongodemo.util.MongoTestExtension
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.shouldHaveLength
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.*
import java.math.BigDecimal
import java.math.BigDecimal.valueOf

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MongoTestExtension::class)
@TestConstructor(autowireMode = ALL)
@ContextConfiguration(initializers = [MongoTestExtension.Companion.Initializer::class])
internal class TornadoCollectorRepositoryTest(
    private val tornadoCollectorRepository: TornadoCollectorRepository
) {

    @BeforeEach
    fun init() {
        tornadoCollectorRepository.deleteAll()
    }

    @Test
    fun repositoryTest() {
        tornadoCollectorRepository.save(
            givenCollectorMock()
        )

        val findAll = tornadoCollectorRepository.findAll()
        findAll.size shouldBe 1
        findAll[0].id.shouldNotBeNull()
        findAll[0] shouldBe givenCollectorMock()
    }

    private fun givenCollectorMock() =
        TornadoCollector(
            channelType = "AMD",
            aid = "aid111",
            PaymentInfo(
                paymentMethodType = "MONEY",
                paymentActionType = "PAYMENT",
                currency = "KRW",
                totalAmount = BigDecimal.TEN
            ),
            SettlementInfo(
                pgFeeAmount = PG_FEE_AMOUNT
            )
        )

    companion object {
        private val PG_FEE_AMOUNT = valueOf(0.1)
    }
}