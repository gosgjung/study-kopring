package io.testprj.kopring_webflux.config

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration
import dev.miku.r2dbc.mysql.MySqlConnectionFactory
import dev.miku.r2dbc.mysql.constant.SslMode
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.transaction.ReactiveTransactionManager
import java.time.Duration
import java.time.ZoneId

@Configuration
@EnableR2dbcRepositories(
    basePackages = [
        // 상위 패키지는 io.testprj.kopring_webflux
        "io.testprj.kopring_webflux"
    ],
    includeFilters = [
        // 엔티티 스캔은 io.testprj.kopring_webflux 밑의 모든 repository 라는 패키지
        // 그 밑에 Repository 라는 파일명으로 끝나는 모든 클래스들
        ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ["io.testprj.kopring_webflux.*.repository.*Repository"]
        )
    ]
)
@EnableR2dbcAuditing
class R2DBCConfig : AbstractR2dbcConfiguration(){
    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val configuration = MySqlConnectionConfiguration.builder()
            .host("127.0.0.1").user("root").password("1111").port(13306)
            .database("collector")
            .sslMode(SslMode.DISABLED)
            .serverZoneId(ZoneId.of("Asia/Seoul"))
            .connectTimeout(Duration.ofMillis(300))
            .build()

        return MySqlConnectionFactory.from(configuration)
    }

    @Bean
    fun reactiveTransactionManager(connectionFactory: ConnectionFactory) : ReactiveTransactionManager{
        return R2dbcTransactionManager(connectionFactory)
    }

    // init sql 실행을 위한 코드..
    @Bean
    fun init(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setDatabasePopulator(
                ResourceDatabasePopulator(
                    // src/main/resources 아래에 mysql-schema.sql 을 두자...
                    ClassPathResource("mysql-schema.sql")
                )
            )
        }
        return initializer
    }
}