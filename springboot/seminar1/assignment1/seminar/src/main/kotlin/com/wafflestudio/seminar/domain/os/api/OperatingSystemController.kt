package com.wafflestudio.seminar.domain.os.api

import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.os.service.OperatingSystemService
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/os")
class OperatingSystemController(
    private val operatingSystemService: OperatingSystemService,
    private val modelMapper: ModelMapper
) {
    @GetMapping("/")
    fun getOperatingSystems(): List<OperatingSystemDto.Response> {
        val operatingSystems = operatingSystemService.getAllOperatingSystems()
        return operatingSystems.map { modelMapper.map(it, OperatingSystemDto.Response::class.java) }
    }

    @GetMapping("/{id}/")
    fun getOperatingSystem(@PathVariable("id") id: Long): ResponseEntity<OperatingSystemDto.Response> {
        return try {
            val operatingSystem = operatingSystemService.getOperatingSystemById(id)
            val responseBody = modelMapper.map(operatingSystem, OperatingSystemDto.Response::class.java)
            ResponseEntity.ok(responseBody)
        } catch (e: OsNotFoundException){
            ResponseEntity.notFound().build()
        }
    }
}