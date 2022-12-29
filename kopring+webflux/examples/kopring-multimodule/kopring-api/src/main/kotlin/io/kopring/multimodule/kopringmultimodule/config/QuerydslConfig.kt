package io.kopring.multimodule.kopringmultimodule.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QuerydslConfig (
    private val entityManager: EntityManager
){

    @Bean(name = ["jpaQueryFactory"])
    fun jpaQueryFactory(): JPAQueryFactory{
        return JPAQueryFactory(entityManager)
    }

}