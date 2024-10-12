package tech.pmenezes.orderms.service

import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.group
import org.springframework.data.mongodb.core.aggregation.Aggregation.match
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import tech.pmenezes.orderms.controller.dto.OrderResponse
import tech.pmenezes.orderms.messaging.dto.OrderCreatedEvent
import tech.pmenezes.orderms.repository.OrderRepository
import java.math.BigDecimal

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val mongoTemplate: MongoTemplate
) {

    fun save(event: OrderCreatedEvent) {
        val eventEntity = event.toEntity()
        orderRepository.save(eventEntity.copy(total = getTotal(event)?: BigDecimal.ZERO))
    }

    fun findAllByCustomerId(customerId: Long, pageRequest: PageRequest): Page<OrderResponse> {
        val orders = orderRepository.findAllByCustomerId(customerId, pageRequest)
        return orders.map {
            OrderResponse(
                customerId = it.customerId,
                orderId = it.orderId,
                total = it.total?: BigDecimal.ZERO
            )
        }
    }

    fun findTotalOnOrdersByCustomerId(customerId: Long): BigDecimal {
        val aggregation = Aggregation.newAggregation(
             match(Criteria.where("customerId").`is`(customerId)),
            group().sum("total").`as`("total")
        )

        val response = mongoTemplate.aggregate(aggregation, "orders", Document::class.java)

        return BigDecimal(response.uniqueMappedResult?.get("total").toString())
    }

    private fun getTotal(event: OrderCreatedEvent) =
        event.itens.map {
            it.preco.multiply(BigDecimal(it.quantidade))
        }.reduce(BigDecimal::add)
}