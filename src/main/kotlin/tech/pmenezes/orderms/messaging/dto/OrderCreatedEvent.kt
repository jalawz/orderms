package tech.pmenezes.orderms.messaging.dto

import tech.pmenezes.orderms.entity.OrderEntity
import tech.pmenezes.orderms.entity.OrderItem
import java.math.BigDecimal

data class OrderCreatedEvent(
    val codigoPedido: Long,
    val codigoCliente: Long,
    val itens: List<OrderEventItems>
) {
    fun toEntity() = OrderEntity(
        orderId = codigoPedido,
        customerId = codigoCliente,
        items = itens.map { it.toEntity() }
    )
}

data class OrderEventItems(
    val produto: String,
    val quantidade: Int,
    val preco: BigDecimal
) {
    fun toEntity() = OrderItem(
        product = produto,
        quantity = quantidade,
        price = preco
    )
}