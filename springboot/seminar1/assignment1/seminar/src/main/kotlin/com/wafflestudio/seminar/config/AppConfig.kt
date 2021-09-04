package com.wafflestudio.seminar.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    public fun modelMapper(): ModelMapper {
        return ModelMapper();
    }
}