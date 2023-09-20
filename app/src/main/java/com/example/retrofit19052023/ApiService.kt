package com.example.retrofit19052023

import retrofit2.http.GET

interface ApiService {
    // End point
    @GET("posts")
    suspend fun getPost(): List<Post>
}