package io.study.gosgjung.reactive_redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveRedisApplication

fun main(args: Array<String>) {
	runApplication<ReactiveRedisApplication>(*args)
}
