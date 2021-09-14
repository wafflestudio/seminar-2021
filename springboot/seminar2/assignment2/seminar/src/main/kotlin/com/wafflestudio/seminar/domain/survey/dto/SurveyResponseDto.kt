package com.wafflestudio.seminar.domain.survey.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.user.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SurveyResponseDto {
    data class Response(
        val id: Long,
        val os: OperatingSystemDto.Response,
        val springExp: Int,
        val rdbExp: Int,
        val programmingExp: Int,
        val major: String,
        val grade: String,
        val backendReason: String?,
        val waffleReason: String?,
        val somethingToSay: String?,
        val timestamp: LocalDateTime
    ) {
        constructor(surveyResponse: SurveyResponse) : this(
            surveyResponse.id,
            OperatingSystemDto.Response(surveyResponse.os),
            surveyResponse.springExp,
            surveyResponse.rdbExp,
            surveyResponse.programmingExp,
            surveyResponse.major,
            surveyResponse.grade,
            surveyResponse.backendReason,
            surveyResponse.waffleReason,
            surveyResponse.somethingToSay,
            surveyResponse.timestamp
        )
    }

    data class CreateRequest(
        @field:NotBlank
        val os: String,

        @field:NotNull
        val springExp: Int,

        @field:NotNull
        val rdbExp: Int,

        @field:NotNull
        val programmingExp: Int,

        @field:NotBlank
        val major: String,

        @field:NotBlank
        val grade: String,

        val backendReason: String? = null,
        val waffleReason: String? = null,
        val somethingToSay: String? = null,
    )
}
