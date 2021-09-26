package com.wafflestudio.seminar.global.common.exception

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    @JsonProperty("error_code")
    val errorCode: Int,
    @JsonProperty("error_message")
    val errorMessage: String = "",
    val detail: String = ""
)
