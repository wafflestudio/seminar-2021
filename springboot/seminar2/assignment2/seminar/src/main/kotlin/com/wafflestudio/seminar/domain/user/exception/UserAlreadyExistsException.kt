package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ConflictException
import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType


class UserAlreadyExistsException(detail: String="") :
    ConflictException(ErrorType.USER_ALREADY_EXISTS, detail)