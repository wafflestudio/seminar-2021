package com.wafflestudio.seminar.global.common.dto

data class ListResponse<T>(
    var results: List<T> = listOf(),
    var count: Int
)
{
    constructor(results: List<T>?) : this(
        results.orEmpty(),
        results?.size ?: 0
    )
}