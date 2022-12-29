package io.kopring.multimodule.kopringmultimodule.demo.domain.repository

import io.kopring.multimodule.kopringmultimodule.demo.domain.Demo
import org.springframework.data.jpa.repository.JpaRepository

interface DemoRepository : JpaRepository<Demo, Long> {
}