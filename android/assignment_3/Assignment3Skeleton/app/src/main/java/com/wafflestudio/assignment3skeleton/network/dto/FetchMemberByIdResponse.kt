package com.wafflestudio.assignment3skeleton.network.dto

import com.wafflestudio.assignment3skeleton.model.Member

// Use if you need it
data class FetchMemberByIdResponse (
    val statusCode: Int,
    val body: Member
)