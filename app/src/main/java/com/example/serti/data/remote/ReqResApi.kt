package com.example.serti.data.remote

import com.example.serti.data.remote.dto.SingleUserResponse
import com.example.serti.data.remote.dto.UsersResponse
import com.example.serti.data.remote.dto.reponse.LoginResponse
import com.example.serti.data.remote.dto.reponse.RegisterResponse
import com.example.serti.data.remote.dto.request.LoginRequest
import com.example.serti.data.remote.dto.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqResApi {
    @POST("login")
    suspend fun login(@Body creds: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body creds: RegisterRequest): RegisterResponse

    @GET("users")
    suspend fun listUsers(@Query("page") page: Int): UsersResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): SingleUserResponse
}
