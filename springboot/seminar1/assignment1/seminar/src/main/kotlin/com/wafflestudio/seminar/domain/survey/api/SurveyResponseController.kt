package com.wafflestudio.seminar.domain.survey.api

import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.domain.survey.exception.SurveyNotFoundException
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.service.SurveyResponseService
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/results")
class SurveyResponseController(
    private val surveyResponseService: SurveyResponseService,
    private val modelMapper: ModelMapper
) {
    @GetMapping("/")
    fun getSurveyResponses(@RequestParam(required = false) os: String?): ResponseEntity<List<SurveyResponseDto.Response>> {
        return try {
            val surveyResponses =
                if (os != null) surveyResponseService.getSurveyResponsesByOsName(os)
                else surveyResponseService.getAllSurveyResponses()
            val responseBody = surveyResponses.map { modelMapper.map(it, SurveyResponseDto.Response::class.java) }
            ResponseEntity.ok(responseBody)
        } catch (e: OsNotFoundException) {
            ResponseEntity.notFound().build()
        }
        // AOP를 적용해 exception handling을 따로 하도록 고쳐보셔도 됩니다.
    }

    @GetMapping("/{id}/")
    fun getSurveyResponse(@PathVariable("id") id: Long): ResponseEntity<SurveyResponseDto.Response> {
        return try {
            val surveyResponse = surveyResponseService.getSurveyResponseById(id)
            val responseBody = modelMapper.map(surveyResponse, SurveyResponseDto.Response::class.java)
            ResponseEntity.ok(responseBody)
        } catch (e: SurveyNotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/")
    fun addSurveyResponse(
        @RequestBody @Valid body: SurveyResponseDto.CreateRequest,
        @RequestHeader("User-Id") userId: Long
    ): SurveyResponseDto.Response {
        //TODO: API 생성
//        val newSurveyResponse = modelMapper.map(body, SurveyResponse::class.java)
        return SurveyResponseDto.Response()
    }

}
