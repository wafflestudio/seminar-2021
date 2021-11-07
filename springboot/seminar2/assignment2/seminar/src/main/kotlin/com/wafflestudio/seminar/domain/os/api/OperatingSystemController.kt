package com.wafflestudio.seminar.domain.os.api

import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.os.service.OperatingSystemService
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.global.common.dto.ListResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/os")
class OperatingSystemController(
    private val operatingSystemService: OperatingSystemService,
) {
    @GetMapping("/")
    fun getOperatingSystems(): ListResponse<OperatingSystemDto.Response> {
        val operatingSystems = operatingSystemService.getAllOperatingSystems()
        return ListResponse(operatingSystems.map { OperatingSystemDto.Response(it) })
    }

    @GetMapping("/{id}/")
    fun getOperatingSystem(@PathVariable("id") id: Long): OperatingSystemDto.Response {
        val operatingSystem = operatingSystemService.getOperatingSystemById(id)
        return OperatingSystemDto.Response(operatingSystem)
    }
}