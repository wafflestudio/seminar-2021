package com.wafflestudio.seminar.common.exception

import java.lang.RuntimeException

open class WaffleException(private val detail: String) : RuntimeException(detail)
