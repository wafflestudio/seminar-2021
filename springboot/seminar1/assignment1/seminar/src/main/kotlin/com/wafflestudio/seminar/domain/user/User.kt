package com.wafflestudio.seminar.domain.user

import com.wafflestudio.seminar.domain.model.BaseEntity
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar_user")
class User(
    @Column(unique = true)
    @NotBlank
    val username: String,

    @Column
    @NotBlank
    val password: String,

    @Column
    @NotNull
    val roles: String="",

) : BaseEntity()
