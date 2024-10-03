package tech.pmenezes.orderms.controller

import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tech.pmenezes.orderms.controller.dto.ApiResponse
import tech.pmenezes.orderms.controller.dto.OrderResponse
import tech.pmenezes.orderms.controller.dto.PaginationResponse
import tech.pmenezes.orderms.service.OrderService

@RestController
class OrderController(
    private val orderService: OrderService
) {

    @GetMapping("/customers/{customerId}/orders")
    fun listOrders(
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "pageSize", defaultValue = "10") pageSize: Int,
        @PathVariable(name = "customerId") customerId: Long
    ): ResponseEntity<ApiResponse<OrderResponse>> {

        val pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize))
        val total = orderService.findTotalOnOrdersByCustomerId(customerId)

        return ResponseEntity.ok(ApiResponse(
            data = pageResponse.content,
            pagination = PaginationResponse(
                page = pageResponse.number,
                pageSize = pageResponse.size,
                totalElements = pageResponse.totalElements,
                totalPages = pageResponse.totalPages
            ),
            summary = mapOf("totalOnOrders" to total)
        ))
    }
}