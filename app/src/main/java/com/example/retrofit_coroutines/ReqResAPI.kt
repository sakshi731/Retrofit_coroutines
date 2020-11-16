package com.example.retrofit_coroutines

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqResAPI {
    @GET("users")
   suspend  fun getUsers(
        @Query("page") page: Int = 1
    ): Response<UsersResponse>

}