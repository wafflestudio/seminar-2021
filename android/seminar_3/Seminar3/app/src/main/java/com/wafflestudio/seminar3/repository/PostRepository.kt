package com.wafflestudio.seminar3.repository

import com.wafflestudio.seminar3.model.Post
import com.wafflestudio.seminar3.network.PostService

class PostRepository constructor(private val postService: PostService) {
    suspend fun getAllPost(): List<Post> {
        return postService.getAllPost()
    }
}
