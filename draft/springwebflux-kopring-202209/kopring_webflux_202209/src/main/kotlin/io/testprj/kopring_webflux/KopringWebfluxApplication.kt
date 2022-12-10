package io.testprj.kopring_webflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [R2dbcAutoConfiguration::class])
class KopringWebfluxApplication

fun main(args: Array<String>) {
	runApplication<KopringWebfluxApplication>(*args)
}
