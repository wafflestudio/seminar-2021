package com.wafflestudio.assignment3skeleton.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Already Completed
@JsonClass(generateAdapter = true)
data class Lecture(
    @Json(name = "instructor")
    val instructor: String,
    @Json(name = "course_title")
    val title: String,
    @Json(name = "credit")
    val credit: Int
)
