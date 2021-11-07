package com.wafflestudio.seminar.global.common.dto

data class ListResponse<T>(
    val results: List<T>,
    val count: Int
)
{
    constructor(list: List<T>?) : this(
        list.orEmpty(),
        list?.size ?: 0
    )
}