package io.kopring.multimodule.kopringmultimodule.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(
    basePackages = ["io.kopring.multimodule.kopringmultimodule"]
)
@EnableJpaRepositories(
    basePackages = ["io.kopring.multimodule.kopringmultimodule"]
)
@Configuration
class JpaConfig {
}