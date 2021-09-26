package com.wafflestudio.seminar.global.common.exception

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SurveyControllerAdvice() {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    @ExceptionHandler(value = [DataNotFoundException::class])
    fun notfound(e: WaffleException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.NOT_FOUND)

    @ExceptionHandler(value = [InvalidRequestException::class])
    fun badRequest(e: WaffleException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [NotAllowedException::class])
    fun notAllowed(e: WaffleException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.FORBIDDEN)

    @ExceptionHandler(value = [RequestConflictException::class])
    fun conflict(e: WaffleException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.CONFLICT)

    @ExceptionHandler(value = [Exception::class])
    fun internalError(e: Exception) {
        logger.debug("{}", e.toString())
        ResponseEntity(ErrorResponse(-1, "서버 오류", ""), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
