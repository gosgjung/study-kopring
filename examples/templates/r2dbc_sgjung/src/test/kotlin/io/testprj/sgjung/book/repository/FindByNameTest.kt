package io.testprj.sgjung.book.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate

@SpringBootTest
class FindByNameTest {

    @Autowired
    lateinit var r2dbcEntityTemplate: R2dbcEntityTemplate

    @Test
    fun `findByName 테스트`(){
        val entityTemplate: BookEntityTemplate = BookEntityTemplate(r2dbcEntityTemplate)
        val blockLast = entityTemplate.findByName("스포츠").blockLast()
        println(blockLast)
    }
}