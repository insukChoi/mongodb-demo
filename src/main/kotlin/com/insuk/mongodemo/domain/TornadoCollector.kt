package com.insuk.mongodemo.domain

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.math.BigDecimal

@Document(collection = "tornado_collection")
data class TornadoCollector(
    val channelType: String,
    val aid: String,
    val paymentInfo: PaymentInfo?,
    val settlementInfo: SettlementInfo?
): IdCollection()

data class PaymentInfo(
    val paymentMethodType: String,
    val paymentActionType: String,
    @Field("currency_type")
    val currency: String,
    val totalAmount: BigDecimal
)

data class SettlementInfo(
    val pgFeeAmount: BigDecimal
)