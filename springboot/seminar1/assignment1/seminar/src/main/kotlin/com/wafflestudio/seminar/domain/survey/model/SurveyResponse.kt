package com.wafflestudio.seminar.domain.survey.model

import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


@Entity
class SurveyResponse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "os_id", referencedColumnName = "id")
    @NotNull
    var os: OperatingSystem? = null,

    @Column(name = "spring_exp")
    @NotNull
    @Min(0, message = "The value must be between 1 and 5")
    @Max(5, message = "The value must be between 1 and 5")
    var springExp: Int? = null,

    @Column(name = "rdb_exp")
    @NotNull
    @Min(0, message = "The value must be between 1 and 5")
    @Max(5, message = "The value must be between 1 and 5")
    var rdbExp: Int? = null,

    @Column(name = "programming_exp")
    @NotNull
    @Min(0, message = "The value must be between 1 and 5")
    @Max(5, message = "The value must be between 1 and 5")
    var programmingExp: Int? = null,

    @NotBlank
    var major: String? = null,

    @NotBlank
    var grade: String? = null,

    @Column(name = "backend_reason")
    var backendReason: String? = null,
    @Column(name = "waffle_reason")
    var waffleReason: String? = null,

    @Column(name = "something_to_say")
    var somethingToSay: String? = null,

    @NotNull
    var timestamp: LocalDateTime = LocalDateTime.now(),
)
