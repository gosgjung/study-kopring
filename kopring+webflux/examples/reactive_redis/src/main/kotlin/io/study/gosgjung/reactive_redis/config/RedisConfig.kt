package io.study.gosgjung.reactive_redis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig (
){

    @Bean(name = ["playerAgeMap"])
    fun playerAgeMap(
        reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory
    ) : ReactiveRedisTemplate<String, String>{
        val keySerializer: StringRedisSerializer = StringRedisSerializer()
        val valSerializer: StringRedisSerializer = StringRedisSerializer()

        val builder: RedisSerializationContext.RedisSerializationContextBuilder<String, String> =
            RedisSerializationContext.newSerializationContext(keySerializer)

        val context : RedisSerializationContext<String, String> = builder
            .key(keySerializer)
            .hashKey(keySerializer)
            .value(valSerializer)
            .hashValue(valSerializer)
            .build()

        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, context)
    }


}