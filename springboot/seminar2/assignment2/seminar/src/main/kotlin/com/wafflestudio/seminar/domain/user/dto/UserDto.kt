package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.user.User
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

class UserDto {
    data class Response(
        val id: Long,
        val email: String,
        val password: String,
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            password = user.password
        )
    }

    data class SignupRequest(
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val password: String,
    )
}