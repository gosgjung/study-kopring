package io.study.gosgjung.reactive_redis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Profile("!test")
@Configuration
class ReactiveRedisConnectionFactory (
    @Value("\${just-test.redis.host}")
    val host: String,

    @Value("\${just-test.redis.port}")
    val port: Int
){
    @Bean(name = ["reactiveRedisConnectionFactory"])
    fun reactiveRedisConnectionFactory() : ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }
}