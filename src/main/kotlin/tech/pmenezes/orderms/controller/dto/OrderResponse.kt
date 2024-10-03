package tech.pmenezes.orderms.controller.dto

import java.math.BigDecimal

data class OrderResponse(
    val orderId: Long,
    val customerId: Long,
    val total: BigDecimal
)