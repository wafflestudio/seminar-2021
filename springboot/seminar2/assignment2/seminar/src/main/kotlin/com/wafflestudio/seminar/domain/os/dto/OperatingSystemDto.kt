package com.wafflestudio.seminar.domain.os.dto

import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import javax.validation.constraints.Min

class OperatingSystemDto {
    data class Response(
        val id: Long,
        val name: String,
        val description: String,
        val price: Long,
    ) {
        constructor(os: OperatingSystem) : this(
            os.id,
            os.name,
            os.description,
            os.price
        )
    }
}
