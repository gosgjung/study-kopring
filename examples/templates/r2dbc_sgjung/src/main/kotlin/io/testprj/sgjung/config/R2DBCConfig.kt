package io.testprj.sgjung.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
@EnableR2dbcRepositories(
    basePackages = [
        "io.testprj.sgjung"
    ],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ["io.testprj.sgjung.*.repository.*Repository"]
        )
    ]
)
@EnableR2dbcAuditing
class R2DBCConfig {

    @Bean
    fun init(connectionFactory: ConnectionFactory) =
        ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setDatabasePopulator(
                ResourceDatabasePopulator(
//                    ClassPathResource("sql/init/schema.sql")
                    ClassPathResource("mysql-schema.sql")
                )
            )
        }
}