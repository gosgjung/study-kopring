package io.study.gosgjung.reactive_redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.study.gosgjung.reactive_redis.study.StockDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
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

    @Bean(name = ["redisObjectMapper"])
    fun redisObjectMapper() : ObjectMapper{
        val kotlinModule = KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()

        return ObjectMapper().registerModule(kotlinModule)
    }

    @Bean(name = ["stockMap"])
    fun stockMap(
        reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory,
        @Qualifier("redisObjectMapper") objectMapper: ObjectMapper
    ) : ReactiveRedisTemplate<String, StockDto>{
        val keySerializer: StringRedisSerializer = StringRedisSerializer()
        val valSerializer: Jackson2JsonRedisSerializer<StockDto> = Jackson2JsonRedisSerializer<StockDto>(StockDto::class.java)
        valSerializer.setObjectMapper(objectMapper)

        val builder: RedisSerializationContextBuilder<String, StockDto> =
            RedisSerializationContext.newSerializationContext<String, StockDto>(keySerializer)

        val context: RedisSerializationContext<String, StockDto> = builder
            .key(keySerializer)
            .value(valSerializer)
            .hashKey(keySerializer)
            .hashValue(valSerializer)
            .build()

        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, context)
    }
}