package com.wafflestudio.seminar3.network

import com.wafflestudio.seminar3.model.Post
import retrofit2.http.GET
import retrofit2.http.POST

interface PostService {
    @GET("/posts")
    suspend fun getAllPost(): List<Post>
}

