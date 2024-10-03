package tech.pmenezes.orderms.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.MongoRepository
import tech.pmenezes.orderms.controller.dto.OrderResponse
import tech.pmenezes.orderms.entity.OrderEntity

interface OrderRepository: MongoRepository<OrderEntity, Long> {

    fun findAllByCustomerId(customerId: Long, pageRequest: PageRequest): Page<OrderEntity>
}