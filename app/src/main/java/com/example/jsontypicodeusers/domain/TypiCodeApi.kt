package com.example.jsontypicodeusers.domain

import com.example.jsontypicodeusers.presentation.AddUserReq
import com.example.jsontypicodeusers.presentation.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private val api = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .addConverterFactory(GsonConverterFactory.create()).build()

interface JsonTypiCodeAPI {
    // GET:: https://jsonplaceholder.typicode.com/users
    @GET("users")
    suspend fun getUsers() : List<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int)

    @POST("users")
    suspend fun createUser(@Body newUser: AddUserReq) : User

}

val typiCodeService = api.create<JsonTypiCodeAPI>()
// typiCodeService.getUsers() => request GET::https://jsonplaceholder.typicode.com/users