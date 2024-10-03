package tech.pmenezes.orderms.controller.dto

data class ApiResponse<T>(
    val data: List<T>,
    val pagination: PaginationResponse,
    val summary: Map<String, Any>
)

data class PaginationResponse(
    val page: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int
)