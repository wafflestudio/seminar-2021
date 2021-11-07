package com.wafflestudio.seminar.domain.os.dto

import javax.validation.constraints.Min

class OperatingSystemDto {
    data class Response(
        var id: Long? = null,
        var name: String = "",
        var description: String = "",
        var price: Long? = null,
    )

}
