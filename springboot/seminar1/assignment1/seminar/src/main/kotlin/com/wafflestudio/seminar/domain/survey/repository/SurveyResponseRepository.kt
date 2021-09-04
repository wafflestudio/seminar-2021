package com.wafflestudio.seminar.domain.survey.repository

import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import org.springframework.data.jpa.repository.JpaRepository

//Spring Data Jpa
interface SurveyResponseRepository : JpaRepository<SurveyResponse, Long?> {
    fun findAllByOs(os: OperatingSystem): List<SurveyResponse>

    fun findAllByBackendReasonContaining(str: String): List<SurveyResponse>

}
