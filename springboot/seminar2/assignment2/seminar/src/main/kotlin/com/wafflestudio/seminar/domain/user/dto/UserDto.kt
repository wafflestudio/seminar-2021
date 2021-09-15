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
        val name: String,
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            name = user.name
        )
    }

    data class SignupRequest(
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val password: String,
    )
}