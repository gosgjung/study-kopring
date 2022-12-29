package io.kopring.multimodule.kopringmultimodule.config

import com.querydsl.jpa.impl.JPAQueryFactory
import io.kopring.multimodule.kopringmultimodule.demo.domain.Demo
import io.kopring.multimodule.kopringmultimodule.demo.domain.QDemo
import io.kopring.multimodule.kopringmultimodule.demo.domain.repository.DemoRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuerdslConfigTest @Autowired constructor(
    private val jpaQueryFactory: JPAQueryFactory,
    private val demoRepository: DemoRepository,
){


    @DisplayName("Querydsl 설정이 잘 되었는지 확인을 위해 데이터 100건 insert 후 결과 확인")
    @Test
    fun test1(){
        for(i in 1..100){
            demoRepository.save(Demo(name = i.toString(), id = i.toLong()))
        }

        val list = jpaQueryFactory.select(QDemo.demo)
            .from(QDemo.demo)
            .fetch()

        assertThat(list, Matchers.hasSize(100))
    }

}