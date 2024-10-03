package tech.pmenezes.orderms.messaging

import org.apache.logging.slf4j.SLF4JLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import tech.pmenezes.orderms.config.RabbitMqConfig.Companion.ORDER_CREATED_QUEUE
import tech.pmenezes.orderms.messaging.dto.OrderCreatedEvent
import tech.pmenezes.orderms.service.OrderService

@Component
class OrderCreatedListener(
    private val orderService: OrderService
) {

    private val log = LoggerFactory.getLogger(OrderCreatedListener::class.java)

    @RabbitListener(queues = [ORDER_CREATED_QUEUE])
    fun orderCreatedEventListener(message: Message<OrderCreatedEvent>) {
        log.info("Message consumed: $message")
        orderService.save(message.payload)
    }
}