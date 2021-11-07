package com.wafflestudio.seminar.global.security.dto

import org.springframework.lang.Nullable

class LoginRequest (
    var email: String? = null,

    var password: String? = null
)