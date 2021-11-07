package com.wafflestudio.seminar.domain.survey.service

import com.wafflestudio.seminar.domain.os.repository.OperatingSystemRepository
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.survey.exception.SurveyResponseNotFoundException
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.repository.SurveyResponseRepository
import com.wafflestudio.seminar.domain.user.model.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SurveyResponseService(
    private val surveyResponseRepository: SurveyResponseRepository,
    private val operatingSystemRepository: OperatingSystemRepository,
) {
    fun getAllSurveyResponses(): List<SurveyResponse> {
        return surveyResponseRepository.findAll()
    }

    fun getSurveyResponsesByOsName(name: String): List<SurveyResponse> {
        val os = operatingSystemRepository.findByNameEquals(name) ?: throw OsNotFoundException()
        return surveyResponseRepository.findAllByOs(os)
    }

    fun getSurveyResponseById(id: Long): SurveyResponse {
        return surveyResponseRepository.findByIdOrNull(id) ?: throw SurveyResponseNotFoundException()
    }

    fun createSurveyResponse(surveyResponseCreateRequest: SurveyResponseDto.CreateRequest, user: User): SurveyResponse {
        return surveyResponseCreateRequest.let {
            val os = operatingSystemRepository.findByNameEquals(it.os) ?: throw OsNotFoundException()
            surveyResponseRepository.save(
                SurveyResponse(
                    os,
                    user,
                    it.springExp,
                    it.rdbExp,
                    it.programmingExp,
                    it.major,
                    it.grade,
                    it.backendReason,
                    it.waffleReason,
                    it.somethingToSay
                )
            )
        }
    }
}
