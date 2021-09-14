package com.wafflestudio.seminar.domain.user

import com.wafflestudio.seminar.domain.model.BaseEntity
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar_user")
class User(
    @Column(unique = true)
    @field:NotBlank
    val email: String,

    @Column
    @field:NotBlank
    val password: String,

    @Column
    @field:NotNull
    val roles: String = "",

    ) : BaseEntity()
