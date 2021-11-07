package com.wafflestudio.seminar.domain.survey.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.user.model.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


@Entity
class SurveyResponse(
    @ManyToOne
    @JoinColumn(name = "os_id", referencedColumnName = "id")
    @field:NotNull
    val os: OperatingSystem,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    val user: User?,

    @Column(name = "spring_exp")
    @field:NotNull
    @field:Min(0, message = "The value must be between 1 and 5")
    @field:Max(5, message = "The value must be between 1 and 5")
    val springExp: Int,

    @Column(name = "rdb_exp")
    @field:NotNull
    @field:Min(0, message = "The value must be between 1 and 5")
    @field:Max(5, message = "The value must be between 1 and 5")
    val rdbExp: Int,

    @Column(name = "programming_exp")
    @field:NotNull
    @field:Min(0, message = "The value must be between 1 and 5")
    @field:Max(5, message = "The value must be between 1 and 5")
    val programmingExp: Int,

    @field:NotBlank
    val major: String,

    @field:NotBlank
    val grade: String,

    @Column(name = "backend_reason")
    val backendReason: String? = null,
    @Column(name = "waffle_reason")
    val waffleReason: String? = null,

    @Column(name = "something_to_say")
    val somethingToSay: String? = null,

    @field:NotNull
    val timestamp: LocalDateTime = LocalDateTime.now(),
): BaseEntity()
