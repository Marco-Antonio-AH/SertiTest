package com.example.serti.data.remote.mapper

import com.example.serti.data.remote.dto.SingleUserResponse
import com.example.serti.data.remote.dto.UserDto
import com.example.serti.model.UserModel


fun UserDto.toModel(): UserModel = UserModel(
    id = id,
    email = email,
    firstName = first_name,
    lastName = last_name,
    avatar = avatar
)

fun SingleUserResponse.toModel(): UserModel = data.toModel()
