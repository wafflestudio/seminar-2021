package com.wafflestudio.seminar

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class IntegrationTests(private val webClient: WebTestClient) {

    @Test
    fun get() {
    }

}
