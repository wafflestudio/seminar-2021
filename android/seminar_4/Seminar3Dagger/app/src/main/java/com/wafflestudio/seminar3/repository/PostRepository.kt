package com.wafflestudio.seminar3.repository

import com.wafflestudio.seminar3.model.Post
import com.wafflestudio.seminar3.network.PostService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(private val postService: PostService) {
    suspend fun getAllPost(): List<Post> {
        return postService.getAllPost()
    }
}
