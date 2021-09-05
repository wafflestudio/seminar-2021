package com.wafflestudio.seminar.domain.os.repository

import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import org.springframework.data.jpa.repository.JpaRepository

interface OperatingSystemRepository : JpaRepository<OperatingSystem, Long?> {
    fun findByNameEquals(name: String): OperatingSystem?
}