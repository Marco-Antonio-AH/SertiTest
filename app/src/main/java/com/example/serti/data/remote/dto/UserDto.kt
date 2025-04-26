package com.example.serti.data.remote.dto


data class UserDto(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

data class UsersResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UserDto>
)

data class SingleUserResponse(
    val data: UserDto
)
