package com.wafflestudio.seminar.domain.survey.exception

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.WaffleException

class SurveyResponseNotFoundException(detail: String="") :
    DataNotFoundException(ErrorType.SURVEY_RESPONSE_NOT_FOUND, detail)