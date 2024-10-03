package tech.pmenezes.orderms.entity

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.math.BigDecimal

@Document(collection = "orders")
data class OrderEntity(
    @MongoId
    val orderId: Long,
    @Indexed(name = "customer_id_index")
    val customerId: Long,
    @Field(targetType = FieldType.DECIMAL128)
    val total: BigDecimal? = BigDecimal.ZERO,
    val items: List<OrderItem>
)

data class OrderItem(
    val product: String,
    val quantity: Int,
    @Field(targetType = FieldType.DECIMAL128)
    val price: BigDecimal
)
