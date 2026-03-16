package com.example.jsontypicodeusers.domain

import com.example.jsontypicodeusers.presentation.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

private val api = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .addConverterFactory(GsonConverterFactory.create()).build()

interface JsonTypiCodeAPI {
    // GET:: https://jsonplaceholder.typicode.com/users
    @GET("users")
    suspend fun getUsers() : List<User>

}

val typiCodeService = api.create<JsonTypiCodeAPI>()
// typiCodeService.getUsers() => request GET::https://jsonplaceholder.typicode.com/users